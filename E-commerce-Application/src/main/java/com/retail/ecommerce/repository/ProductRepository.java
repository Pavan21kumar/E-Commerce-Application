package com.retail.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
