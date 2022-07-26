package com.Ilibrary.BooksService.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.Ilibrary.BooksService.Entity.Books;
import com.Ilibrary.BooksService.Entity.Ordini;
import com.Ilibrary.BooksService.Entity.Users;


public interface OrdiniService {
	
	public List<Ordini> selOrdiniAll();
	
	public Ordini selByOrdineId(int id);
	public Set<Ordini> selByUsername(Users user);
	public List<Ordini> selByOrdinePrice(double price);
	public List<Ordini> selByOrdineDate(Date data);
	public void insertOrdine(Ordini ordine);
	public void deleteOrdine(Ordini ordine);
	public double calcTotOrdine(List<Books> books);
}
