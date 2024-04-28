package com.retail.ecommerce.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.retail.ecommerce.entity.Address;
import com.retail.ecommerce.entity.Contact;
import com.retail.ecommerce.entity.Customer;
import com.retail.ecommerce.entity.Seller;
import com.retail.ecommerce.entity.User;
import com.retail.ecommerce.enums.AddressType;
import com.retail.ecommerce.enums.UserRole;
import com.retail.ecommerce.exception.AddressAllreadyAddedException;
import com.retail.ecommerce.exception.AddressLimitException;
import com.retail.ecommerce.exception.AddressTypeIsNullException;
import com.retail.ecommerce.exception.PleaseGiveRefreshAccessTokenRequest;
import com.retail.ecommerce.exception.UserIsNotLoginException;
import com.retail.ecommerce.jwt.JwtService;
import com.retail.ecommerce.repository.AddressRepo;
import com.retail.ecommerce.repository.CustomerRepository;
import com.retail.ecommerce.repository.SellerRepository;
import com.retail.ecommerce.repository.UserRegisterRepoository;
import com.retail.ecommerce.requestdto.AddressRequest;
import com.retail.ecommerce.responsedto.AddressContactResponse;
import com.retail.ecommerce.responsedto.AddressContactsResponse;
import com.retail.ecommerce.responsedto.AddressResponse;
import com.retail.ecommerce.service.AdderssService;
import com.retail.ecommerce.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddAddressServiceImpl implements AdderssService {

	private AddressRepo addAddressRepo;
	private JwtService jwtService;
	private UserRegisterRepoository userRepo;
	private SellerRepository sellerRepo;
	private CustomerRepository customerRepo;
	private ResponseStructure<AddressResponse> responseStructure;
	private ResponseStructure<AddressContactsResponse> addressContactStructure;

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest,
			String accessToken, String refreshToken) {

		if (accessToken == null || refreshToken == null)
			throw new UserIsNotLoginException("user is not Login...........");
		if (!addressRequest.getAddressType().equals(AddressType.PRIMARY)
				&& !addressRequest.getAddressType().equals(AddressType.ADDITIONAL))
			throw new AddressTypeIsNullException("AddressType Is Not Specified");
		String userName = jwtService.getUserName(refreshToken);
		User user = userRepo.findByUsername(userName).get();
		Address address = null;
		if (user.getRole().equals(UserRole.SELLER)) {
			Seller seller = (Seller) user;
			if (seller.getAddress() != null)
				throw new AddressAllreadyAddedException("Address Is Allready Added...");
			address = addAddressRepo.save(mapToAddress(addressRequest));
			seller.setAddress(address);
			sellerRepo.save(seller);
		} else {

			Customer customer = (Customer) user;

			if (customer.getAddresses().size() >= 5)
				throw new AddressLimitException("You Allready Reached Limit Of Adding Addresses...");
			address = addAddressRepo.save(mapToAddress(addressRequest));
			address.setCustomer(customer);
			customerRepo.save(customer);
			addAddressRepo.save(address);

		}

		return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("address is saved")
				.setData(mapToAddressResponse(address)));
	}

	private AddressResponse mapToAddressResponse(Address address) {
		return AddressResponse.builder().addressId(address.getAddressId()).build();
	}

	private Address mapToAddress(AddressRequest addressRequest) {
		// TODO Auto-generated method stub
		return Address.builder().streetAddress(addressRequest.getStreetAddress())
				.streetAdressAditional(addressRequest.getStreetAdressAditional()).city(addressRequest.getCity())
				.state(addressRequest.getState()).country(addressRequest.getCountry())
				.pincode(addressRequest.getPincode()).addressType(addressRequest.getAddressType()).build();

	}

	@Override
	public ResponseEntity<ResponseStructure<AddressContactsResponse>> findAddress(String accessToken,
			String refreshToken) {

		if (accessToken == null && refreshToken != null)
			throw new PleaseGiveRefreshAccessTokenRequest("");
		if (accessToken == null && refreshToken == null)
			throw new UserIsNotLoginException("user is not Login...........");
		String userName = jwtService.getUserName(accessToken);
		User user = userRepo.findByUsername(userName).get();
		if (user.getRole().equals(UserRole.SELLER)) {
			Seller seller = (Seller) user;
			Address address = seller.getAddress();
			List<Contact> contact = address.getContact();
			return ResponseEntity.ok(addressContactStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Data Found").setData(mapToAddressContactResponse(address, contact)));
		} else {
			Customer customer = (Customer) user;
			List<Address> addresses = customer.getAddresses();
			return ResponseEntity.ok(addressContactStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Data Found").setData(mapToAddressContactResponse(addresses)));
		}

	}

	// if it is seller
	private AddressContactsResponse mapToAddressContactResponse(Address address, List<Contact> contact) {

		return AddressContactsResponse.builder().address(mapToAddressContacts(address, contact)).build();

	}

	private List<AddressContactResponse> mapToAddressContacts(Address address, List<Contact> contact) {

		List<AddressContactResponse> list = new ArrayList<>();
		AddressContactResponse build = AddressContactResponse.builder().addressId(address.getAddressId())
				.streetAddress(address.getStreetAddress()).streetAdressAditional(address.getStreetAdressAditional())
				.city(address.getCity()).state(address.getState()).country(address.getCountry())
				.pincode(address.getPincode()).contact(contact).addressType(address.getAddressType()).build();
		list.add(build);

		return list;
	}

	private AddressContactsResponse mapToAddressContactResponse(List<Address> addresses) {

		return AddressContactsResponse.builder().address(mapToAddresContact(addresses)).build();
	}

	private List<AddressContactResponse> mapToAddresContact(List<Address> addresses) {

		List<AddressContactResponse> list = new ArrayList<>();
		for (Address address : addresses) {
			AddressContactResponse build = AddressContactResponse.builder().addressId(address.getAddressId())
					.streetAddress(address.getStreetAddress()).streetAdressAditional(address.getStreetAdressAditional())
					.city(address.getCity()).state(address.getState()).country(address.getCountry())
					.pincode(address.getPincode()).contact(address.getContact()).addressType(address.getAddressType())
					.build();
			list.add(build);

		}
		return list;
	}
}
