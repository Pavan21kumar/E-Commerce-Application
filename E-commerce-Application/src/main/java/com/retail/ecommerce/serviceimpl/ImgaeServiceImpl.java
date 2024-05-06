package com.retail.ecommerce.serviceimpl;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.retail.ecommerce.entity.Image;
import com.retail.ecommerce.enums.ImageTypes;
import com.retail.ecommerce.exception.AccessTokenExpireException;
import com.retail.ecommerce.exception.CoverImageallreadyAddedException;
import com.retail.ecommerce.exception.ImageNotFoundByIdException;
import com.retail.ecommerce.exception.ImageTypeNotCorrectexception;
import com.retail.ecommerce.exception.ImageTypeNotSpecifiedException;
import com.retail.ecommerce.exception.NoFileFoundException;
import com.retail.ecommerce.exception.ProductIsNotBelongsToTheSellerException;
import com.retail.ecommerce.exception.ProductIsNotFoundException;
import com.retail.ecommerce.repository.ImageMongodbRepository;
import com.retail.ecommerce.repository.ProductRepository;
import com.retail.ecommerce.repository.SellerRepository;
import com.retail.ecommerce.repository.UserRegisterRepoository;
import com.retail.ecommerce.service.Imageservice;
import com.retail.ecommerce.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImgaeServiceImpl implements Imageservice {

	private ImageMongodbRepository imageRepo;
	private ProductRepository productRepo;
	private ResponseStructure<String> imageStructure;
	private UserRegisterRepoository userRepo;
	private SellerRepository sellerRepo;

	@Override
	public ResponseEntity<ResponseStructure<String>> addImage(int productId, String imageType, MultipartFile images) {

		return productRepo.findById(productId).map(product -> {
			if (imageType == null)
				throw new ImageTypeNotSpecifiedException("imageType is Not Specified Please Provide Image Type...");
			if (!sellerRepo.existsByProduct(product))
				throw new ProductIsNotBelongsToTheSellerException("invalid prodct");
			if (images.isEmpty())
				throw new NoFileFoundException("no file found...............");
			Image image = mapToImage(images, imageType);

			// System.out.println(MediaType.IMAGE_PNG);
			if (imageRepo.existsByProductIdAndImageTypes(productId, ImageTypes.COVER)
					&& imageType.equalsIgnoreCase(ImageTypes.COVER.name()))
				throw new CoverImageallreadyAddedException("cover image allready added....");
			image.setProductId(product.getProductId());
			imageRepo.save(image);

			return ResponseEntity.ok(imageStructure.setStatusCode(HttpStatus.OK.value()).setMessage("image is added ")
					.setData("immage Is added For Product..."));
		}).orElseThrow(() -> new ProductIsNotFoundException("product Is Not found By Given Id.."));
	}

	private Image mapToImage(MultipartFile images, String imageType) {

		return Image.builder().image(images.getOriginalFilename()).imageTypes(mapToImageType(imageType))
				.imageBytes(mapToImagebytes(images)).contentType(images.getContentType()).build();

	}

	private byte[] mapToImagebytes(MultipartFile images) {
		byte[] bytes = null;
		try {
			bytes = images.getBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}

	private ImageTypes mapToImageType(String imageType) {

		if (imageType.equalsIgnoreCase(ImageTypes.COVER.name()))
			return ImageTypes.COVER;
		else if (imageType.equalsIgnoreCase(ImageTypes.OTHER.name()))
			return ImageTypes.OTHER;
		throw new ImageTypeNotCorrectexception("imageType Is Not mention.....");
	}

	@Override
	public ResponseEntity<byte[]> findImage(String imageId) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepo.findByUsername(username).map(user -> {

			return imageRepo.findById(imageId).map(image -> {
				return ResponseEntity.status(HttpStatus.FOUND).contentLength(image.getImageBytes().length)
						.contentType(MediaType.valueOf(image.getContentType())).body(image.getImageBytes());

			}).orElseThrow(() -> new ImageNotFoundByIdException("image not found based on imageId"));

		}).orElseThrow(() -> new AccessTokenExpireException("accesstoken Is Expired..please regenerate.."));

	}

}
