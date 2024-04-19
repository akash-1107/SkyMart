package com.example.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@PathVariable("categoryId") Long categoryId);
    
}
