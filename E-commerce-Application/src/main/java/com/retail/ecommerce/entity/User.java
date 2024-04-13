package com.retail.ecommerce.entity;

import com.retail.ecommerce.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

	private int userId;
	private String username;
	private String email;
	private String password;
	private UserRole role;
	private boolean isEmailVerified;
	private boolean isDeleted;

}
