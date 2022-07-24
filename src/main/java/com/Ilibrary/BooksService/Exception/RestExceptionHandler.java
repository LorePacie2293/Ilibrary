package com.Ilibrary.BooksService.Exception;

import java.net.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

//Gestisce le exception lanciate dai controller
@ControllerAdvice
@RestController
public class RestExceptionHandler {
	
	//NOT FOUND
	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<ErrorResponse> exceptionNotFoundHeandler(Exception exc){
		
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setCodice(HttpStatus.NOT_FOUND.value());
		errorResponse.setMsg(exc.getMessage());
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	//BINDING VALIDATION
	@ExceptionHandler(BindingException.class)
	public final ResponseEntity<ErrorResponse> exceptionBindingHeandler(Exception exc){
		
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setCodice(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMsg(exc.getMessage());
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	//BINDING
	@ExceptionHandler(DuplicateException.class)
	public final ResponseEntity<ErrorResponse> exceptionDuplicateHeandler(Exception exc){
			
		ErrorResponse errorResponse = new ErrorResponse();
			
		errorResponse.setCodice(HttpStatus.NOT_ACCEPTABLE.value());
		errorResponse.setMsg(exc.getMessage());
			
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
	}
	
}
