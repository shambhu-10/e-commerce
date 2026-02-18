package com.e_commerce.dto;

import com.e_commerce.entity.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200, message = "Product name must be between 2 and 200 characters")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;


    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater that 0")
    @Digits(integer = 8, fraction = 2, message = "Price format invalid (max 8 digits, 2 decimal places)")
    private BigDecimal price;


    @NotNull(message = "stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    private String imageUrl;

    @Size(max = 100, message = "SKU cannot exceed 100 characters")
    private String sku;

    private ProductStatus status = ProductStatus.ACTIVE;

    @NotNull(message = "Category is required")
    private Long categoryId;
}
