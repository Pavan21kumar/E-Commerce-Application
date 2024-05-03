package com.retail.ecommerce.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.retail.ecommerce.exception.CoverImageallreadyAddedException;
import com.retail.ecommerce.exception.ImageTypeNotCorrectexception;
import com.retail.ecommerce.exception.ImageTypeNotSpecifiedException;
import com.retail.ecommerce.util.ErrorStructure;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class AllImageCustomeExceptionHandler {

	private ErrorStructure<String> errorStructure;

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingCoverImageallreadyAddedException(
			CoverImageallreadyAddedException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("Cover image Is Allready Present").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingImageTypeNotCorrectexception(ImageTypeNotCorrectexception e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage(" imageType Is Not Correct").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingImageTypeNotSpecifiedException(
			ImageTypeNotSpecifiedException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage(" imageType Is Not Present ").setRootCouse(e.getMessage()));
	}
}
