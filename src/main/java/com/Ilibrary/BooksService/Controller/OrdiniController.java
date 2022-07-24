package com.Ilibrary.BooksService.Controller;

import java.util.List;
import java.util.Set;

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

import com.Ilibrary.BooksService.Entity.Books;
import com.Ilibrary.BooksService.Entity.Carts;
import com.Ilibrary.BooksService.Entity.Ordini;
import com.Ilibrary.BooksService.Entity.Users;
import com.Ilibrary.BooksService.Exception.BindingException;
import com.Ilibrary.BooksService.Exception.DuplicateException;
import com.Ilibrary.BooksService.Exception.NotFoundException;
import com.Ilibrary.BooksService.Service.BooksService;
import com.Ilibrary.BooksService.Service.CartsService;
import com.Ilibrary.BooksService.Service.OrdiniService;
import com.Ilibrary.BooksService.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class OrdiniController {

	@Autowired
	BooksService bookService;
	
	@Autowired
	OrdiniService ordiniService;
	
	@Autowired
	UserService userService;
	
	@Autowired 
	CartsService cartsService;
	
	@Autowired
	private ResourceBundleMessageSource messageRepository;
	
	//--------------------------------------------- GET --------------------------------------------------
	
	//Restituisce la LISTA COMPLETA degli ordini
	@GetMapping(value = "/ordini/get/byAll")
	public ResponseEntity<List<Ordini>> listOrdiniByAll() throws NotFoundException{
		List<Ordini> listOrdini = ordiniService.selOrdiniAll();
		
		//in caso di barcode non trovato
		if(listOrdini.isEmpty()) {
			String msgErr = String.format("Nessun ORDINE presente nel database");
			throw new NotFoundException(msgErr);
		}
		else {
							
			return new ResponseEntity<List<Ordini>> (listOrdini, HttpStatus.OK);
		}
		
	}
	
	//Restituisce 1 ORDINE specificando ID
	@GetMapping(value = "/ordini/{id}", produces = "application/json")
	public ResponseEntity<Ordini> listOrdiniById(@PathVariable("id") int ordineId){
		
		Ordini ordine = ordiniService.selByOrdineId(ordineId);
		
		//in caso di barcode non trovato
		if(ordine == null) {
			return new ResponseEntity<Ordini>(HttpStatus.NOT_FOUND);
		}
		else {
					
			return new ResponseEntity<Ordini>(ordine, HttpStatus.OK);
		}
	}
	
	//Restituisce 1 ORDINE specificando USERNAME
	@GetMapping(value = "/ordini/get/byUsername/{username}", produces = "application/json")
	public ResponseEntity<Set<Ordini>> listOrdiniByUsername(@PathVariable("username") String username) throws NotFoundException{
		
		Users user = userService.selUserByUsername(username);
		
		Set<Ordini> ordini = ordiniService.selByUsername(user);
			
		//in caso di barcode non trovato
		if(ordini.isEmpty()) {
			String msgErr = String.format("Nessun ORDINE presente nel database");
			throw new NotFoundException(msgErr);
		}
		else {
						
			return new ResponseEntity<Set<Ordini>>(ordini, HttpStatus.OK);
		}
	}
	
	//Restituisce un LISTA di ORDINI specificando il PREZZO
	@GetMapping(value = "/ordini/get/byPrice/{price}", produces = "application/json")
	public ResponseEntity<List<Ordini>> listOrdiniByPrice(@PathVariable("price") double ordinePrice){
		
		List<Ordini> ordiniList = ordiniService.selByOrdinePrice(ordinePrice);
		
		//in caso di barcode non trovato
		if(ordiniList.isEmpty()) {
			return new ResponseEntity<List<Ordini>>(HttpStatus.NOT_FOUND);
		}
		else {
					
			return new ResponseEntity<List<Ordini>>(ordiniList, HttpStatus.OK);
		}
	}
	
	//--------------------------------------------- POST --------------------------------------------------
	
	//Inserisce un ordine 
	@PostMapping(value = "/ordini/post/Users/byUsername/{username}", produces={"application/json"})
	public ResponseEntity<Ordini> insertBooks(@PathVariable(value = "username") String username) throws BindingException, DuplicateException, NotFoundException{
			
		//Ottiene USER by cart ID (UGUALE CART ID)
		Users user = userService.selUserByUsername(username);
		
		//Verifica che l'ordine esista
		if(user == null) {
			String msgErr = String.format("Nessun user con username: " + username);
			throw new NotFoundException(msgErr);
		}
		
		//Ottiene la lista di books dal carrello di user
		List<Books> books = user.getCart().getBooks();
		
		//Verifica che l'utente abbia books contenuti nel cart
		if(books.isEmpty()) {
			String msgErr = String.format("Nessun BOOK presente nel cart");
			throw new NotFoundException(msgErr);
		}
		
		//Creazione nuovo ordine
		Ordini ordine = new Ordini(ordiniService.calcTotOrdine(books), user.getUsername());
		
		//Creazione relazione USER-ORDINI
		//Aggiungi ordine a users
		user.addOrdine(ordine);
		
		//Aggiungi user ad ordine
		ordine.setUser(user);
		
		//Creazione relazione ORDINE-BOOKS
		//Aggiunta BOOKS  a ordine
		ordine.addBook(books);
		
		//Aggiunta ORDINE a BOOKS
		bookService.insertOrderToBooks(books, ordine);
		
		//Pulizia CART
		Carts cart = cartsService.selCartByUser(user);
		
		//Finchè sono presenti books nel cart
		while(cartsService.isExistBooks(cart)) {
			for(Books book : bookService.selBooksInCart(cart)) {
				if(book != null) {
					cart.removeBook(book);
			    	book.removeCart(cart);
			    	cartsService.removeCarts(cart);
				}
				else {
					//Verifica che l'ordine esista
					if(user == null) {
						String msgErr = String.format("Nessun user con username: " + username);
						throw new NotFoundException(msgErr);
					}
				}
			}
		}
		
		return new ResponseEntity<Ordini>(ordine, HttpStatus.OK);
		
	}
	
	//--------------------------------------------- PUT --------------------------------------------------
	
		//Aggiorna Ordine (NON GESTISCE ECCEZIONE DUPLICATE EXCEPTION)
		@RequestMapping(value = "/ordini", method = RequestMethod.PUT)
		public ResponseEntity<Ordini> updateOrdine(@Valid @RequestBody Ordini ordine, BindingResult bindValid ) throws BindingException, NotFoundException{
			
			//Se la BINDING VALIDATION non è stata superata
			if(bindValid.hasErrors()) {
						
				//Ottiene la stringa di errore ricavata dal BINDING VALIDATION
				String msgErr = messageRepository.getMessage(bindValid.getFieldError(), LocaleContextHolder.getLocale());
				throw new BindingException(msgErr);
			}
			
			//VERIFICA DISPONIBILITA ORDINE
			Ordini checkOrdine = ordiniService.selByOrdineId(ordine.getOrdineId());
			
			//se il book NON E PRESENTE nel databasee
			if(checkOrdine == null) {
				
				String msgErr = String.format("Ordine %s non presente nel database", ordine.getOrdineId());
				throw new NotFoundException(msgErr);
			}
			
			//Inserisce il NUOVO ordine nel database
			ordiniService.insertOrdine(ordine);
			return new ResponseEntity<Ordini>(ordine, new HttpHeaders(), HttpStatus.CREATED);
		}
	
		//--------------------------------------------- DELETE --------------------------------------------------
		
		//Elimina 1 ordine
		@RequestMapping(value = "/ordini/delete/byIdOrdine/{idOrdine}", method = RequestMethod.DELETE, produces = "application/json")
		public ResponseEntity<?> deleteOrdine(@Valid @PathVariable("idOrdine") int idOrdine) throws  NotFoundException{
			
			//VERIFICA DISPONIBILITA BOOK
			Ordini checkOrdine = ordiniService.selByOrdineId(idOrdine);
			
			//se il book NON E PRESENTE nel databasee
			if(checkOrdine == null) {
						
				String msgErr = String.format("Ordine %s non presente nel database", idOrdine);
				throw new NotFoundException(msgErr);
			}
			
			//ELIMINA ORDINE  con ID corrispondente
			ordiniService.deleteOrdine(checkOrdine);
			
			//CREAZIONE risposta di conferma eliminazione BOOK
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode responseNode = mapper.createObjectNode();
			
			responseNode.put("codice:", HttpStatus.OK.toString());
			responseNode.put("Messaggio: ", "ORDINE con ID: " + idOrdine + " Eliminato");
			return new ResponseEntity<>(responseNode, HttpStatus.OK) ;
		}
}
