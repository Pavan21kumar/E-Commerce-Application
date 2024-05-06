package com.retail.ecommerce.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.retail.ecommerce.exception.ProductCategoryNotSpecifiedException;
import com.retail.ecommerce.util.ErrorStructure;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class AllProductCustomeExceptionHandler {

	private ErrorStructure<String> errorStructure;

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleProductCategoryNotSpecifiedException(
			ProductCategoryNotSpecifiedException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("product category is not mention..").setRootCouse(e.getMessage()));
	}
}
