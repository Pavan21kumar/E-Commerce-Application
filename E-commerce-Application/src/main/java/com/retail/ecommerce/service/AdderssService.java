package com.retail.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.retail.ecommerce.enums.UserRole;
import com.retail.ecommerce.requestdto.AddressRequest;
import com.retail.ecommerce.responsedto.AddressContactResponse;
import com.retail.ecommerce.responsedto.AddressResponse;
import com.retail.ecommerce.responsedto.AddressSellerResponse;
import com.retail.ecommerce.responsedto.AddressUpdateResponse;
import com.retail.ecommerce.util.ResponseStructure;

import jakarta.validation.Valid;

public interface AdderssService {

	ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest);

	ResponseEntity<ResponseStructure<AddressSellerResponse>> findAddressBySeller(UserRole role);

	ResponseEntity<ResponseStructure<AddressUpdateResponse>> updateAddress(@Valid AddressRequest addressRequest,
			int addressId);

	ResponseEntity<ResponseStructure<List<AddressContactResponse>>> findCustomerAddress(UserRole role);

}
