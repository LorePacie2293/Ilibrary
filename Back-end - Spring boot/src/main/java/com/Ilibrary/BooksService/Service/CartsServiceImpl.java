package com.Ilibrary.BooksService.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ilibrary.BooksService.Entity.Books;
import com.Ilibrary.BooksService.Entity.Carts;
import com.Ilibrary.BooksService.Entity.Users;
import com.Ilibrary.BooksService.Repository.BooksRepository;
import com.Ilibrary.BooksService.Repository.CartsRepository;
import com.Ilibrary.BooksService.Repository.UsersRepository;

@Service
@Transactional
public class CartsServiceImpl implements CartsService{

	@Autowired
	CartsRepository cartsRepository;
	
	@Autowired
	BooksRepository booksRepository;
	
	@Autowired
	UsersRepository userRepository;
	
	@Override
	public Carts selCartByUser(Users user) {
		
		return cartsRepository.findCartByUser(user); 
		
	}

	@Override
	@Transactional
	public Carts insertcart(Carts cart) {
		return cartsRepository.save(cart);
	}

	@Override
	@Transactional
	public void removeCarts(Carts cart) {
		
		cartsRepository.delete(cart);
		
	}

	@Override
	@Transactional
	public Carts removeAllBooksByCarts(Carts carts, Users user) {
		
		for(Books book : carts.getBooks()) {
			
			//Ricerca book
			booksRepository.findById(book.getBookId());
			
			//Rimozione book in cart
	    	carts.removeBook(book);
	    	book.removeCart(carts);
	    		
	    	cartsRepository.delete(carts);
		}
		
		return carts;
		
	}

	@Override
	public boolean isExistBooks(Carts cart) {
		
		List<Books> books = cart.getBooks();
		
		if(books.isEmpty()) {
			return false;
		}
		else {
			return true;

		}
	}
	
	

}
