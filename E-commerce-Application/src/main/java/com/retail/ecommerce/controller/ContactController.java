package com.retail.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecommerce.requestdto.ContactsRequest;
import com.retail.ecommerce.responsedto.AddressResponse;
import com.retail.ecommerce.service.ContactService;
import com.retail.ecommerce.util.SimpleResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ContactController {

	private ContactService contactService;
	
	@PostMapping("/{addressId}/addContacts")
	public ResponseEntity<SimpleResponseStructure> addContact(@Valid @RequestBody List<ContactsRequest> contactRequests,@PathVariable int addressId )
	{
		return  contactService.addContact(contactRequests,addressId);
	}
}