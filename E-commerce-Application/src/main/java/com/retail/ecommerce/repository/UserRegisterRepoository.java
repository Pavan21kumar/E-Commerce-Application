package com.retail.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecommerce.entity.Product;
import com.retail.ecommerce.entity.User;

public interface UserRegisterRepoository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

	boolean existsByUsername(String name);

	Optional<User> findByUsername(String username);

	boolean existsByPassword(String password);

}
