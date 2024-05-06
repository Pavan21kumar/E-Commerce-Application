package com.retail.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoFileFoundException extends RuntimeException {

	private String message;
}
