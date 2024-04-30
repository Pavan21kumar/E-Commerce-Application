package com.retail.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.retail.ecommerce.requestdto.AddressRequest;
import com.retail.ecommerce.responsedto.AddressContactsResponse;
import com.retail.ecommerce.responsedto.AddressResponse;
import com.retail.ecommerce.responsedto.AddressUpdateResponse;
import com.retail.ecommerce.util.ResponseStructure;

import jakarta.validation.Valid;

public interface AdderssService {

	ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@Valid AddressRequest addressRequest,
			String accessToken, String refreshToken);

	ResponseEntity<ResponseStructure<AddressContactsResponse>> findAddress(String accessToken, String refreshToken);

	ResponseEntity<ResponseStructure<AddressUpdateResponse>> updateAddress(@Valid AddressRequest addressRequest,
			int addressId);

}
