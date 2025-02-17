package com.retail.ecommerce.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossOrigin implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**") // Apply CORS configuration to all APIs, adjust the path as needed
				.allowedOrigins("http://localhost:5173") // Allow requests from this origin
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
				.allowCredentials(true)
				.allowedHeaders("*");
	}
}