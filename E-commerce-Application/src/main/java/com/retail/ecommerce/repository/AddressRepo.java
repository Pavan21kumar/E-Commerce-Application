package com.retail.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecommerce.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Integer> {

}
