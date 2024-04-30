package com.retail.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddressnotFoundByIdException extends RuntimeException {

	private String meggage;
}
