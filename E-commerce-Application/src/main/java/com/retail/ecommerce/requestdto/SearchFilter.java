package com.retail.ecommerce.requestdto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchFilter {

	private Integer minPrice;
	private Integer maxPrice;
	private String category;
	private String status;
	private Integer rating;
	private Integer discount;
}
