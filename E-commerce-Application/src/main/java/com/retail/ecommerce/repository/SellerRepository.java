package com.retail.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecommerce.entity.Product;
import com.retail.ecommerce.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

	boolean existsByProduct(Product product);

}
