package com.retail.ecommerce.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.retail.ecommerce.entity.Contact;
import com.retail.ecommerce.enums.Priority;
import com.retail.ecommerce.exception.AddressNotFoundException;
import com.retail.ecommerce.exception.ContactsFulledException;
import com.retail.ecommerce.exception.PriorityNotSetException;
import com.retail.ecommerce.repository.AddressRepo;
import com.retail.ecommerce.repository.ContactRepo;
import com.retail.ecommerce.requestdto.ContactsRequest;
import com.retail.ecommerce.responsedto.AddressResponse;
import com.retail.ecommerce.service.ContactService;
import com.retail.ecommerce.util.ResponseStructure;
import com.retail.ecommerce.util.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

	private AddressRepo addressRepo;
	private ContactRepo contactRepo;
	private SimpleResponseStructure responseStructure;

	@Override
	public ResponseEntity<SimpleResponseStructure> addContact(List<ContactsRequest> contactRequests, int addressId) {

		return addressRepo.findById(addressId).map(address -> {

			if (contactRequests.size() > 2)
				throw new ContactsFulledException("Cannot add more than two contacts at once.");

			// throw new PriorityNotSetException("priority is Not Set..");
			List<Contact> contacts = new ArrayList<>();
			for (ContactsRequest request : contactRequests) {
				contacts.add(mappToContact(request));
			}
			contactRepo.saveAll(contacts);
			address.setContact(contacts);
			addressRepo.save(address);

			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Contacs Is created..SuccessFully"));
		}).orElseThrow(() -> new AddressNotFoundException(""));

	}

	private Contact mappToContact(ContactsRequest request) {
		if (request.getPriority().equals(Priority.ADDITIONAL) || request.getPriority().equals(Priority.PRIMARY))
			return Contact.builder().name(request.getName()).phoneNumber(request.getPhonoNumber())
					.email(request.getEmail()).priority(request.getPriority()).build();
		throw new PriorityNotSetException(" priority not set..");
	}

}
