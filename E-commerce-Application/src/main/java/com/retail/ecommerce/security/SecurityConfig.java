package com.retail.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.retail.ecommerce.jwt.JwtFilter;
import com.retail.ecommerce.jwt.JwtService;
import com.retail.ecommerce.jwt.RefreshFilter;
import com.retail.ecommerce.repository.AccessTokenRepo;
import com.retail.ecommerce.repository.RefreshTokenRepo;

import ch.qos.logback.core.boolex.Matcher;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

	private CustomeUserDetailservice userDetailservice;
	private AccessTokenRepo accessTokenRepo;
	private RefreshTokenRepo refreshTokenRepo;
	private JwtService jwtService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);// more secured and more use and 12 times hashing is basic secure
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailservice);
		return provider;

	}

	@Bean
	@Order(2)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("im in Main  filter*****************************");
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/v1/user/register", "/api/v1/login", "/api/v1/verify-email")
								.permitAll().anyRequest().authenticated())
				.sessionManagement(management -> {
					management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.addFilterBefore(new JwtFilter(jwtService, accessTokenRepo, refreshTokenRepo),
						UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(authenticationProvider()).build();
	}

	@Bean
	@Order(1)
	SecurityFilterChain refreshFilterChain(HttpSecurity http) throws Exception {

		System.out.println("im in refresh filter*****************************");
		return http.csrf(csrf -> csrf.disable())
				.securityMatchers(matcher -> matcher.requestMatchers("/api/v1/login/refresh/**"))
				.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
				.sessionManagement(session -> {
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.addFilterBefore(new RefreshFilter(jwtService, refreshTokenRepo),
						UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(authenticationProvider()).build();
	}

//	@Bean
//	@Order(3)
//	SecurityFilterChain fileterChain(HttpSecurity httpSecurity) throws Exception {
//		System.out.println("im in Last filter*****************************");
//		return httpSecurity.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
//				.sessionManagement(session -> {
//					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//				})
//				.addFilterBefore(new JwtFilter(jwtService, accessTokenRepo, refreshTokenRepo),
//						UsernamePasswordAuthenticationFilter.class)
//				.authenticationProvider(authenticationProvider()).build();
//
//	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
