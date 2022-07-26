package com.Ilibrary.BooksService.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Ilibrary.BooksService.Entity.Categorie;
import com.Ilibrary.BooksService.Repository.CategorieRepository;



@Service
@Transactional(readOnly = true)
public class CategorieServiceImpl implements CategorieService {
	

	@Autowired 
	CategorieRepository categorieRepository;
	
	@Override
	public List<Categorie> selCategoryAll(){
		return categorieRepository.findAll();
	}
	
	@Override
	public Categorie selCategoryById(int id) {
		return categorieRepository.findById(id);
	}
	
	@Override
	public Categorie selCategoryByTitle(String title){
		return categorieRepository.findByCategoriaTitle(title);
	}
	
	@Override
	@Transactional
	public Categorie insertCategoria(Categorie categoria) {
		
		
		return categorieRepository.save(categoria);
	}
	
	@Override
	@Transactional
	public void deleteCategoria(Categorie categoria) {
		categorieRepository.delete(categoria);
	}
	
	//Controllo validita categoria
	@Override
	public boolean checkInsertcategory(Set<Categorie> categoryList) {
		
		Categorie categoryTemp;
		boolean checkResult = false;
		
		for( Categorie category : categoryList) {
			categoryTemp = categorieRepository.findByCategoriaTitle(category.getCategoriaTitle());
			
			if(categoryTemp != null) {
				checkResult = true;
			}
			else {
				checkResult = false;
			}
			
		}
		return checkResult;
	}
}
