package com.retail.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.retail.ecommerce.responsedto.ImageResponse;
import com.retail.ecommerce.service.Imageservice;
import com.retail.ecommerce.util.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ImageController {

	private Imageservice imageService;

	@PreAuthorize("hasAuthority('SELLER')")
	@PostMapping("/products/{productId}/imagetype/{imageType}/images")
	public ResponseEntity<ResponseStructure<String>> addImage( @PathVariable int productId, 
			@PathVariable String imageType, MultipartFile images) {
		return imageService.addImage(productId, imageType, images);
	}
	@GetMapping("/{imageId}/images")
	public ResponseEntity<byte[]> getImageById(@PathVariable String imageId,MultipartFile images){
		return imageService.findImage(imageId);
	}
}
