package com.Ilibrary.BooksService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ilibrary.BooksService.Entity.Categorie;



public interface CategorieRepository extends JpaRepository<Categorie, Integer>{
	
	List<Categorie> findAll();
	Categorie findById(int categoriaId);
	Categorie findByCategoriaTitle(String categoriaTitle);

}
