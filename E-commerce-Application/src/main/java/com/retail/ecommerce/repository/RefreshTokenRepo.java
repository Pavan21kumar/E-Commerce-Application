package com.retail.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecommerce.entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {

	boolean existsByTokenAndIsBlocked(String rt, boolean b);

}
