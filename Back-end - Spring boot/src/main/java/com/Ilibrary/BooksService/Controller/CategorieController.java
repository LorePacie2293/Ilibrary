package com.Ilibrary.BooksService.Controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ilibrary.BooksService.Entity.Categorie;
import com.Ilibrary.BooksService.Exception.NotFoundException;
import com.Ilibrary.BooksService.Service.CategorieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@RestController
@RequestMapping("/api")
@CrossOrigin()
public class CategorieController {
	
	@Autowired
	CategorieService categorieService;
	
	//--------------------------------------------- GET --------------------------------------------------
	
	//Ricerca TUTTE le categorie
	@GetMapping(value = "/category/get/byAll", produces = "application/json")
	public ResponseEntity<List<Categorie>> listCategoryByAll() throws NotFoundException{
					
		List<Categorie> categoryList = categorieService.selCategoryAll();
		String errorMsg = "Impossibile trovare le categorie";
			
		//in caso di NOME non trovato
		if(categoryList.isEmpty()) {
			throw new NotFoundException(errorMsg);
		}
		else {
								
			return new ResponseEntity<List<Categorie>>(categoryList, HttpStatus.OK);
		}
	}
		
	//Restituisce una categoria by ID
	@GetMapping(value = "/category/get/byId/{categoryId}")
	public ResponseEntity<Categorie> getCategoriaById(@PathVariable(value = "categoryId") int categoryId) throws NotFoundException{
				
		Categorie categoria = categorieService.selCategoryById(categoryId);
				
		//Verifica che la categoria esista
		if(categoria == null) {
			String msgErr = String.format("Nessuna categoria con id: " + categoryId);
			throw new NotFoundException(msgErr);
		}
				
			return new ResponseEntity<Categorie>(categoria, HttpStatus.OK);
	}
	
		
	//Restituisce una categoria by TITLE
	@GetMapping(value = "/category/get/byTitle/{categoryTitle}")
	public ResponseEntity<Categorie> getAllBooksByTitle(@PathVariable(value = "categoryTitle") String categoryTitle) throws NotFoundException{
			
		Categorie listCategory = categorieService.selCategoryByTitle(categoryTitle);
			
		//Verifica che l'ordine esista
		if(listCategory == null) {
			String msgErr = String.format("Nessuna categoria con title: " + categoryTitle);
			throw new NotFoundException(msgErr);
		}
			
		return new ResponseEntity<Categorie>(listCategory, HttpStatus.OK);
	}
	
	//--------------------------------------------- DELETE --------------------------------------------------
	
	//Elimina 1 CATEGORIA
	@RequestMapping(value = "/category/delete/byId/{idCategory}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteBook(@Valid @PathVariable("idCategory") int idCategory) throws  NotFoundException{
			
		//VERIFICA DISPONIBILITA CATEGORIA
		Categorie checkCategoria = categorieService.selCategoryById(idCategory);
			
		//se categoria NON E PRESENTE nel databasee
		if(checkCategoria == null) {
						
			String msgErr = String.format("Genere %s non presente nel database", idCategory);
			throw new NotFoundException(msgErr);
		}
			
		//ELIMINA la categoria con ID corrispondente
		categorieService.deleteCategoria(checkCategoria);
			
		//CREAZIONE risposta di conferma eliminazione BOOK
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
			
		responseNode.put("codice:", HttpStatus.OK.toString());
		responseNode.put("Messaggio: ", "Genere con ID: " + idCategory + " Eliminato");
		return new ResponseEntity<>(responseNode, HttpStatus.OK) ;
		
	}
}
	

	
