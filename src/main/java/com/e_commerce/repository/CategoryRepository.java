package com.e_commerce.repository;

import com.e_commerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // get category by id
    Optional<Category> findByNameIgnoreCase(String name);

    // check duplicate name
    boolean existsByNameIgnoreCase(String name);

    // find all root categories (no parent)
    // these are top level categories
    List<Category> findByParentIsNull();

    // find all active categories
    List<Category> findByActiveTrue();

    // find sub categories of parent
    List<Category> findByParentId(Long id);

    // find active root categories with their sub categories
    // use JOIN FETCH to avoid N+1 problem
    @Query("SELECT DISTINCT c FROM Category c " +
            "LEFT JOIN FETCH c.subCategories" +
            "WHERE c.parent IS NULL AND c.active = true")
    List<Category> findActiveRootCategoriesWithSubCategories();
}
