package com.retail.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecommerce.entity.Contact;

public interface ContactRepo extends JpaRepository<Contact, Integer> {

}
