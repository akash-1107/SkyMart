package com.example.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    
}
