package com.e_commerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "User Id is required")
    private Long userId;

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<OrderItemRequest> items;

    // shipping address fields
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotBlank(message = "shipping city is required")
    private String shippingCity;

    @NotBlank(message = "Shipping country is required")
    private String shippingCountry;

    @NotBlank(message = "Shipping zip code is required")
    private String shippingZipCode;
}
