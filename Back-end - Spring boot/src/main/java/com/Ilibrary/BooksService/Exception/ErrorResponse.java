package com.Ilibrary.BooksService.Exception;

import java.time.LocalDate;
import java.util.Date;

public class ErrorResponse {

	private Date data = new Date();
	private int codice;
	private String messaggio;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public ErrorResponse() {}
	
	public ErrorResponse(Date data, String messaggio) {
		super();
		this.data = data;
		this.messaggio = messaggio;
	}

	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMsg(String msg) {
		this.messaggio = msg;
	}
	
	
	
}
