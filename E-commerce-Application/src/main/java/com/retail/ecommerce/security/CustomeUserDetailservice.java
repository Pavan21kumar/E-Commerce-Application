package com.retail.ecommerce.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.retail.ecommerce.repository.UserRegisterRepoository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomeUserDetailservice implements UserDetailsService {

	private UserRegisterRepoository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username).map( CustomeUserDetails::new

		).orElseThrow(() -> new UsernameNotFoundException("user not found"));
	}

}
