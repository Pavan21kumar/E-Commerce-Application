package com.retail.ecommerce.responsedto;

import java.util.List;

import com.retail.ecommerce.enums.AvailabilityStatus;
import com.retail.ecommerce.enums.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {

	private int productId;
	private String productName;
	private String description;
	private double price;
	private int quantity;
	private AvailabilityStatus status;
	private List<String> immagesLinks;
	private String coverImage;
	private ProductCategory category;

}
