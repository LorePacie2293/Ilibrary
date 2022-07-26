
package com.Ilibrary.BooksService.Exception;

//Json di risposta alla BINDING VALIDATION
public class BindingException extends Exception{

	private static final long serialVersionUID = -4101587648409374306L;
	
	private String message;
	
	public BindingException() {
		super();
	}
	
	public BindingException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
