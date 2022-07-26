package com.Ilibrary.BooksService.Controller.Jwt;

import java.io.Serializable;

import lombok.Data;

//Corpo TOKEN RESPONSE
public class JwtTokenResponse implements Serializable 
{

	private static final long serialVersionUID = 8317676219297719109L;

	public JwtTokenResponse(String token) {
		super();
		this.token = token;
	}
	
	private final String token;



	public String getToken() {
		return token;
	}
	
	
}