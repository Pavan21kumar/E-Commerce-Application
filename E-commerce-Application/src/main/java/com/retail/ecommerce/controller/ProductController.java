package com.retail.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecommerce.requestdto.ProductRequest;
import com.retail.ecommerce.responsedto.ProductResponse;
import com.retail.ecommerce.service.ProductService;
import com.retail.ecommerce.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

	private ProductService productService;

	@PreAuthorize("hasAuthority('SELLER')")
	@PostMapping("/products")
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(
			@Valid @RequestBody ProductRequest productRequest) {
		return productService.addProduct(productRequest);
	}
}
