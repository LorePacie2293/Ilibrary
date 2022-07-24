package com.Ilibrary.BooksService.Exception;

//Excpetion NOT FOUND
public class NotFoundException extends Exception{

	private static final long serialVersionUID = 701491241222044041L;
	private String msg = "Elemento ricercato non trovato";
	
	public NotFoundException() {
		super();
	}
	
	public NotFoundException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
