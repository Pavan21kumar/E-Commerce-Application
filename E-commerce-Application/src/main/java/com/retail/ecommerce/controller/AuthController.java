package com.retail.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecommerce.jwt.JwtService;
import com.retail.ecommerce.requestdto.AuthRequest;
import com.retail.ecommerce.requestdto.UserRequest;
import com.retail.ecommerce.responsedto.AuthResponse;
import com.retail.ecommerce.responsedto.OtpRequest;
import com.retail.ecommerce.responsedto.UserResponse;
import com.retail.ecommerce.service.AuthService;
import com.retail.ecommerce.util.ResponseStructure;
import com.retail.ecommerce.util.SimpleResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

	private AuthService userService;
	private JwtService jwtService;

	@PostMapping("/user/register")
	public ResponseEntity<SimpleResponseStructure> userRegister(@Valid @RequestBody UserRequest userRequest) {
		return userService.userRegister(userRequest);
	}

	@PostMapping("/verify-email")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(@RequestBody OtpRequest otpRequest) {
		return userService.verifyOTP(otpRequest);
	}

//	@GetMapping("/getToken")
//	public String accessingToken() {
//		return jwtService.generateAccessToken("kumar");
//
//	}

	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponse>> login(@Valid @RequestBody AuthRequest loginRequest,
			@CookieValue(name = "at", required = false) String accessToken,
			@CookieValue(name = "rt", required = false) String refreshToken) {
		return userService.login(loginRequest, accessToken, refreshToken);
	}

	@PostMapping("/logout")
	public ResponseEntity<SimpleResponseStructure> logout(
			@CookieValue(name = "at", required = false) String accessToken,
			@CookieValue(name = "rt", required = false) String refreshToken) {
		return userService.logout(accessToken, refreshToken);
	}

	@PostMapping("/login/refresh")
	public ResponseEntity<ResponseStructure<AuthResponse>> refreshToken(
			@CookieValue(name = "at", required = false) String accessToken,
			@CookieValue(name = "rt", required = false) String refreshToken) {
		return userService.refreshToken(accessToken, refreshToken);

	}

}
