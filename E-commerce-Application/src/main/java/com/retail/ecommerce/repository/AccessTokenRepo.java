package com.retail.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecommerce.entity.Accesstoken;

public interface AccessTokenRepo extends JpaRepository<Accesstoken, Integer> {

	boolean existsByTokenAndIsBlocked(String at, boolean b);

}
