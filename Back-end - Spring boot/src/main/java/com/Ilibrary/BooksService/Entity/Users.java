package com.Ilibrary.BooksService.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = "user_email"))
public class Users implements Serializable{

	private static final long serialVersionUID = -669125978566813916L;
	
	public Users(String userFirstName, String userLastName, String userEmail, String userPassword, String username, String role) {
		super();
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.username = username;
		this.userRole = role;
	}
	
	public Users() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private int userId;
	
	@Column(name = "USER_FNAME")
	@Size(min=4, max=25, message="{Size.Users.FisrtName.Validation}")
	@NotNull(message = "{NotNull.Users.FisrtName.Validation}")
	private String userFirstName;
	
	@Column(name = "USER_LNAME")
	@Size(min=5, max=25, message="{Size.Users.LastName.Validation}")
	@NotNull(message = "{NotNull.Users.LastName.Validation}")
	private String userLastName;
	
	@Column(name = "USERNAME")
	@Size(min=5, max=25, message="{Size.Users.Username.Validation}")
	@NotNull(message = "{NotNull.Users.Username.Validation}")
	private String username;
	
	@Column(name = "USER_EMAIL")
	@Size(min=5, max=25, message="{Size.Users.Email.Validation}")
	@NotNull(message = "{NotNull.Users.Email.Validation}")
	private String userEmail;
	
	@Column(name = "USER_PASSWORD")
	@NotNull(message = "{NotNull.Users.Password.Validation}")
	private String userPassword;
	
	@Column(name = "USER_ROLE")
	private String userRole;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Carts cart;
	

	//1 A MOLTI con tabella ORDINI
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Ordini> lisOrdini = new HashSet<>();
	
	//Aggiunge 1 ordine all'utente
	public void addOrdine(Ordini ordine) {
		this.lisOrdini.add(ordine);
	}

	//GET / SET
	public Set<Ordini> getLisOrdini() {
		return lisOrdini;
	}

	public void setLisOrdini(Set<Ordini> lisOrdini) {
		this.lisOrdini = lisOrdini;
	}

	public Carts getCart() {
		return cart;
	}

	public void setCart(Carts cart) {
		this.cart = cart;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	
}
