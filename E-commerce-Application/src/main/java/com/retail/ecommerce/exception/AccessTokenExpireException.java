package com.retail.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccessTokenExpireException extends RuntimeException {

	private String message;
}
