package com.Ilibrary.BooksService.Service;

import java.awt.print.Book;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Ilibrary.BooksService.Entity.Books;
import com.Ilibrary.BooksService.Entity.Carts;
import com.Ilibrary.BooksService.Entity.Categorie;
import com.Ilibrary.BooksService.Entity.Ordini;


public interface BooksService {
	
	//ALL
	public List<Books> selBookAll();
	public byte[] codImage(byte[] file) throws IOException;
	
	//SELECT 
	public Books selBookById(int id);
	public List<Books> selBookByTitle(String title);
	public List<Books> selByAuthorFname(String name);
	public List<Books> selByAuthorLname(String surnamename);
	public List<Books> selByReleasedYear(int year);
	public List<Books> selByStockQuantity(int quantity);
	public List<Books> selByPages(int pages);
	public List<Books> selByPrice(double price);
	public List<Books> selBooksInCart(Carts cart);
	public List<Books> selBooksInOrder(int orderId);
	public List<Books> selBooksInCategory(Categorie category);

 	//INSERT
	public void insertBook(Books book);
	
	//UPDATE
	public void updateBook(Books book);
	
	//DELETE
	public void deleteBook(Books book);
	
	//AGGIUNGE per ogni BOOK ll nuovo ordine in cui verrà inserito
	public void insertOrderToBooks(List<Books> books, Ordini ordine);

}
