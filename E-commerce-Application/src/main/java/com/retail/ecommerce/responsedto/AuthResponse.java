package com.retail.ecommerce.responsedto;

import com.retail.ecommerce.enums.UserRole;

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
public class AuthResponse {
	private int userId;
	private String username;
	private UserRole userRole;
	private long accessExpiration;
	private long refreshExpiration;

}
