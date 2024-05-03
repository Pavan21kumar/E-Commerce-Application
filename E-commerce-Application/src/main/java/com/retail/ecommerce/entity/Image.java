package com.retail.ecommerce.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.retail.ecommerce.enums.ImageTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "images")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Image {

	@MongoId
	private String imageId;
	private String image;
	private byte[] imageBytes;
	private ImageTypes imageTypes;
	private String contentType;
	private int productId;

}
