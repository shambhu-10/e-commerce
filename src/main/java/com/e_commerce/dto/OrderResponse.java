package com.e_commerce.dto;

import com.e_commerce.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private String orderNumber;
    private OrderStatus status;
    private BigDecimal totalAmount;

    private String shippingAddress;
    private String shippingCity;
    private String shippingCountry;
    private String shippingZipCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // include user summary but no password
    private UserSummaryResponse user;

    // Include all order items
    private List<OrderItemResponse> items;
}
