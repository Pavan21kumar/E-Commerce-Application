package com.retail.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CoverImageallreadyAddedException extends RuntimeException {

	private String message;
}