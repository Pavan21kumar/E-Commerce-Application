package com.retail.ecommerce.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecommerce.entity.Accesstoken;

public interface AccessTokenRepo extends JpaRepository<Accesstoken, Integer> {

	boolean existsByTokenAndIsBlocked(String at, boolean b);

	Optional<Accesstoken> findByToken(String accessToken);

	List<Accesstoken> findAllByExpirationLessThan(LocalDateTime date);

}
