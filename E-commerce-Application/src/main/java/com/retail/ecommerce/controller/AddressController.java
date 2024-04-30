package com.retail.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecommerce.enums.UserRole;
import com.retail.ecommerce.requestdto.AddressRequest;
import com.retail.ecommerce.responsedto.AddressContactsResponse;
import com.retail.ecommerce.responsedto.AddressResponse;
import com.retail.ecommerce.responsedto.AddressSellerResponse;
import com.retail.ecommerce.responsedto.AddressUpdateResponse;
import com.retail.ecommerce.service.AdderssService;
import com.retail.ecommerce.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class AddressController {

	private AdderssService addressservice;

	@PostMapping("/addAddress")
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(
			@Valid @RequestBody AddressRequest addressRequest) {
		return addressservice.addAddress(addressRequest);

	}

	@GetMapping("/{role}/findAddress")
	public ResponseEntity<ResponseStructure<AddressSellerResponse>> findSellerAddress(@PathVariable UserRole role) {
		return addressservice.findAddressBySeller(role);
	}

	@GetMapping("/{role}/findCustomerAddress")
	public ResponseEntity<ResponseStructure<AddressContactsResponse>> findCustomerAddress(@PathVariable UserRole role) {
		return addressservice.findCustomerAddress(role);
	}

	@PutMapping("/{addressId}/updateAddress")
	public ResponseEntity<ResponseStructure<AddressUpdateResponse>> updateAddress(
			@Valid @RequestBody AddressRequest addressRequest, @PathVariable int addressId) {
		return addressservice.updateAddress(addressRequest, addressId);
	}
}
