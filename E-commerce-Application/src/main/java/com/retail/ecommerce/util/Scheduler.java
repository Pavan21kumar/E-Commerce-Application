package com.retail.ecommerce.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.retail.ecommerce.entity.Accesstoken;
import com.retail.ecommerce.entity.RefreshToken;
import com.retail.ecommerce.repository.AccessTokenRepo;
import com.retail.ecommerce.repository.RefreshTokenRepo;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@EnableScheduling
public class Scheduler {

	private AccessTokenRepo accessTokenRepo;
	private RefreshTokenRepo refreshTokenRepo;

	@Scheduled(fixedDelay = 60 * 60 * 1000l)
	public void removeAccessTokenScheduling() {
		// long currentTimestamp = Instant.now().getEpochSecond();

		List<Accesstoken> list = accessTokenRepo.findAllByExpirationLessThan(LocalDateTime.now()).stream()
				.map(accessToken -> {
					return accessToken;
				}).collect(Collectors.toList());
		if (!list.isEmpty()) {
			accessTokenRepo.deleteAll(list);
		}

	}

	@Scheduled(fixedDelay = 60 * 60 * 1000l)
	public void removeRefreshTokenScheduling() {

//		refreshTokenRepo.findAllByTokenParseJwtClaimsIssuedAtLessThan(new Date());
		long currentTimestamp = Instant.now().getEpochSecond();
		List<RefreshToken> refreshTokenList = refreshTokenRepo.findAllByExpirationLessThan(LocalDateTime.now()).stream()
				.map(refreshToken -> {
					return refreshToken;
				}).collect(Collectors.toList());
		if (!refreshTokenList.isEmpty()) {
			refreshTokenRepo.deleteAll(refreshTokenList);
		}
	}

}
