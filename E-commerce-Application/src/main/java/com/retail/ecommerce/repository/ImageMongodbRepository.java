package com.retail.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.retail.ecommerce.entity.Image;
import com.retail.ecommerce.entity.Product;
import com.retail.ecommerce.enums.ImageTypes;

public interface ImageMongodbRepository extends MongoRepository<Image, String> {

	boolean existsByProductIdAndImageTypes(int productId, ImageTypes cover);

}
