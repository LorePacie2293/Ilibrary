package com.Ilibrary.BooksService.Service;

import java.awt.print.Book;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.Deflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.Ilibrary.BooksService.Entity.Books;
import com.Ilibrary.BooksService.Entity.Carts;
import com.Ilibrary.BooksService.Entity.Categorie;
import com.Ilibrary.BooksService.Entity.Ordini;
import com.Ilibrary.BooksService.Repository.BooksRepository;
import com.Ilibrary.BooksService.Repository.CartsRepository;

@Service
@Transactional(readOnly = true)
public class BooksServiceImpl implements BooksService{
	
	@Autowired 
	BooksRepository bookRepository;
	
	@Autowired
	CategorieService categoryService;
	
	@Autowired
	CartsService cartService;
	
	@Autowired
	CartsRepository cartsRepository;
	
	@Override
	public List<Books> selBookAll(){
		return bookRepository.findAll();
	}
	
	@Override
	public Books selBookById(int id) {
		return bookRepository.findById(id);
	}
	
	@Override
	public List<Books> selBookByTitle(String title){
		return bookRepository.findByBookTitleContaining(title);
	}
	
	@Override
	public List<Books> selByAuthorFname(String name){
		return bookRepository.findByAuthorFnameContaining(name);
	}
	
	@Override
	public List<Books> selByAuthorLname(String surname){
		return bookRepository.findByAuthorLnameContaining(surname);
	}
	
	
	@Override
	public List<Books> selByReleasedYear(int year){
		return bookRepository.findByReleasedYear(year);
	}
	
	@Override
	public List<Books> selByStockQuantity(int quantity){
		return bookRepository.findByStockQuantity(quantity);
	}
	
	@Override
	public List<Books> selByPages(int pages){
		return bookRepository.findByPages(pages);
	}
	
	//Codifica un'IMG
	@Override
	public byte[] codImage(byte[] file) throws IOException {
	
	
		Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(file);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(file.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception e) {
        }
        return outputStream.toByteArray();
	}
	
	@Override
	@Transactional
	public void insertBook(Books book) {
		
		Books checkBook = bookRepository.findById(book.getBookId());

		//POSSIBILE TOGLIERE ---------------------------------------
		//Ricerca le categorie incluse nel book e le ricerca nel db
		//Per evitare che ne vengano generate di nuove con un nuovo ID
		Set<Categorie> categoryNew = new HashSet<>();
		
		for(Categorie cat : book.getBooksCategory()) {
			cat = categoryService.selCategoryByTitle(cat.getCategoriaTitle());
			categoryNew.add(cat);
		}
		
		book.setBooksCategory(categoryNew);
		
		bookRepository.save(book);
	}
	
	@Override
	@Transactional
	public void deleteBook(Books book) {
		bookRepository.delete(book);
	}
	
	//BUG se modifico un book vengono eleminate le relazioni tra quel book ed TUTTI i cart che lo contengono
	@Override
	@Transactional
	public void updateBook(Books book) {
		
		//Lista di carts CONTENTENTE il book da modificare
		List<Carts> carts = cartsRepository.findBybooks(book);
		
		bookRepository.save(book);
		
		//RIPRISTINO relazione tra cart e book modificato
		for(Carts cart : carts) {
			
			book.addCart(cart);
			bookRepository.save(book);
		}
		
	}

	@Override
	@Transactional
	public void insertOrderToBooks(List<Books> books, Ordini ordini) {
		
		for(Books book : books) {
			
			book.addOrder(ordini);
		}
		
	}

	@Override
	public List<Books> selBooksInCart(Carts cart) {
		
		
		return bookRepository.findBylistCarts(cart);
	}

	@Override
	public List<Books> selBooksInOrder(int orderId) {
		
		return bookRepository.findBooksBylistOrdiniOrdineId(orderId);
	}

	@Override
	public List<Books> selByPrice(double price) {
	
		return bookRepository.findByPrice(price);
	}

	@Override
	public List<Books> selBooksInCategory(Categorie category) {
		
		return bookRepository.findBooksBybooksCategory(category);
	}

	

	
}
