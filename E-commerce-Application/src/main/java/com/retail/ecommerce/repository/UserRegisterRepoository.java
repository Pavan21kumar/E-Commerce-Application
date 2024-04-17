package com.retail.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecommerce.entity.User;

public interface UserRegisterRepoository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

	boolean existsByUsername(String name);

}
