package com.retail.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.retail.ecommerce.requestdto.AuthRequest;
import com.retail.ecommerce.requestdto.UserRequest;
import com.retail.ecommerce.responsedto.AuthResponse;
import com.retail.ecommerce.responsedto.OtpRequest;
import com.retail.ecommerce.responsedto.UserResponse;
import com.retail.ecommerce.util.ResponseStructure;
import com.retail.ecommerce.util.SimpleResponseStructure;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

	ResponseEntity<SimpleResponseStructure> userRegister(UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OtpRequest otpRequest);

	ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest loginRequest);

	ResponseEntity<SimpleResponseStructure> logout(String accessToken, String refreshToken);

	ResponseEntity<ResponseStructure<AuthResponse>> refreshToken(String accessToken, String refreshToken);

}
