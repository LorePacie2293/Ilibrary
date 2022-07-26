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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "CATEGORIE")
public class Categorie implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 73611352566263078L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CATEGORIA_ID")
	private int categoriaId;
	
	@Column(name = "CATEGORIA_TITLE")
	private String categoriaTitle;
	
	//MANYTOMANY CON ORDINI
	@ManyToMany(fetch = FetchType.LAZY,
			   cascade = {
				
					  CascadeType.PERSIST,
					  CascadeType.MERGE
			   },mappedBy = "booksCategory")
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private List<Books> listBooks = new ArrayList<>();
	
	public int getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getCategoriaTitle() {
		return categoriaTitle;
	}

	public void setCategoriaTitle(String categoriaTitle) {
		this.categoriaTitle = categoriaTitle;
	}

	public List<Books> getListBooks() {
		return listBooks;
	}

	public void setListBooks(List<Books> listBooks) {
		this.listBooks = listBooks;
	}
}
