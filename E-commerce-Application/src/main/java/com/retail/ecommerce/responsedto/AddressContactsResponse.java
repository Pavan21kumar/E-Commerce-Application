package com.retail.ecommerce.responsedto;

import java.util.List;

import com.retail.ecommerce.entity.Address;
import com.retail.ecommerce.entity.Contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressContactsResponse {

	private List<AddressContactResponse> address;
	// private List<Contact> contacts;
}
