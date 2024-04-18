package com.example.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory , Long>{
    
}
