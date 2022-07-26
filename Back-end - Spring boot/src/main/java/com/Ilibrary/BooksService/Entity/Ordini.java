package com.Ilibrary.BooksService.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "ORDINI")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Ordini implements Serializable{

	public Ordini() {}
	
	public Ordini(double ordinePrice, String username) {
		super();
		this.ordineData = new Date();
		this.ordinePrice = ordinePrice;
		this.username = username;
	}
	private static final long serialVersionUID = 5717542325025229659L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDINE_ID")
	private int ordineId;

	@Column(name = "ORDINE_DATA")
	@Temporal(TemporalType.DATE)
	private Date ordineData;
	
	
	@Column(name = "ORDINE_PRICE")
	@NotNull(message = "{NotNull.Ordini.Ordineprice.Validation}")
	private double ordinePrice;
	
	//MOLTI A 1 con tabella USERS
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	@JsonIgnore
	private Users user;
	
	//tiene traccia dell'username dell'user, problema SERIALIZAZZIONE
	private String username;
	
	@ManyToMany(fetch= FetchType.LAZY, 
	        cascade =
	        {
	        		CascadeType.PERSIST,
			        CascadeType.MERGE
	           
	        },
	        targetEntity = Books.class)
	@JoinTable(name = "booksInOrder",
			joinColumns = { @JoinColumn(name = "ordine_id", referencedColumnName = "ordine_id")},
			inverseJoinColumns = {@JoinColumn (name = "book_id", referencedColumnName = "book_id")})

	private List<Books> bookListOrder = new ArrayList<>();
	
	
	
	//ADD BOOK TO ORDINE
	public void addBook(List<Books> books) {
		
		for(Books book : books) {
			
			//Aggiunta 1 book all'ordine
			this.bookListOrder.add(book);
		}
	}
	
	//DELETE BOOK TO ORDINE
	public void removeBook(int bookId) {
		
		//Ricerca book con ID stabilito
		Books book = this.bookListOrder.stream().filter(b -> b.getBookId() == bookId).findFirst().orElse(null);
		
		//Se il book è stato trovato
		if(book != null) {
			
			//Rimozione book da ordine
			this.bookListOrder.remove(book);
		}
	}
	
	//GET
	public double getOrdinePrice() {
		return ordinePrice;
	}

	public int getOrdineId() {
		return ordineId;
	}
	
	
	public void setOrdineId(int ordineId) {
		this.ordineId = ordineId;
	}

	public void setOrdineData(Date ordineData) {
		this.ordineData = ordineData;
	}

	public void setOrdinePrice(double ordinePrice) {
		this.ordinePrice = ordinePrice;
	}
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Date getOrdineData() {
		return ordineData;
	}
	
	
	public List<Books> getBookListOrder() {
		return bookListOrder;
	}
	
	public void setBookListOrder(List<Books> bookListOrder) {
		this.bookListOrder = bookListOrder;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
