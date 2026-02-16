package com.e_commerce.repository;

import com.e_commerce.entity.Product;
import com.e_commerce.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // find by product sku
    Optional<Product> findBySku(String sku);



    // check if the sku already exists
    boolean existsBySku(String sku);



    // find active products only
    Page<Product> findByStatus(ProductStatus status, Pageable pageable);



    // find products by category Id
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);



    // find product by category and status
    Page<Product> findByCategoryIdAndStatus(Long categoryId, ProductStatus status, Pageable pageable);



    // search product by name or description
    @Query("SELECT p FROM Product p WHERE " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND p.status = 'ACTIVE'")
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);



    // find products within price range
    @Query("SELECT p FROM Product p WHERE " +
            "p.price BETWEEN :minPrice AND :maxPrice " +
            "AND p.status = 'ACTIVE' " +
            "ORDER BY p.price ASC")
    List<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice,
                                   @Param("maxPrice") BigDecimal maxPrice);



    // find low stock products , useful for admin dashboard
    @Query("SELECT p FROM Product p WHERE " +
            "p.stockQuantity <= :threshold " +
            "AND p.status = 'ACTIVE'")
    List<Product> findLowStockProducts(@Param("threshold") Integer threshold);



    // Bulk update product status
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.status = :status WHERE p.id IN :ids")
    int updateStatusForProducts(@Param("ids") List<Long> ids,
                                @Param("status") ProductStatus status);



    // reduce stock quantity after order, return number of rows updated, if not enough stocks then no update happens
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.stockQuantity = p.stockQuantity - :quantity " +
            "WHERE p.id = :id AND p.stockQuantity >= :quantity")
    int decreaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);



    // restore stock quantity after order cancellation
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.stockQuantity = p.stockQuantity + :quantity " + "WHERE p.id = :id")
    int increaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);



    // find products with category loaded (avoid N+1)
    @Query("SELECT p FROM Products p JOIN FETCH p.category WHERE p.status = 'ACTIVE'")
    List<Product> findAllActiveWithCategory();

}
