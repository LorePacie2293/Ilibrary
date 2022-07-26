package com.Ilibrary.BooksService.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "BOOKS")
public class Books implements Serializable{

public Books() {}
	
	public Books(String bookTitle, String authorFname, String authorLname, int releasedYear, int stockQuantity, int pages, String imgUrl,Set<Categorie> booksCategory) {
		super();
		this.bookTitle = bookTitle;
		this.authorFname = authorFname;
		this.authorLname = authorLname;
		this.releasedYear = releasedYear;
		this.stockQuantity = stockQuantity;
		this.pages = pages;
		this.imgUrl = imgUrl;
		this.booksCategory = booksCategory;
	}
	
	private static final long serialVersionUID = 7054846598650452728L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOK_ID")
	private int bookId;
	
	@Column(name = "TITLE")
	@Size(min=2, max=50, message="{Size.Books.Title.Validation}")
	@NotNull(message = "{NotNull.Books.Title.Validation}")
	private String bookTitle;
	
	@Column(name = "AUTHOR_FNAME")
	@Size(min=3, max=25, message="{Size.Books.FirstName.Validation}")
	@NotNull(message = "{NotNull.Books.FirstName.Validation}")
	private String authorFname;

	@Column(name = "AUTHOR_LNAME")
	@Size(min=3, max=25, message="{Size.Books.LastName.Validation}")
	@NotNull(message = "{NotNull.Books.LastName.Validation}")
	private String authorLname;
	
	@Column(name = "RELEASED_YEAR")
	@NotNull(message = "{NotNull.Books.ReleasedYear.Validation}")
	@Min(value = 1, message = "{ErrorFormat.Books.ReleasedYear.Validation}")
	private int releasedYear;
	
	@Column(name = "STOCK_QUANTITY")
	@NotNull(message = "{NotNull.Books.StockQuantity.Validation}")
	@Min(value = 1, message = "{ErrorFormat.Books.StockQuantity.Validation}")
	private int stockQuantity;
	
	@Column(name = "PAGES")
	@NotNull(message = "{NotNull.Books.Pages.Validation}")
	@Min(value = 1, message = "{ErrorFormat.Books.Pages.Validation}")
	private int pages;
	
	@Column(name = "PRICE")
	@NotNull(message = "{NotNull.Books.Price.Validation}")
	@Min(value = 1, message = "{ErrorFormat.Books.Price.Validation}")
	double price;
	
	@Column(name = "imgUrl", unique = false)
	@NotNull(message = "{NotNull.Books.ImgUrl.Validation}")
	private String imgUrl;
	
	
	//MANYTOMANY CON ORDINI
	@ManyToMany(fetch = FetchType.LAZY,
				  cascade = {
				    		CascadeType.PERSIST,
				          CascadeType.MERGE
			},
				  mappedBy = "bookListOrder")
	@JsonIgnore
	private Set<Ordini> listOrdini = new HashSet<>();
	
	//MANYTOMANY CON CATEGORIE
	@ManyToMany(fetch= FetchType.LAZY, cascade = {
			 CascadeType.PERSIST,
	         CascadeType.MERGE
	    },targetEntity = Categorie.class)
	@JoinTable(name = "categorieInBooks",
	joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "book_id")},
	inverseJoinColumns = {@JoinColumn (name = "categoria_id", referencedColumnName = "categoria_id", nullable=false, insertable=false)})
	@Size(min = 1, message = "{NotNull.Books.Category.Validation}")
	private Set<Categorie> booksCategory= new HashSet<>();
	
	//MANY TO MANY CON CARTS
	@ManyToMany(fetch= FetchType.LAZY, cascade = {
			CascadeType.PERSIST,
	        CascadeType.MERGE
	},targetEntity = Carts.class)
		@JoinTable(name = "BooksInCart",
			joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "book_id")},
			inverseJoinColumns = {@JoinColumn (name = "CART_ID", referencedColumnName = "CART_ID")})
	@JsonIgnore
	private List<Carts> listCarts = new ArrayList<>();
	
	
	//ADD CART to books
	public void addCart(Carts cart) {
			
		//Aggiunta 1 book al carrello
		this.listCarts.add(cart);
			
	}
	
	//REMOVE cart TO books
	public void removeCart(Carts cart) {
		this.listCarts.remove(cart);
	}
	
	//ADD ORDINE to books
	public void addOrder(Ordini ordine) {
				
		//Aggiunta 1 book ad ordine
		this.listOrdini.add(ordine);
				
	}
	
	//ADD e DELETE COTEGORIA
	public void addCategoria(Categorie categoria) {
		this.booksCategory.add(categoria);
	}
	
	//----- GET -----
	
	public Set<Categorie> getBooksCategory() {
		return booksCategory;
	}

	public void setBooksCategory(Set<Categorie> booksCategory) {
		this.booksCategory = booksCategory;
	}

	public int getBookId() {
		return bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public String getAuthorFname() {
		return authorFname;
	}

	public String getAuthorLname() {
		return authorLname;
	}

	public int getReleasedYear() {
		return releasedYear;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public int getPages() {
		return pages;
	}
	
	//SET
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public void setAuthorFname(String authorFname) {
		this.authorFname = authorFname;
	}

	public void setAuthorLname(String authorLname) {
		this.authorLname = authorLname;
	}

	public List<Carts> getListCarts() {
		return listCarts;
	}

	public void setListCarts(List<Carts> listCarts) {
		this.listCarts = listCarts;
	}
	

	public void setReleasedYear(int releasedYear) {
		this.releasedYear = releasedYear;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public Set<Ordini> getListOrdini() {
		return listOrdini;
	}

	public void setListOrdini(Set<Ordini> listOrdini) {
		this.listOrdini = listOrdini;
	}

	
	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String img) {
		this.imgUrl = img;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
