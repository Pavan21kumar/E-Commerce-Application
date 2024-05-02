package com.retail.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PreAuthorize("hasAuthority('SELLER')")
	@PutMapping("/{productId}/products")
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(@RequestBody ProductRequest productRequest,@PathVariable int productId){
		return productService.updateProduct(productRequest,productId);
	}
	@GetMapping("/{productId}/products")
	public ResponseEntity<ResponseStructure<ProductResponse>> findProduct(@PathVariable int productId){
		
		return productService.findProduct(productId);
	}
}
