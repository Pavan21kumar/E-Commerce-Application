package com.retail.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.retail.ecommerce.entity.Product;

public interface ProductRepository extends JpaSpecificationExecutor<Product>, JpaRepository<Product, Integer> {

}
