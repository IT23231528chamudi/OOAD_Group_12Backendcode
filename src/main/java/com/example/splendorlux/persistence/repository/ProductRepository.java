package com.example.splendorlux.persistence.repository;

import com.example.splendorlux.persistence.entity.Categories;
import com.example.splendorlux.persistence.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    List<Products> findByProductNameContainingIgnoreCase(String name);

    // Get products by category ID
    List<Products> findByCategory_CategoryId(Long categoryId);

    // Corrected method for fetching latest 4 products by productID
    List<Products> findTop4ByOrderByProductIDDesc();
}
