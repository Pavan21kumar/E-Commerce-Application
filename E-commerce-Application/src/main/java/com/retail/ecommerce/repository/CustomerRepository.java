package com.retail.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecommerce.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
