package com.Ilibrary.BooksService.Exception;

public class DuplicateException extends Exception{

	private static final long serialVersionUID = -3080964638891004256L;
	
	private String message;
	
	public DuplicateException() {
		super();
	}
	
	public DuplicateException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
