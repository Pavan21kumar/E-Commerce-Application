package com.retail.ecommerce.jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.retail.ecommerce.exception.InvalidCreadentials;
import com.retail.ecommerce.repository.AccessTokenRepo;
import com.retail.ecommerce.repository.RefreshTokenRepo;
import com.retail.ecommerce.util.ErrorStructure;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private JwtService jwtService;
	private AccessTokenRepo accessTokenRepo;
	private RefreshTokenRepo refreshTokenRepo;

	@Override // FilterChain is Belongs to Servlet FilterChain
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String at = null;
		String rt = null;
		if (request.getCookies() != null) {
			for (Cookie c : request.getCookies()) {
				if (c.getName().equals("at")) {

					at = c.getValue();
				}
				if (c.getName().equals("rt")) {
					rt = c.getValue();
				}
			}
		}
		if (at != null && rt != null) {
			if (accessTokenRepo.existsByTokenAndIsBlocked(at, true)
					&& refreshTokenRepo.existsByTokenAndIsBlocked(rt, true))
				throw new InvalidCreadentials("invalid Creadentials  please enter Correct Details....");

			String userName = jwtService.getUserName(at);
			String role = jwtService.getRole(at);
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null && role != null) {

				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, null,
						Collections.singleton(new SimpleGrantedAuthority(role)));
				token.setDetails(new WebAuthenticationDetails(request));

				SecurityContextHolder.getContext().setAuthentication(token);

			}

		}
		try {
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, " JWT token Expaired..");

		} catch (JwtException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");

		}

	}

}
