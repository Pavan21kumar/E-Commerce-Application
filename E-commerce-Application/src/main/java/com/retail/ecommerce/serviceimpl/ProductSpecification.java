package com.retail.ecommerce.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.retail.ecommerce.entity.Product;
import com.retail.ecommerce.enums.AvailabilityStatus;
import com.retail.ecommerce.enums.ProductCategory;
import com.retail.ecommerce.exception.ProductCategoryNotSpecifiedException;
import com.retail.ecommerce.requestdto.SearchFilter;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductSpecification {

	private SearchFilter searchFilter;

	public Specification<Product> buildSpecification() {

		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (searchFilter.getMinPrice() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchFilter.getMinPrice()));
			}
			if (searchFilter.getMaxPrice() != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchFilter.getMaxPrice()));
			}
			if (searchFilter.getStatus() != null) {
				try {//

					AvailabilityStatus status = AvailabilityStatus.valueOf(searchFilter.getStatus().toUpperCase());
					predicates.add(criteriaBuilder.equal((root.get("status")), status));
				} catch (IllegalArgumentException e) {
					throw new ProductCategoryNotSpecifiedException("category not mention..");
				}

			}
			if (searchFilter.getCategory() != null) {
				ProductCategory category = ProductCategory.valueOf(searchFilter.getCategory().toUpperCase());
				predicates.add(criteriaBuilder.equal(root.get("category"), category));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};

	}

}
