package com.retail.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.retail.ecommerce.requestdto.SearchFilter;
import com.retail.ecommerce.responsedto.ImageResponse;
import com.retail.ecommerce.responsedto.ProductResponse;
import com.retail.ecommerce.util.ResponseStructure;

public interface Imageservice {

	ResponseEntity<ResponseStructure<String>> addImage(int productId, String imageType, MultipartFile images);

	ResponseEntity<byte[]> findImage(String imageId);

}
