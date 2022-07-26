package com.Ilibrary.BooksService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ilibrary.BooksService.Entity.Books;
import com.Ilibrary.BooksService.Entity.Carts;
import com.Ilibrary.BooksService.Entity.Categorie;


public interface BooksRepository extends JpaRepository<Books, Integer>{

	Books findById(int d);
	List<Books> findByBookTitleContaining(String title);
	List<Books> findByAuthorFnameContaining(String name);
	List<Books> findByAuthorLnameContaining(String surname);
	List<Books> findByReleasedYear(int year);
	List<Books> findByStockQuantity(int quantity);
	List<Books> findByPages(int pages);
	List<Books> findByPrice(double price);
	List<Books>	findBylistCarts(Carts cart);
	
	List<Books> findBooksBylistOrdiniOrdineId(int ORDINE_ID);
	List<Books> findBooksBybooksCategory(Categorie category);
}
