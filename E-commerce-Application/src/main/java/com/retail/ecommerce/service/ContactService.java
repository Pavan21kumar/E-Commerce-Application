package com.retail.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.retail.ecommerce.requestdto.ContactsRequest;
import com.retail.ecommerce.responsedto.AddressResponse;
import com.retail.ecommerce.util.SimpleResponseStructure;

import jakarta.validation.Valid;

public interface ContactService {

	ResponseEntity<SimpleResponseStructure> addContact(@Valid List<ContactsRequest> contactRequests, int addressId);

}
