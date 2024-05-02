package com.retail.ecommerce.serviceimpl;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.retail.ecommerce.entity.Product;
import com.retail.ecommerce.entity.Seller;
import com.retail.ecommerce.enums.AvailabilityStatus;
import com.retail.ecommerce.exception.AccessTokenExpireException;
import com.retail.ecommerce.exception.ProductIsNotFoundException;
import com.retail.ecommerce.exception.UserIsNotLoginException;
import com.retail.ecommerce.repository.ProductRepository;
import com.retail.ecommerce.repository.SellerRepository;
import com.retail.ecommerce.repository.UserRegisterRepoository;
import com.retail.ecommerce.requestdto.ProductRequest;
import com.retail.ecommerce.responsedto.ProductResponse;
import com.retail.ecommerce.service.ProductService;
import com.retail.ecommerce.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceimpl implements ProductService {

	private ProductRepository productRepo;
	private UserRegisterRepoository userRepo;
	private SellerRepository sellerRepo;
	private ResponseStructure<ProductResponse> productResponse;

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(ProductRequest productRequest) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.isAuthenticated())
			throw new UserIsNotLoginException("user Is Not Login");

		String username = authentication.getName();
		return userRepo.findByUsername(username).map(user -> {

			Product product = mapToProduct(productRequest);
			product.setStatus(setStatus(product.getQuantity()));
			Seller seller = (Seller) user;
			if (seller.getProduct() == null)
				seller.setProduct(Arrays.asList(product));
			else
				seller.getProduct().add(product);
			sellerRepo.save(seller);

			return ResponseEntity.ok(productResponse.setStatusCode(HttpStatus.OK.value()).setMessage("product is Saved")
					.setData(MapToProductResponse(product)));

		}).orElseThrow(
				() -> new AccessTokenExpireException("AccessToken Is Expired please refresh AccessToken  Again.."));

	}

	private ProductResponse MapToProductResponse(Product product) {
		return ProductResponse.builder().productId(product.getProductId()).productName(product.getProductName())
				.description(product.getDescription()).price(product.getPrice()).quantity(product.getQuantity())
				.status(product.getStatus()).build();
	}

	private Product mapToProduct(ProductRequest productRequest) {

		return Product.builder().productName(productRequest.getProductName())
				.description(productRequest.getDescription()).price(productRequest.getPrice())
				.quantity(productRequest.getQuantity()).build();
	}

	private AvailabilityStatus setStatus(int quantity) {

		if (quantity > 10)
			return AvailabilityStatus.AVAILABLE;
		else if (quantity == 0)
			return AvailabilityStatus.OUTOFSTOCK;
		else
			return AvailabilityStatus.FEWLEFT;

	}

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(ProductRequest productRequest,
			int productId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.isAuthenticated())
			throw new UserIsNotLoginException("user Is Not Login");

		String username = authentication.getName();
		return userRepo.findByUsername(username).map(user -> {

			return productRepo.findById(productId).map(product -> {

				product = mapToProductRequestToProduct(product, productRequest);

				productRepo.save(product);
				return ResponseEntity.ok(productResponse.setStatusCode(HttpStatus.OK.value())
						.setMessage("Product data Is Updated..").setData(MapToProductResponse(product)));
			}).orElseThrow(() -> new ProductIsNotFoundException("product is Not found By Given Id"));
		}).orElseThrow(
				() -> new AccessTokenExpireException("AccessToken Is Expired please refresh AccessToken  Again.."));

	}

	private Product mapToProductRequestToProduct(Product product, ProductRequest productRequest) {

		product.setProductId(product.getProductId());
		product.setProductName(productRequest.getProductName());
		product.setDescription(productRequest.getDescription());
		product.setPrice(productRequest.getPrice());
		product.setQuantity(productRequest.getQuantity());
		product.setStatus(setStatus(productRequest.getQuantity()));

		return product;
	}

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> findProduct(int productId) {

		return productRepo.findById(productId).map(product -> {
			return ResponseEntity.status(HttpStatus.FOUND).body(productResponse.setStatusCode(HttpStatus.FOUND.value())
					.setMessage("Product Is Found..").setData(MapToProductResponse(product)));
		}).orElseThrow(() -> new ProductIsNotFoundException("product Is Not Found........"));
	}

}
