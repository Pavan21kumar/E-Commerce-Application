package com.retail.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageTypeNotSpecifiedException extends RuntimeException {

	private String message;
}
