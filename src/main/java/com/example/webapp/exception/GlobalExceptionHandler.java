package com.example.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler  {

	@ExceptionHandler(value = RestException.class)
	public  ResponseEntity<Object> getExcption(RestException restException) {
		return new ResponseEntity<>(restException,HttpStatus.NOT_FOUND);
		
	}
}
