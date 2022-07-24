package com.Ilibrary.BooksService.Service;

import java.util.List;
import java.util.Set;

import com.Ilibrary.BooksService.Entity.Categorie;


public interface CategorieService {
	
	//ALL
	public List<Categorie> selCategoryAll();
	
	//Controlla che le categorie inserite in un libro siano esistenti
	public boolean checkInsertcategory(Set<Categorie> categoryList);
	
	//SELECT 
	public Categorie selCategoryById(int id);
	public Categorie selCategoryByTitle(String title);
		
	//INSERT
	public Categorie insertCategoria(Categorie category);
		
	//DELETE
	public void deleteCategoria(Categorie categoryList);

	

	
}
