package com.retail.ecommerce.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RefreshToken {

	private int tokenId;
	private Token token;
	private long expiration;
}
