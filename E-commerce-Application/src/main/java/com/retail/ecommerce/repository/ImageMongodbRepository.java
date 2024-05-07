package com.retail.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.retail.ecommerce.entity.Image;
import com.retail.ecommerce.enums.ImageTypes;

public interface ImageMongodbRepository extends MongoRepository<Image, String> {

	boolean existsByProductIdAndImageTypes(int productId, ImageTypes cover);

	Optional<Image> findByImageTypesAndProductId(ImageTypes othe, int productId);

	List<Image> findByProductIdAndImageTypes(int productId, ImageTypes other);

}
