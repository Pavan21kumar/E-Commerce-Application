package com.retail.ecommerce.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.retail.ecommerce.entity.User;

@Configuration
public class CacheBeanConfig {

	@Bean
	CacheStore<String> otpCache() {
		return new CacheStore<String>(2);
	}

	@Bean
	CacheStore<User> userCache() {
		return new CacheStore<User>(10);
	}
}
