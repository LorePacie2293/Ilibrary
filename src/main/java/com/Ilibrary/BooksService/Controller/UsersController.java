package com.Ilibrary.BooksService.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.Ilibrary.BooksService.Entity.Users;
import com.Ilibrary.BooksService.Exception.BindingException;
import com.Ilibrary.BooksService.Exception.DuplicateException;
import com.Ilibrary.BooksService.Exception.NotFoundException;
import com.Ilibrary.BooksService.Repository.UsersRepository;
import com.Ilibrary.BooksService.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class UsersController {

	@Autowired
	UserService userService;
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ResourceBundleMessageSource messageRepository;
	
	//Restituisce TUTTI gli user
	@GetMapping(value = "/users/get/byAll")
	public ResponseEntity<List<Users>> getAllBooksByAll() throws NotFoundException{
				
		List<Users> users = userService.selUsersAll();
				
		//Verifica che l'ordine esista
		if(users.isEmpty()) {
			String msgErr = String.format("Nessuno users registrato");
			throw new NotFoundException(msgErr);
		}		
		return new ResponseEntity<List<Users>>(users, HttpStatus.OK);
	}
		
	//Restituisce uno user da USERNAME
	@GetMapping(value = "/users/get/byUsername/{username}")
	public ResponseEntity<Users> getAllBooksById(@PathVariable(value = "username") String username) throws NotFoundException{
			
		Users user = usersRepository.findByusername(username);
			
		//Verifica che l'ordine esista
		if(user == null) {
			String msgErr = String.format("Nessun user con username: " + username);
			throw new NotFoundException(msgErr);
		}
			
		return new ResponseEntity<Users>(user, HttpStatus.OK);
	}
		
	//Registrazione nuovo utente
	@PostMapping(value = "/users/post", consumes={"application/json"})
	public Users registerUserAccount(@Valid @RequestBody Users user, BindingResult bindValid) throws DuplicateException, BindingException {
		
		String encodedPassword;
		
		//Se prima registrazione, AVVIA registrazione automatica utente ADMIN
		List<Users> listUsers = userService.selUsersAll();
		
		if(listUsers.isEmpty()) {
			Users adminUser = new Users("Gandalf", "Il grigio", "gandalf@gmail.com", "prova", "Gandalf", "ADMIN");
			encodedPassword = passwordEncoder.encode(adminUser.getUserPassword());
			adminUser.setUserPassword(encodedPassword);
			userService.registerUser(adminUser);
		}
		
		//Se la BINDING VALIDATION non è stata superata
		if(bindValid.hasErrors()) {
					
			//Ottiene la stringa di errore ricavata dal BINDING VALIDATION
			String msgErr = messageRepository.getMessage(bindValid.getFieldError(), LocaleContextHolder.getLocale());
			throw new BindingException(msgErr);
		}
				
		//Controllo username GIA ESISTENTE
		Users checkUserUsername = userService.selUserByUsername(user.getUsername());
		
		if(checkUserUsername != null) {
			
			String msgErr = String.format("Username %s e gia presente nel database", checkUserUsername.getUsername());
			throw new DuplicateException(msgErr);
			
		}
		
		//Controlla mail gia esistente
		Users checkUserEmail = userService.selUserByEmail(user.getUserEmail());
		
		if(checkUserEmail != null) {
			
			String msgErr = String.format("Email %s e gia presente nel database", checkUserEmail.getUserEmail());
			throw new DuplicateException(msgErr);
		}
		
		encodedPassword = passwordEncoder.encode(user.getUserPassword());
		user.setUserPassword(encodedPassword);
		return userService.registerUser(user);
	}
	
	//Elimina 1 USER By USERNAME 
	@RequestMapping(value = "/users/delete/byUsername/{username}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteBook(@Valid @PathVariable("username") String username) throws  NotFoundException{
				
		//VERIFICA DISPONIBILITA BOOK
		Users user = userService.selUserByUsername(username);
				
		//se USER NON E PRESENTE nel databasee
		if(user == null) {
							
			String msgErr = String.format("User %s non presente nel database", username);
			throw new NotFoundException(msgErr);
		}
				
		//ELIMINA user con username corrispondente
		userService.removeUser(user);
				
		//CREAZIONE risposta di conferma eliminazione BOOK
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
				
		responseNode.put("codice:", HttpStatus.OK.toString());
		responseNode.put("Messaggio: ", "User "+ username + " Eliminato");
		return new ResponseEntity<>(responseNode, HttpStatus.OK) ;
	}
}
