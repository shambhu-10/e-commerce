package com.e_commerce.repository;

import com.e_commerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // find cart by user id, each user has one cart (@OneToOne)
    Optional<Cart> findByUserId(Long userId);


    // find cart with all items and products loaded , avoid N+1
    @Query("SELECT c FROM Cart c " +
            "LEFT JOIN FETCH c.items i " +
            "LEFT JOIN FETCH i.product " +
            "WHERE c.user.id = :userId")
    Optional<Cart> findByUserIdWithItems(@Param("userId") Long userId);


    // Check if cart exists for user
    boolean existsByUserId(Long userId);
}
