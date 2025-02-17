package com.retail.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProductIsNotBelongsToTheSellerException extends RuntimeException {

	private String message;
}
