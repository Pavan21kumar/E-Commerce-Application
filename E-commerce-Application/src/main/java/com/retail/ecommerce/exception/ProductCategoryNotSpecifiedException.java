package com.retail.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductCategoryNotSpecifiedException extends RuntimeException {

	private String message;
}
