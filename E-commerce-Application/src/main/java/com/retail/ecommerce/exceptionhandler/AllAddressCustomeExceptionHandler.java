package com.retail.ecommerce.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.retail.ecommerce.exception.AddressAllreadyAddedException;
import com.retail.ecommerce.exception.AddressNotFoundException;
import com.retail.ecommerce.exception.InvalidRoleForThisCustomerException;
import com.retail.ecommerce.exception.InvalidSellerRoleException;
import com.retail.ecommerce.exception.NoAddressFoundException;
import com.retail.ecommerce.util.ErrorStructure;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class AllAddressCustomeExceptionHandler {

	private ErrorStructure<String> errorStructure;

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleInvalidSellerRoleException(InvalidSellerRoleException e) {

		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("Role Is Not Match with Authenticated User").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleInvalidRoleForThisCustomerException(
			InvalidRoleForThisCustomerException e) {

		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("Role Is Not Match with Authenticated User").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleNoAddressFoundException(NoAddressFoundException e) {

		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("No Address Found.. please Add Address ..").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleAddressNotFoundException(AddressNotFoundException e) {

		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("No Address Found..  ..").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleAddressAllreadyAddedException(AddressAllreadyAddedException e) {

		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("Address Allready Added plaese Update That Address..  ..").setRootCouse(e.getMessage()));
	}
}
