package com.e_commerce.repository;

import com.e_commerce.entity.Product;
import com.e_commerce.entity.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    // filter by  keyword in name or description, return null if keyword is blank
    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;    // no filter
            }
            String pattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }


    // filter by category Id
    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) {
                return null;
            }
            return cb.equal(root.get("category").get("id"), categoryId);
        };
    }


    // filter by minimum price
    public static Specification<Product> hasPriceGreaterThanOrEqual(BigDecimal minPrice) {
        return (root, query, cb) -> {
            if (minPrice == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }


    // filter by maximum price
    public static Specification<Product> hasPriceLessThanOrEqual(BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (maxPrice == null) {
                return null;
            }
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }


    // filter by status
    public static Specification<Product> hasStatus(ProductStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }
            return cb.equal(root.get("status"), status);
        };
    }


    // combine all filters into one specification
    public static Specification<Product> buildFilter(String keyword,
                                                     Long categoryId,
                                                     BigDecimal minPrice,
                                                     BigDecimal maxPrice,
                                                     ProductStatus status) {
        return Specification
                .where(hasKeyword(keyword))
                .and(hasCategory(categoryId))
                .and(hasPriceGreaterThanOrEqual(minPrice))
                .and(hasPriceLessThanOrEqual(maxPrice))
                .and(hasStatus(status));
    }

}

















