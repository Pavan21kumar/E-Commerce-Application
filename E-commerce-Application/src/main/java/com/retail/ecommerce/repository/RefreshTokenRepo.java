package com.retail.ecommerce.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecommerce.entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {

	boolean existsByTokenAndIsBlocked(String rt, boolean b);

	Optional<RefreshToken> findByToken(String refreshToken);

	List<RefreshToken> findAllByExpirationLessThan(LocalDateTime date);

//	List<RefreshToken> findAllByTokenParseJwtClaimsIssuedAtLessThan(Date date);

}
