package com.Ilibrary.BooksService.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ilibrary.BooksService.Entity.Books;
import com.Ilibrary.BooksService.Entity.Carts;
import com.Ilibrary.BooksService.Entity.Users;
import com.Ilibrary.BooksService.Exception.NotFoundException;
import com.Ilibrary.BooksService.Repository.CartsRepository;
import com.Ilibrary.BooksService.Service.BooksService;
import com.Ilibrary.BooksService.Service.CartsService;
import com.Ilibrary.BooksService.Service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class CartsController {

	@Autowired
	UserService userService;
	
	@Autowired
	BooksService bookService;
	
	@Autowired
	CartsService cartService;
	
	@Autowired
	CartsRepository cartRepository;
	
	
	
	//Restituisce tutti i CART by USERNAME
	@GetMapping(value = "/carts/get/byUsername/{username}")
	public ResponseEntity<Carts> getAllBooksById(@PathVariable(value = "username") String username) throws NotFoundException{
				
		//Ottiene l'utente tramite username
		Users user = userService.selUserByUsername(username);
		
		Carts cart = cartService.selCartByUser(user);
				
		//Verifica che l'ordine esista
		if(cart == null) {
			String msgErr = String.format("Nessun cart per user con username: " + username);
			throw new NotFoundException(msgErr);
		}
			
		//Verifica che l'utente esista
		if(user == null) {
			String msgErr = String.format("Nessuno user per username: " + username);
			throw new NotFoundException(msgErr);
		}
	
		return new ResponseEntity<Carts>(cart, HttpStatus.OK);
	}
	
	//Restituisce TUTTI I CARTS che abbiano un book con id specificato
	@GetMapping(value = "/carts/get/byBookId/{bookId}")
	public ResponseEntity<List<Carts>> getAllBooksById(@PathVariable(value = "bookId") int bookId) throws NotFoundException{
				
			Books book = bookService.selBookById(bookId);
			
			//Controlla se il book esiste
			if(book == null) {
				String msgErr = String.format("Nessun cart con book id: " + bookId);
				throw new NotFoundException(msgErr);
			}
			
			List<Carts> noDuplicateCarts = new ArrayList<Carts>();
			
			//Ottiene l'utente tramite username
			List<Carts> carts = cartRepository.findBybooks(book);
					
			//Eliminazioni duplicati, in caso un carrello abbia piu di 1 libro
			for(Carts cart : carts) {
				
				//Se il carrello ricevuto è gia stato inserito nel'array no duplicate
				if(!noDuplicateCarts.contains(cart)) {
					noDuplicateCarts.add(cart);
				}
			}
			
			//Verifica che l'ordine esista
			if(noDuplicateCarts.isEmpty()) {
				String msgErr = String.format("Impossibile trovare carts");
				throw new NotFoundException(msgErr);
			}
				
			
		
			return new ResponseEntity<List<Carts>>(noDuplicateCarts, HttpStatus.OK);
	}
	
	//Aggiunge un book al carrello
	@PostMapping("/carts/post/{username}/books/{bookId}")
	public ResponseEntity<Carts> addBooksToCart(@PathVariable(value = "username") String username, 
			                                    @PathVariable(value = "bookId") int bookId) throws NotFoundException 
	{
		//Ricerca dell'utente(quindi carrello)
	    Users user = userService.selUserByUsername(username);
	    Carts cart= null;
	    
	    //Se l'utente è stato trovato
	    if(user != null) {
	    	
	    	//Ricerca carrello dell'user
	    	cart = cartService.selCartByUser(user);
	    	
	    	//Se il carrello non è stato trovato
			if(cart == null) {
				String msgErr = String.format("Nessun cart per user con username: " + username);
				throw new NotFoundException(msgErr);
			}
			
	    	//Ricerca Book tramite ID
	    	Books book = bookService.selBookById(bookId);
	    	if(book != null) {
	    		
	    		//Diminuzione quantita disponibili
	    		book.setStockQuantity(book.getStockQuantity() - 1);
	    		
	    		
	    		//Aggiunta book in cart
	    		cart.addBook(book);
	    		book.addCart(cart);
	    		
	    		cartService.insertcart(cart);
	    	
	
	    	}
	    	else {
	    		String msgErr = String.format("Book %s non presente nel database", bookId);
				throw new NotFoundException(msgErr);
	    	}
	    }
	    else {
	    	String msgErr = String.format("Utente %s non presente nel database", username);
			throw new NotFoundException(msgErr);
	    }
	    
	    return new ResponseEntity<Carts>(cart, HttpStatus.OK);
	 }
		
	//Aggiunge un book by TITLE al carrello
	@GetMapping("/carts/get/{username}/booksByTitle/{bookTitle}")
	public ResponseEntity<Carts> addBooksToCartByTitle(@PathVariable(value = "username") String username, 
				                                    @PathVariable(value = "bookTitle") String bookTitle) throws NotFoundException 
		{
			//Ricerca dell'utente(quindi carrello)
		    Users user = userService.selUserByUsername(username);
		    Carts cart= null;
		    
		    //Se l'utente è stato trovato
		    if(user != null) {
		    	
		    	//Ricerca carrello dell'user
		    	cart = cartService.selCartByUser(user);
		    	
		    	//Se il carrello non è stato trovato
				if(cart == null) {
					String msgErr = String.format("Nessun cart per user con username: " + username);
					throw new NotFoundException(msgErr);
				}
				
		    	//Ricerca Book tramite TITLE
		    	Books book = bookService.selBookByTitle(bookTitle).get(0);
		    	
		    	if(book != null) {
		    		
		    		book.setStockQuantity(book.getStockQuantity() - 1);
		    		
		    		//Aggiunta book in cart
		    		cart.addBook(book);
		    		book.addCart(cart);
		    		
		    		cartService.insertcart(cart);
		
		    	}
		    	else {
		    		String msgErr = String.format("Book %s non presente nel database", bookTitle);
					throw new NotFoundException(msgErr);
		    	}
		    }
		    else {
		    	String msgErr = String.format("Utente %s non presente nel database", username);
				throw new NotFoundException(msgErr);
		    }
		    
		    return new ResponseEntity<Carts>(cart, HttpStatus.OK);
	}
		
	//Rimuove un book al carrello
	@RequestMapping(value = "/carts/delete/{username}/books/{bookId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Carts> removeBooksToCart(@PathVariable(value = "username") String username, 
				                                    @PathVariable(value = "bookId") int bookId) throws NotFoundException 
	{
		//Ricerca dell'utente(quindi carrello)
		Users user = userService.selUserByUsername(username);
		Carts cart= null;
		    
		//Se l'utente è stato trovato
		if(user != null) {
		    	
			//Ricerca carrello dell'user
		    cart = cartService.selCartByUser(user);
		    	
		    //Se il carrello non è stato trovato
			if(cart == null) {
				String msgErr = String.format("Nessun cart per user con username: " + username);
				throw new NotFoundException(msgErr);
			}
				
		    //Ricerca Book tramite ID
		    Books book = bookService.selBookById(bookId);
		    if(book != null) {
		    	
	    		book.setStockQuantity(book.getStockQuantity() + 1);

		    	//Rimozione book in cart
		    	cart.removeBook(book);
		    	book.removeCart(cart);
		    		
		    	cartService.removeCarts(cart);
		
		    }
		    else {
		    		String msgErr = String.format("Book %s non presente nel database", bookId);
					throw new NotFoundException(msgErr);
		    }
		 }
		 else {
		    	String msgErr = String.format("Utente %s non presente nel database", username);
				throw new NotFoundException(msgErr);
		 }
		    
		 return new ResponseEntity<Carts>(cart, HttpStatus.OK);
	}
	
	//Rimuove un book by TITLE carrello
	@RequestMapping(value = "/carts/delete/{username}/booksByTitle/{bookTitle}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Carts> removeBooksByTitleToCart(@PathVariable(value = "username") String username, 
				                                    @PathVariable(value = "bookTitle") String bookTitle) throws NotFoundException 
	{
		//Ricerca dell'utente(quindi carrello)
		Users user = userService.selUserByUsername(username);
		Carts cart= null;
		    
		//Se l'utente è stato trovato
		if(user != null) {
		    	
			//Ricerca carrello dell'user
		    cart = cartService.selCartByUser(user);
		    	
		    //Se il carrello non è stato trovato
			if(cart == null) {
				String msgErr = String.format("Nessun cart per user con username: " + username);
				throw new NotFoundException(msgErr);
			}
				
		    //Ricerca Book tramite ID
		    Books book = bookService.selBookByTitle(bookTitle).get(0);
		    if(book != null) {
		    		
		    	book.setStockQuantity(book.getStockQuantity() + 1);
		    	
		    	//Aggiunta book in cart
		    	cart.removeBook(book);
		    	book.removeCart(cart);
		    		
		    	cartService.removeCarts(cart);
		
		    }
		    else {
		    		String msgErr = String.format("Book %s non presente nel database", bookTitle);
					throw new NotFoundException(msgErr);
		    }
		 }
		    else {
		    	String msgErr = String.format("Utente %s non presente nel database", username);
				throw new NotFoundException(msgErr);
		    }
		    
		    return new ResponseEntity<Carts>(cart, HttpStatus.OK);
		 }
}
