package com.e_commerce.dto;

import com.e_commerce.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private String sku;
    private ProductStatus status;
    private boolean inStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // include category summary in product response
    private CategoryResponse category;
}
