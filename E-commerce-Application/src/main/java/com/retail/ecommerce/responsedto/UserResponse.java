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
public class UserResponse {

	private int userId;
	private String displayName;
	private String username;
	private String email;
	private UserRole role;
	private boolean isDeleted;
	private boolean isEmailVerified;
}
