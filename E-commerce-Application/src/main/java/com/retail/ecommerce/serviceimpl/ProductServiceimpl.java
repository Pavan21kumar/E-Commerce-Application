package com.retail.ecommerce.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.retail.ecommerce.entity.Image;
import com.retail.ecommerce.entity.Product;
import com.retail.ecommerce.entity.Seller;
import com.retail.ecommerce.enums.AvailabilityStatus;
import com.retail.ecommerce.enums.ImageTypes;
import com.retail.ecommerce.enums.ProductCategory;
import com.retail.ecommerce.exception.AccessTokenExpireException;
import com.retail.ecommerce.exception.ProductIsNotFoundException;
import com.retail.ecommerce.exception.UserIsNotLoginException;
import com.retail.ecommerce.repository.ImageMongodbRepository;
import com.retail.ecommerce.repository.ProductRepository;
import com.retail.ecommerce.repository.SellerRepository;
import com.retail.ecommerce.repository.UserRegisterRepoository;
import com.retail.ecommerce.requestdto.ProductRequest;
import com.retail.ecommerce.requestdto.SearchFilter;
import com.retail.ecommerce.responsedto.ProductResponse;
import com.retail.ecommerce.service.ProductService;
import com.retail.ecommerce.util.ResponseStructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.val;

@Service
@AllArgsConstructor
public class ProductServiceimpl implements ProductService {

	private ProductRepository productRepo;
	private UserRegisterRepoository userRepo;
	private SellerRepository sellerRepo;
	private ResponseStructure<ProductResponse> productResponse;
	private ResponseStructure<List<ProductResponse>> listProductResponses;
	private ImageMongodbRepository imageRepo;
	@PersistenceContext
	private EntityManager em;
	private ResponseStructure<ProductCategory[]> allCategories;

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(ProductRequest productRequest) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.isAuthenticated())
			throw new UserIsNotLoginException("user Is Not Login");

		String username = authentication.getName();
		return userRepo.findByUsername(username).map(user -> {

			Product product = mapToProduct(productRequest);
			product.setStatus(setStatus(product.getQuantity()));
			productRepo.save(product);
			Seller seller = (Seller) user;
			if (seller.getProduct() == null)
				seller.setProduct(Arrays.asList(product));
			else
				seller.getProduct().add(product);
			sellerRepo.save(seller);

			return ResponseEntity.ok(productResponse.setStatusCode(HttpStatus.OK.value()).setMessage("product is Saved")
					.setData(MapToProductResponse(product)));

		}).orElseThrow(
				() -> new AccessTokenExpireException("AccessToken Is Expired please refresh AccessToken  Again.."));

	}

	private ProductResponse MapToProductResponse(Product product) {
		return ProductResponse.builder().productId(product.getProductId()).productName(product.getProductName())
				.description(product.getDescription()).price(product.getPrice()).quantity(product.getQuantity())
				.status(product.getStatus()).category(product.getCategory()).build();
	}

	private Product mapToProduct(ProductRequest productRequest) {

		return Product.builder().productName(productRequest.getProductName())
				.description(productRequest.getDescription()).price(productRequest.getPrice())
				.quantity(productRequest.getQuantity()).category(productRequest.getCategory()).build();
	}

	private AvailabilityStatus setStatus(int quantity) {

		if (quantity > 10)
			return AvailabilityStatus.AVAILABLE;
		else if (quantity == 0)
			return AvailabilityStatus.OUTOFSTOCK;
		else
			return AvailabilityStatus.FEWLEFT;

	}

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(ProductRequest productRequest,
			int productId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.isAuthenticated())
			throw new UserIsNotLoginException("user Is Not Login");

		String username = authentication.getName();
		return userRepo.findByUsername(username).map(user -> {

			return productRepo.findById(productId).map(product -> {

				product = mapToProductRequestToProduct(product, productRequest);

				productRepo.save(product);
				return ResponseEntity.ok(productResponse.setStatusCode(HttpStatus.OK.value())
						.setMessage("Product data Is Updated..").setData(MapToProductResponse(product)));
			}).orElseThrow(() -> new ProductIsNotFoundException("product is Not found By Given Id"));
		}).orElseThrow(
				() -> new AccessTokenExpireException("AccessToken Is Expired please refresh AccessToken  Again.."));

	}

	private Product mapToProductRequestToProduct(Product product, ProductRequest productRequest) {

		product.setProductId(product.getProductId());
		product.setProductName(productRequest.getProductName());
		product.setDescription(productRequest.getDescription());
		product.setPrice(productRequest.getPrice());
		product.setQuantity(productRequest.getQuantity());
		product.setStatus(setStatus(productRequest.getQuantity()));

		return product;
	}

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> findProduct(int productId) {

		return productRepo.findById(productId).map(product -> {
			return ResponseEntity.status(HttpStatus.FOUND).body(productResponse.setStatusCode(HttpStatus.FOUND.value())
					.setMessage("Product Is Found..").setData(mapToProductResponse(product)));
		}).orElseThrow(() -> new ProductIsNotFoundException("product Is Not Found........"));
	}

	@Override
	public ResponseEntity<ResponseStructure<List<ProductResponse>>> getProducts(SearchFilter filters) {
		Specification<Product> buildSpecification = new ProductSpecification(filters).buildSpecification();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Product> createQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = createQuery.from(Product.class);
		createQuery.select(root);
		Predicate predicate = buildSpecification.toPredicate(root, createQuery, criteriaBuilder);

		createQuery.where(predicate);
		TypedQuery<Product> createQuery2 = em.createQuery(createQuery);
		List<Product> resultList = createQuery2.getResultList();
		return ResponseEntity.ok(listProductResponses.setStatusCode(HttpStatus.OK.value()).setMessage("data is found")
				.setData(mapToListProducts(resultList)));
	}

	private List<ProductResponse> mapToListProducts(List<Product> resultList) {
		List<ProductResponse> listProducts = new ArrayList<>();
		for (Product product : resultList) {
			listProducts.add(mapToProductResponse(product));
		}

		return listProducts;
	}

	private ProductResponse mapToProductResponse(Product product) {

		List<Image> imageids = imageRepo.findByProductIdAndImageTypes(product.getProductId(), ImageTypes.OTHER);
		Image cover = null;
		Optional<Image> image = imageRepo.findByImageTypesAndProductId(ImageTypes.COVER, product.getProductId());
		if (image.isPresent()) {
			cover = image.get();
		}
		return ProductResponse.builder().productId(product.getProductId()).productName(product.getProductName())
				.description(product.getDescription()).category(product.getCategory())
				.coverImage(mapToCoverImage(cover)).immagesLinks(mapToOtherImages(imageids)).status(product.getStatus())
				.build();

	}

	private List<String> mapToOtherImages(List<Image> images) {
		if (images.isEmpty())
			return null;
		List<String> list = new ArrayList<>();
		for (Image link : images) {

			list.add("/api/v1/images/" + link.getImageId());
		}
		return list;
	}

	private String mapToCoverImage(Image coverImage) {
		if (coverImage == null)
			return null;

		return "/api/v1/images/" + coverImage.getImageId();
	}

	@Override
	public ResponseEntity<ResponseStructure<ProductCategory[]>> fetchAllCategories() {

		return ResponseEntity.status(HttpStatus.FOUND.value())
				.body(allCategories.setStatusCode(HttpStatus.FOUND.value()).setMessage("categoties are Found.....")
						.setData(ProductCategory.values()));

	}

}
