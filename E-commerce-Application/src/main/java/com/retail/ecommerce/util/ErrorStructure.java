package com.retail.ecommerce.util;

import org.springframework.stereotype.Component;

@Component
public class ErrorStructure<T> {

	private int statusCode;
	private String message;
	private T rootCouse;

	public int getStatusCode() {
		return statusCode;
	}

	public ErrorStructure<T> setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ErrorStructure<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getRootCouse() {
		return rootCouse;
	}

	public ErrorStructure<T> setRootCouse(T rootCouse) {
		this.rootCouse = rootCouse;
		return this;
	}

}
