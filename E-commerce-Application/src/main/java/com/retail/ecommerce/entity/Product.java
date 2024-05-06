package com.retail.ecommerce.entity;

import com.retail.ecommerce.enums.AvailabilityStatus;
import com.retail.ecommerce.enums.ProductCategory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String productName;
	private String description;
	private double price;
	private int quantity;
	private AvailabilityStatus status;
	private ProductCategory category;
}
