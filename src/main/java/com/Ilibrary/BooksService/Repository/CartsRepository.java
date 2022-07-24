package com.Ilibrary.BooksService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ilibrary.BooksService.Entity.Books;
import com.Ilibrary.BooksService.Entity.Carts;
import com.Ilibrary.BooksService.Entity.Users;

public interface CartsRepository extends JpaRepository<Carts, Integer>{

	Carts findCartByUser(Users user);
	List<Carts> findBybooks(Books book);
	
}
