package com.retail.ecommerce.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.retail.ecommerce.entity.Address;
import com.retail.ecommerce.entity.Contact;
import com.retail.ecommerce.entity.Customer;
import com.retail.ecommerce.entity.Seller;
import com.retail.ecommerce.entity.User;
import com.retail.ecommerce.enums.AddressType;
import com.retail.ecommerce.enums.UserRole;
import com.retail.ecommerce.exception.AccessTokenExpireException;
import com.retail.ecommerce.exception.AddressAllreadyAddedException;
import com.retail.ecommerce.exception.AddressLimitException;
import com.retail.ecommerce.exception.AddressTypeIsNullException;
import com.retail.ecommerce.exception.AddressnotFoundByIdException;
import com.retail.ecommerce.exception.ContactsNotAddedException;
import com.retail.ecommerce.exception.InvalidRoleForThisCustomerException;
import com.retail.ecommerce.exception.InvalidSellerRoleException;
import com.retail.ecommerce.exception.NoAddressFoundException;
import com.retail.ecommerce.exception.PleaseGiveRefreshAccessTokenRequest;
import com.retail.ecommerce.exception.UserIsNotLoginException;
import com.retail.ecommerce.jwt.JwtService;
import com.retail.ecommerce.repository.AddressRepo;
import com.retail.ecommerce.repository.CustomerRepository;
import com.retail.ecommerce.repository.SellerRepository;
import com.retail.ecommerce.repository.UserRegisterRepoository;
import com.retail.ecommerce.requestdto.AddressRequest;
import com.retail.ecommerce.responsedto.AddressContactResponse;
import com.retail.ecommerce.responsedto.AddressResponse;
import com.retail.ecommerce.responsedto.AddressSellerResponse;
import com.retail.ecommerce.responsedto.AddressUpdateResponse;
import com.retail.ecommerce.service.AdderssService;
import com.retail.ecommerce.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddAddressServiceImpl implements AdderssService {

	private AddressRepo addressRepo;
	private JwtService jwtService;
	private UserRegisterRepoository userRepo;
	private SellerRepository sellerRepo;
	private CustomerRepository customerRepo;
	private ResponseStructure<AddressResponse> responseStructure;
	private ResponseStructure<List<AddressContactResponse>> addressContactStructure;
	private ResponseStructure<AddressUpdateResponse> updateresponse;
	private ResponseStructure<AddressSellerResponse> sellerResponse;

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.isAuthenticated())
			throw new UserIsNotLoginException("user is not Login...........");
		if (!addressRequest.getAddressType().equals(AddressType.PRIMARY)
				&& !addressRequest.getAddressType().equals(AddressType.ADDITIONAL))
			throw new AddressTypeIsNullException("AddressType Is Not Specified");
		String userName = authentication.getName();
		User user = userRepo.findByUsername(userName).get();
		if (user == null)
			throw new AccessTokenExpireException("Access token is Expired...");
		Address address = null;
		if (user.getRole().equals(UserRole.SELLER)) {
			Seller seller = (Seller) user;
			if (seller.getAddress() != null)
				throw new AddressAllreadyAddedException("Address Is Allready Added...");
			address = addressRepo.save(mapToAddress(addressRequest));
			seller.setAddress(address);
			sellerRepo.save(seller);
		} else {

			Customer customer = (Customer) user;

			if (customer.getAddresses().size() >= 5)
				throw new AddressLimitException("You Allready Reached Limit Of Adding Addresses...");
			address = addressRepo.save(mapToAddress(addressRequest));
			if (customer.getAddresses() == null) {
				customer.setAddresses(Arrays.asList(address));
			} else {
				customer.getAddresses().add(address);
			}

			customerRepo.save(customer);
			addressRepo.save(address);

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
	public ResponseEntity<ResponseStructure<AddressSellerResponse>> findAddressBySeller(UserRole role) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!authentication.isAuthenticated())
			throw new UserIsNotLoginException("user is Not Loged In");
		String userName = authentication.getName();
		User user = userRepo.findByUsername(userName).get();
		if (user == null)
			throw new AccessTokenExpireException("Access token is Expired...");
		if (!user.getRole().equals(role))
			throw new InvalidSellerRoleException("User Role Not ,match with The Specifing Role..");
		Seller seller = (Seller) user;
		Address address = seller.getAddress();
		if (address == null)
			throw new NoAddressFoundException("No Address Added ...");
		List<Contact> contacts = address.getContact();
		if (contacts.isEmpty())
			throw new ContactsNotAddedException("Contacts Not Added Please Add Contacts to The Perticuler Address");
		return ResponseEntity.ok(sellerResponse.setStatusCode(HttpStatus.OK.value()).setMessage("Data Found")
				.setData(mapToSellerResponse(address, contacts)));

	}

	private AddressSellerResponse mapToSellerResponse(Address address, List<Contact> contacts) {

		return AddressSellerResponse.builder().addressId(address.getAddressId())
				.streetAddress(address.getStreetAddress()).streetAdressAditional(address.getStreetAdressAditional())
				.city(address.getCity()).state(address.getState()).country(address.getCountry())
				.pincode(address.getPincode()).addressType(address.getAddressType()).contacts(contacts).build();
	}

