package com.Ilibrary.BooksService.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CARTS")
public class Carts implements Serializable{

	private static final long serialVersionUID = -7084059327971863756L;

	public Carts() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CART_ID")
	private int cartId;

	@OneToOne
	@PrimaryKeyJoinColumn
	@JsonIgnore
	private Users user;

	//MANYTOMANY CON BOOKS
	@ManyToMany(fetch = FetchType.LAZY,
				cascade = {
						CascadeType.PERSIST,
						CascadeType.MERGE,

				},mappedBy = "listCarts")
	@OnDelete(action=OnDeleteAction.NO_ACTION)

	private List<Books> books = new ArrayList<>();
	
	//ADD BOOK TO ORDINE
	public void addBook(Books book) {
			
		//Aggiunta 1 book al carrello
		this.books.add(book);
		
	}
		
	//DELETE BOOK TO Carrello
	public void removeBook(Books book) {
			
		//Rimozione book da carrello
		this.books.remove(book);
		
	}
	
	//DELETE ALL BOOKS TO CART
	public boolean removeBookAll() {
				
		//Rimozione book da carrello
		return (this.books.removeAll(books) ? true : false);
		
	}
		
	//GET / SET
	public Users getUser() {
		return user;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public int getCartId() {
		return cartId;
	}

	public List<Books> getBooks() {
		return books;
	}

	public void setBooks(List<Books> books) {
		this.books = books;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
