package com.Ilibrary.BooksService.Controller.Jwt;

import java.io.Serializable;

//Corpo TOKEN REQUEST
public class JwtTokenRequest implements Serializable 
{

	public JwtTokenRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	private static final long serialVersionUID = -5616176897013108345L;

	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
