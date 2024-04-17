package com.retail.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.retail.ecommerce.requestdto.UserRequest;
import com.retail.ecommerce.responsedto.OtpRequest;
import com.retail.ecommerce.responsedto.UserResponse;
import com.retail.ecommerce.util.ResponseStructure;
import com.retail.ecommerce.util.SimpleResponseStructure;

public interface AuthService {

	ResponseEntity<SimpleResponseStructure> userRegister(UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OtpRequest otpRequest);

}
