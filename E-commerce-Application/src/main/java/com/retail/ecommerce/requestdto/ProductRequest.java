package com.retail.ecommerce.requestdto;

import com.retail.ecommerce.enums.ProductCategory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
	@NotNull
	private String productName;
	@NotNull
	private String description;
	@NotNull
	private double price;
	@NotNull
	@Positive
	private int quantity;
	// private AvailabilityStatus status;
	private ProductCategory category;
}
