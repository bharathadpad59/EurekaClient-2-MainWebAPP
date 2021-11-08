package com.example.webapp.exception;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = 69331750227896439L;
	HttpStatus status;
	String errorMsg;
	
	
	
	public RestException(HttpStatus badRequest, String errorMsg) {
		super();
		this.status = badRequest;
		this.errorMsg = errorMsg;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
