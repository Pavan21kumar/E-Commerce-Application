package com.retail.ecommerce.requestdto;

import com.retail.ecommerce.enums.AddressType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "streetAddress countains onle alpha numeric charecters")
	private String streetAddress;
	@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "streetAddress countains onle alpha numeric charecters")
	private String streetAdressAditional;
	@Pattern(regexp = "^[a-zA-Z]+$", message = "only alphabates can accept")
	private String city;
	@Pattern(regexp = "^[a-zA-Z]+$", message = "only alphabates can accept")
	private String state;
	@Pattern(regexp = "^[a-zA-Z]+$", message = "only alphabates can accept")
	private String country;
	private int pincode;
	@NotNull
	private AddressType addressType;
}
