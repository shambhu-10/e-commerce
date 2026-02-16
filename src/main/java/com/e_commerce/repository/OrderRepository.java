package com.e_commerce.repository;

import com.e_commerce.entity.Order;
import com.e_commerce.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // find order by order number
    Optional<Order> findByOrderNumber(String orderNumber);



    // find all orders for a user
    Page<Order> findByUserId(Long userId, Pageable pageable);



    // find all orders by status (for admin)
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);



    // load order with all items and products in one query, avoid N+1 when displaying order details
    @Query("SELECT DISTINCT o FROM Order o " +
            "JOIN FETCH o.item i " +
            "JOIN FETCH i.product " +
            "WHERE o.id = :id")
    Optional<Order> findByIdWithItems(@Param("id") Long id);



    // load orders with items for a user
    @Query("SELECT DISTINCT o FROM Order o " +
            "JOIN FETCH o.items " +
            "WHERE o.user.id = :userId " +
            "ORDER BY o.createdAt DESC")
    List<Order> findByUserIdWithItems(@Param("usreId") Long userId);



    // update order status
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    int updateOrderStatus(@Param("id") Long id, @Param("status") OrderStatus status);



    // count order by status, for admin dashboard
    Long countByStatus(OrderStatus status);



    // revenue report - total revenue between two dates
    // this is an AGGREGATE query, returns a single value
    // if no orders found:  COALESCE return 0 instead of null, SUM return null
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o " +
            "WHERE o.status = 'DELIVERED' " +
            "AND o.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal calculateRevenue(@Param("startDate")LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);



    // find recent orders for last 30 days, for admin dashboard
    @Query("SELECT o FROM Order o WHERE o.createdAt >= :since " +
            "ORDER BY o.createdAt DESC")
    List<Order> findRecentOrders(@Param("since") LocalDateTime since);
}































