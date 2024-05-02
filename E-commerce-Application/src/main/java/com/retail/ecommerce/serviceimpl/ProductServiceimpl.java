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
			if (product.getQuantity() > 10)
				product.setStatus(AvailabilityStatus.AVAILABLE);
			else if (product.getQuantity() == 0)
				product.setStatus(AvailabilityStatus.OUTOFSTOCK);
			else
				product.setStatus(AvailabilityStatus.FEWLEFT);
			productRepo.save(product);
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

}
