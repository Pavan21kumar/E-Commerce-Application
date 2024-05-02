package com.retail.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.retail.ecommerce.requestdto.ProductRequest;
import com.retail.ecommerce.responsedto.ProductResponse;
import com.retail.ecommerce.util.ResponseStructure;

public interface ProductService {

	ResponseEntity<ResponseStructure<ProductResponse>> addProduct(ProductRequest productRequest);

	ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(ProductRequest productRequest, int productId);

	ResponseEntity<ResponseStructure<ProductResponse>> findProduct(int productId);

}