//----------------------------------------------------------------------------------------------
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

	@Override
	public ResponseEntity<ResponseStructure<List<AddressContactResponse>>> findCustomerAddress(UserRole role) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!authentication.isAuthenticated())
			throw new UserIsNotLoginException("user is Not Loged In");
		String userName = authentication.getName();
		User user = userRepo.findByUsername(userName).get();
		if (user == null)
			throw new AccessTokenExpireException("Access");
		System.out.println(user.getRole().equals(role));
		System.out.println(role);
		if (!user.getRole().equals(role))
			throw new InvalidRoleForThisCustomerException("User role Is Diffrent");
		Customer customer = (Customer) user;
		List<Address> addresses = customer.getAddresses();
		if (addresses == null)
			throw new NoAddressFoundException("No Address Added ...");
		return ResponseEntity.ok(addressContactStructure.setStatusCode(HttpStatus.OK.value()).setMessage("Data Found")
				.setData(mapToAddresContact(addresses)));
	}

	@Override
	public ResponseEntity<ResponseStructure<AddressUpdateResponse>> updateAddress(AddressRequest addressRequest,
			int addressId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!authentication.isAuthenticated())
			throw new UserIsNotLoginException("user is Not Loged In");
		String userName = authentication.getName();
		User user = userRepo.findByUsername(userName).get();
		if (user == null)
			throw new AccessTokenExpireException("Access");
		return addressRepo.findById(addressId).map(address -> {
			address = mapToAddress(address, addressRequest);
			addressRepo.save(address);
			return ResponseEntity.ok(updateresponse.setStatusCode(HttpStatus.OK.value())
					.setMessage("Address Is Updated").setData(mapToAddressUpdateResponse(address)));
		}).orElseThrow(() -> new AddressnotFoundByIdException("address not found by given id....."));

	}

	private AddressUpdateResponse mapToAddressUpdateResponse(Address address) {

		return AddressUpdateResponse.builder().addressId(address.getAddressId())
				.streetAddress(address.getStreetAddress()).streetAdressAditional(address.getStreetAdressAditional())
				.city(address.getCity()).state(address.getState()).country(address.getCountry())
				.pincode(address.getPincode()).addressType(address.getAddressType()).build();
	}

	private Address mapToAddress(Address address, AddressRequest addressRequest) {
		address.setAddressId(address.getAddressId());
		address.setStreetAddress(addressRequest.getStreetAddress());
		address.setStreetAdressAditional(addressRequest.getStreetAdressAditional());
		address.setCity(addressRequest.getCity());
		address.setState(addressRequest.getState());
		address.setCountry(addressRequest.getCountry());
		address.setPincode(addressRequest.getPincode());
		address.setContact(address.getContact());
		return address;

	}

}
