package com.e_commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    // price at the time of purchase (snapshot) stored separately from product.price because price can change !
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtPurchase;

    // product name snapshot - in case product is deleted later
    @Column(nullable = false)
    private String productName;

    // sub total, stored for easy retrieval (avoid recomputation)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // reference to the original product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // many items belong to one order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

}
