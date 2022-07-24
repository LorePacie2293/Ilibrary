package com.Ilibrary.BooksService.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ilibrary.BooksService.Entity.Ordini;
import com.Ilibrary.BooksService.Entity.Users;

public interface OrdiniRepository extends JpaRepository<Ordini, Integer>{
	
	Ordini findById(int id);
	List<Ordini> findByOrdinePrice(double price);
	List<Ordini> findByOrdineData(Date data);
	Set<Ordini> findByUser(Users user);
	
	//Set<Ordini> findByBookListOrder(int book);
	
}
