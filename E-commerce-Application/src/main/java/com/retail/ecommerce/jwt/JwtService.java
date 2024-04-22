package com.retail.ecommerce.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${myapp.jwt.secret}")
	private String secrete;

	@Value("${myapp.jwt.access.expairation}")
	private long accesExpairation;

	@Value("${myapp.jwt.refresh.expairation}")
	private long refreshExpairation;

	private Key getSignatureKey() {

		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secrete));

	}

	private String generateToken(long expairation, String userName) {
		return Jwts.builder().setClaims(new HashMap<>()).setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expairation))
				.signWith(getSignatureKey(), SignatureAlgorithm.HS512).compact();
	}

	public String generateAccessToken(String userName) {
		return generateToken(accesExpairation, userName);
	}

	public String generateRefreshToken(String userName) {
		return generateToken(refreshExpairation, userName);
	}

}
