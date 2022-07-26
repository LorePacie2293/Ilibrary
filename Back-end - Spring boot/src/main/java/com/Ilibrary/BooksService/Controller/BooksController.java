package com.Ilibrary.BooksService.Controller;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Ilibrary.BooksService.Entity.Books;
import com.Ilibrary.BooksService.Entity.Carts;
import com.Ilibrary.BooksService.Entity.Categorie;
import com.Ilibrary.BooksService.Entity.Ordini;
import com.Ilibrary.BooksService.Entity.Users;
import com.Ilibrary.BooksService.Exception.BindingException;
import com.Ilibrary.BooksService.Exception.DuplicateException;
import com.Ilibrary.BooksService.Exception.ErrorResponse;
import com.Ilibrary.BooksService.Exception.NotFoundException;
import com.Ilibrary.BooksService.Repository.BooksRepository;
import com.Ilibrary.BooksService.Repository.OrdiniRepository;
import com.Ilibrary.BooksService.Repository.UsersRepository;
import com.Ilibrary.BooksService.Service.BooksService;
import com.Ilibrary.BooksService.Service.CategorieService;
import com.Ilibrary.BooksService.Service.OrdiniService;
import com.Ilibrary.BooksService.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class BooksController {
	
	@Autowired
	private BooksService bookService;
	
	@Autowired
	private CategorieService categoryService;
	
	@Autowired
	private OrdiniService ordiniService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceBundleMessageSource messageRepository;
	
	//--------------------------------------------- GET --------------------------------------------------
	
	//Ricerca TUTTI i BOOKS
	@GetMapping(value = "/books/get/byAll", produces = "application/json")
	public ResponseEntity<List<Books>> listBooksByAll() throws NotFoundException{
				
		List<Books> bookList = bookService.selBookAll();
		String errorMsg = "Impossibile trovare gli articoli";
		
		//in caso di NOME non trovato
		if(bookList.isEmpty()) {
			throw new NotFoundException(errorMsg);
		}
		else {
							
			return new ResponseEntity<List<Books>>(bookList, HttpStatus.OK);
		}
	}
	
	//Restituisce gli articoli by ID
	@GetMapping(value = "/books/get/byId/{bookId}")
	public ResponseEntity<Books> getAllBooksById(@PathVariable(value = "bookId") int bookId) throws NotFoundException{
			
		Books book = bookService.selBookById(bookId);
			
		//Verifica che l'ordine esista
		if(book == null) {
			String msgErr = String.format("Nessun book con id: " + bookId);
			throw new NotFoundException(msgErr);
		}
			
		return new ResponseEntity<Books>(book, HttpStatus.OK);
	}
		
	//Restituisce gli articoli by TITLE
	@GetMapping(value = "/books/get/byTitle/{bookTitle}")
	public ResponseEntity<List<Books>> getAllBooksByTitle(@PathVariable(value = "bookTitle") String bookTitle) throws NotFoundException{
		
		List<Books> listBooks = bookService.selBookByTitle(bookTitle);
		
		//Verifica che l'ordine esista
		if(listBooks.isEmpty()) {
			String msgErr = String.format("Nessun book con title: " + bookTitle);
			throw new NotFoundException(msgErr);
		}
		
		return new ResponseEntity<List<Books>>(listBooks, HttpStatus.OK);
	}
	
	//Restituisce gli articoli by AUTHOR FIRST NAME
	@GetMapping(value = "/books/get/byAuthFname/{authFname}")
	public ResponseEntity<List<Books>> getAllBooksByAuthorFirstName(@PathVariable(value = "authFname") String firstName) throws NotFoundException{
			
		List<Books> listBooks = bookService.selByAuthorFname(firstName);
			
		//Verifica che l'ordine esista
		if(listBooks.isEmpty()) {
			String msgErr = String.format("Nessun book con author first name: " + firstName);
			throw new NotFoundException(msgErr);
		}
			
		return new ResponseEntity<List<Books>>(listBooks, HttpStatus.OK);
	}
	

	//Restituisce gli articoli by AUTHOR LAST NAME
	@GetMapping(value = "/books/get/byAuthLname/{authLname}")
	public ResponseEntity<List<Books>> getAllBooksByAuthorLastName(@PathVariable(value = "authLname") String lastName) throws NotFoundException{
			
		List<Books> listBooks = bookService.selByAuthorLname(lastName);
			
		//Verifica che l'ordine esista
		if(listBooks.isEmpty()) {
			String msgErr = String.format("Nessun book con author last name: " + lastName);
			throw new NotFoundException(msgErr);
		}
			
		return new ResponseEntity<List<Books>>(listBooks, HttpStatus.OK);
	}
	
	//Restituisce gli articoli by YEAR RELEASED
	@GetMapping(value = "/books/get/byYear/{bookYear}")
	public ResponseEntity<List<Books>> getAllBooksByYear(@PathVariable(value = "bookYear") int year) throws NotFoundException{
		
		List<Books> listBooks = bookService.selByReleasedYear(year);
		
		//Verifica che l'ordine esista
		if(listBooks.isEmpty()) {
			String msgErr = String.format("Nessun book con year released: " + year);
			throw new NotFoundException(msgErr);
		}
		
		return new ResponseEntity<List<Books>>(listBooks, HttpStatus.OK);
	}
	
	//Restituisce gli articoli by STOCK QUANTITY
	@GetMapping(value = "/books/get/byStockQuantity/{quantity}")
	public ResponseEntity<List<Books>> getAllBooksByStockQuantity(@PathVariable(value = "quantity") int quantity) throws NotFoundException{
			
		List<Books> listBooks = bookService.selByStockQuantity(quantity);
			
		//Verifica che l'ordine esista
		if(listBooks.isEmpty()) {
			String msgErr = String.format("Nessun book con stock quantity: " + quantity);
			throw new NotFoundException(msgErr);
		}
			
		return new ResponseEntity<List<Books>>(listBooks, HttpStatus.OK);
	}
	
	//Restituisce gli articoli by PAGES
	@GetMapping(value = "/books/get/byPages/{pages}")
	public ResponseEntity<List<Books>> getAllBooksByPages(@PathVariable(value = "pages") int pages) throws NotFoundException{
				
		List<Books> listBooks = bookService.selByPages(pages);
				
		//Verifica che l'ordine esista
		if(listBooks.isEmpty()) {
			String msgErr = String.format("Nessun book con pages: " + pages);
			throw new NotFoundException(msgErr);
		}
				
		return new ResponseEntity<List<Books>>(listBooks, HttpStatus.OK);
	}
	
	//Restituisce gli articoli by PRICE
	@GetMapping(value = "/books/get/byPrice/{price}")
	public ResponseEntity<List<Books>> getAllBooksByPrice(@PathVariable(value = "price") double price) throws NotFoundException{
					
			List<Books> listBooks = bookService.selByPrice(price);
					
			//Verifica che l'ordine esista
			if(listBooks.isEmpty()) {
				String msgErr = String.format("Nessun book con price: " + price);
				throw new NotFoundException(msgErr);
			}
					
			return new ResponseEntity<List<Books>>(listBooks, HttpStatus.OK);
	}
	
	//Restituisce TUTTI i BOOKS presentI in un ORDINE
	@GetMapping(value = "/books/get/byOrdine/{idOrdine}")
	public ResponseEntity<List<Books>> getAllBooksByOrdine(@PathVariable(value = "idOrdine") int idOrdine) throws NotFoundException{
		
		Ordini order = ordiniService.selByOrdineId(idOrdine);
		
		//Verifica che l'ordine esista
		if(order == null) {
			String msgErr = String.format("ordine %s non presente nel database", idOrdine);
			throw new NotFoundException(msgErr);
		}
		
		List<Books> listBooks  = bookService.selBooksInOrder(idOrdine);
		
		if(listBooks.isEmpty()) {
			String msgErr = String.format("Nessun book presente nell'ordine", idOrdine);
			throw new NotFoundException(msgErr);
		}
		
		return new ResponseEntity<List<Books>>(listBooks, HttpStatus.OK);
	}
	
	//Restituisce TUTTI i BOOKS presentI in un CATEGORY
	@GetMapping(value = "/books/get/byCategory/{idCategory}")
	public ResponseEntity<List<Books>> getAllBooksByCategory(@PathVariable(value = "idCategory") int idCategory) throws NotFoundException{
			
		Categorie categoria = categoryService.selCategoryById(idCategory);
			
		//Verifica che l'ordine esista
		if(categoria == null) {
			String msgErr = String.format("categoria %s non presente nel database", idCategory);
			throw new NotFoundException(msgErr);
		}
			
		List<Books> listBooks  = bookService.selBooksInCategory(categoria);
			
		if(listBooks.isEmpty()) {
			String msgErr = String.format("Nessun book presente con categoria %s", idCategory);
			throw new NotFoundException(msgErr);
		}
			
		return new ResponseEntity<List<Books>>(listBooks, HttpStatus.OK);
	}
	
	//Restituisce TUTTI i BOOKS presentI in un CART
	@GetMapping(value = "/books/get/byCart/{username}")
	public ResponseEntity<List<Books>> getAllBooksByCart(@PathVariable(value = "username") String username) throws NotFoundException{
				
		Users user = userService.selUserByUsername(username);
		
		if(user == null) {
			String msgErr = String.format("Nessun cart presente per utente: %s", username);
			throw new NotFoundException(msgErr);
		}
		
		Carts cart = user.getCart();
		List<Books> books = cart.getBooks();
		
		//Se non è presente alcun libro
		if(books.isEmpty()) {
			String msgErr = String.format("Nessun book presente in cart di user: %s", username);
			throw new NotFoundException(msgErr);
		}
		
		return new ResponseEntity<List<Books>>(books, HttpStatus.OK);
	}
		
	//--------------------------------------------- POST --------------------------------------------------
	
	//Inserisce un BOOK 
	@PostMapping(value = "/books/post", consumes={"application/json"})
	public ResponseEntity<Books> insertBooks(@Valid @RequestBody Books book, BindingResult bindValid) throws BindingException, DuplicateException, IOException, NotFoundException{
		
		//Se la BINDING VALIDATION non è stata superata
		if(bindValid.hasErrors()) {
			
			//Ottiene la stringa di errore ricavata dal BINDING VALIDATION
			String msgErr = messageRepository.getMessage(bindValid.getFieldError(), LocaleContextHolder.getLocale());
			throw new BindingException(msgErr);
		}
		
		//Controlla che l'articolo da inserire SIA GIA PRESENTE nel db
		Books checkBook = bookService.selBookById(book.getBookId());
		
		//Controlla che le categorie inserite siano CATEGORIE ESISTENTI
		boolean checkCategory = categoryService.checkInsertcategory(book.getBooksCategory());
				
		if(checkBook != null) {
			
			String msgErr = String.format("Book %s e gia presente nel databas", book.getBookId());
			throw new DuplicateException(msgErr);
		}
		
		if(!checkCategory) {
			String msgErr = String.format("Nessuna categoria inserita nel db");
			throw new NotFoundException(msgErr);
		}
		
		//Se le categorie sono valide
		
		//Inserisce il book nel DB
		bookService.insertBook(book);
		return new ResponseEntity<Books>(book, new HttpHeaders(), HttpStatus.CREATED);
	}
	
	

	
	//--------------------------------------------- PUT --------------------------------------------------
	
	//Aggiorna Book (NON GESTISCE ECCEZIONE DUPLICATE EXCEPTIO)
	@RequestMapping(value = "/books/put", method = RequestMethod.PUT)
	public ResponseEntity<Books> updateBook(@Valid @RequestBody Books book, BindingResult bindValid ) throws BindingException, NotFoundException{
		
		//Se la BINDING VALIDATION non è stata superata
		if(bindValid.hasErrors()) {
					
			//Ottiene la stringa di errore ricavata dal BINDING VALIDATION
			String msgErr = messageRepository.getMessage(bindValid.getFieldError(), LocaleContextHolder.getLocale());
			throw new BindingException(msgErr);
		}
		
		//VERIFICA DISPONIBILITA BOOK
		Books checkBook = bookService.selBookById(book.getBookId());
		
		//Controlla che le categorie inserite siano CATEGORIE ESISTENTI
		boolean checkCategory = categoryService.checkInsertcategory(book.getBooksCategory());
		
		//se il book NON E PRESENTE nel databasee
		if(checkBook == null) {
			
			String msgErr = String.format("Book %s non presente nel database", book.getBookId());
			throw new NotFoundException(msgErr);
		}
		
		if(!checkCategory) {
			String msgErr = String.format("Nessuna categoria inserita nel db");
			throw new NotFoundException(msgErr);
		}
		
		//Inserisce il NUOVO book nel database
		bookService.updateBook(book);
		return new ResponseEntity<Books>(book, new HttpHeaders(), HttpStatus.CREATED);
	}
	
	//--------------------------------------------- DELETE --------------------------------------------------
	
	//Elimina 1 book By ID
	@RequestMapping(value = "/books/delete/{idBook}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteBook(@Valid @PathVariable("idBook") int idBook) throws  NotFoundException{
		
		//VERIFICA DISPONIBILITA BOOK
		Books checkBook = bookService.selBookById(idBook);
		
		//se il book NON E PRESENTE nel databasee
		if(checkBook == null) {
					
			String msgErr = String.format("Book %s non presente nel database", idBook);
			throw new NotFoundException(msgErr);
		}
		
		//ELIMINA il book con ID corrispondente
		bookService.deleteBook(checkBook);
		
		//CREAZIONE risposta di conferma eliminazione BOOK
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put("codice:", HttpStatus.OK.toString());
		responseNode.put("Messaggio: ", "BOOK con ID: " + idBook + " Eliminato");
		return new ResponseEntity<>(responseNode, HttpStatus.OK) ;
	}
	
	//Elimina 1 book By TITLE la prima OCCORRENZA
	@RequestMapping(value = "/books/delete/byTitle/{titleBook}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteBook(@Valid @PathVariable("titleBook") String titleBook) throws  NotFoundException{
			
			//VERIFICA DISPONIBILITA BOOK
			List<Books> checkBooks = bookService.selBookByTitle(titleBook);
			
			//se il book NON E PRESENTE nel databasee
			if(checkBooks.isEmpty()) {
						
				String msgErr = String.format("Books %s non presenti nel database", titleBook);
				throw new NotFoundException(msgErr);
			}
			
			//ELIMINA il book con ID corrispondente
			bookService.deleteBook(checkBooks.get(0));
			
			//CREAZIONE risposta di conferma eliminazione BOOK
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode responseNode = mapper.createObjectNode();
			
			responseNode.put("codice:", HttpStatus.OK.toString());
			responseNode.put("Messaggio: ", "BOOK con ID: " + titleBook + " Eliminato");
			return new ResponseEntity<>(responseNode, HttpStatus.OK) ;
		}
	
	
}
