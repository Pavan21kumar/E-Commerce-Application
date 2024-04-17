package com.retail.ecommerce.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoleNotSpecifyException extends RuntimeException {

	private String message;
}
