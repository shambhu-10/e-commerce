package com.e_commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {

    private Long id;
    private Integer quantity;
    private BigDecimal productPrice;
    private BigDecimal subtotal;

    // include product summary in cart
    private Long productId;
    private String productName;
    private String productImageUrl;
}
