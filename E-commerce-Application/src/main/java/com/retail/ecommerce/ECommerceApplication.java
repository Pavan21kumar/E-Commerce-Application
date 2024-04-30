package com.retail.ecommerce;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ECommerceApplication {
	@Value("${spring.mail.password}")
	private String name;

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);

		// ECommerceApplication bean = run.getBean(ECommerceApplication.class);

	}

}
