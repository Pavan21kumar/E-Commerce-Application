package com.retail.ecommerce.requestdto;

import jakarta.validation.constraints.NotNull;
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
	private int quantity;
	// private AvailabilityStatus status;
}
