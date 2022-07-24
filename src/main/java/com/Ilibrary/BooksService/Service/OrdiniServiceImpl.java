package com.Ilibrary.BooksService.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Ilibrary.BooksService.Entity.Books;
import com.Ilibrary.BooksService.Entity.Ordini;
import com.Ilibrary.BooksService.Entity.Users;
import com.Ilibrary.BooksService.Repository.OrdiniRepository;


@Service
@Transactional(readOnly = true)
public class OrdiniServiceImpl implements OrdiniService{
	
	@Autowired
	OrdiniRepository ordiniRepository;
	
	@Override
	public List<Ordini> selOrdiniAll() {
		return ordiniRepository.findAll();
	}
	
	@Override
	public Ordini selByOrdineId(int id) {
		return ordiniRepository.findById(id);
	}

	@Override
	public List<Ordini> selByOrdinePrice(double price) {
		return ordiniRepository.findByOrdinePrice(price);
	}

	@Override
	public List<Ordini> selByOrdineDate(Date data){
		return ordiniRepository.findByOrdineData(data);
	}
	
	@Override
	@Transactional
	public void insertOrdine(Ordini ordine) {
		 ordiniRepository.save(ordine);
	}

	@Override
	@Transactional
	public void deleteOrdine(Ordini ordine) {
		ordiniRepository.delete(ordine);
	}

	@Override
	public double calcTotOrdine(List<Books> books) {
		
		double tot = 0;
		
		for(Books book : books) {
			tot += book.getPrice();
		}
		
		return tot;
	}

	@Override
	public Set<Ordini> selByUsername(Users user) {
		
		return user.getLisOrdini();
	}

}
