package com.retail.ecommerce.responsedto;

import java.util.List;

import com.retail.ecommerce.entity.Contact;
import com.retail.ecommerce.enums.AddressType;

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
public class AddressSellerResponse {

	private int addressId;
	private String streetAddress;
	private String streetAdressAditional;
	private String city;
	private String state;
	private String country;
	private int pincode;
	private AddressType addressType;
	private List<Contact> contacts;
}
