package com.example.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.backend.dao.ProductCategoryRepository;
import com.example.backend.dao.ProductRepository;
import com.example.backend.entity.Product;
import com.example.backend.entity.ProductCategory;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    // list of products
    @GetMapping("")
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // individual product by id
    @GetMapping("/{productId}")
    public Optional<Product> getById(@PathVariable Long productId) {
        return productRepository.findById(productId);
    }

    @GetMapping("/category")
    public List<ProductCategory> getAllCategory() {
        return productCategoryRepository.findAll();
    }

    // product list by category id
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // post new product
    @PostMapping("")
    public void addNew(@RequestBody Product product) {
        ProductCategory n = product.getCategory();
        productCategoryRepository.save(n);
        productRepository.save(product);
    }
    //delete a product
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            productRepository.deleteById(productId);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product: " + e.getMessage());
        }
    }

    //
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }
        // Update existing product with data from updated product
        existingProduct.setSku(updatedProduct.getSku());
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setUnitPrice(updatedProduct.getUnitPrice());
        existingProduct.setImageUrl(updatedProduct.getImageUrl());
        existingProduct.setActive(updatedProduct.isActive());
        existingProduct.setUnitsInStock(updatedProduct.getUnitsInStock());
        existingProduct.setCategory(updatedProduct.getCategory());

        Product savedProduct = productRepository.save(existingProduct);
        return ResponseEntity.ok(savedProduct);
    }






    //patch....jo given body me null nhi vhi replace only
    @PatchMapping("/{id}")
    public ResponseEntity<?> partiallyUpdateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }
        // Apply partial updates to existing product
        if (updatedProduct.getName() != null) {
            existingProduct.setName(updatedProduct.getName());
        }
        if (updatedProduct.getDescription() != null) {
            existingProduct.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getUnitPrice() != null) {
            existingProduct.setUnitPrice(updatedProduct.getUnitPrice());
        }
        if (updatedProduct.getImageUrl() != null) {
            existingProduct.setImageUrl(updatedProduct.getImageUrl());
        }
        
        Product savedProduct = productRepository.save(existingProduct);
        return ResponseEntity.ok(savedProduct);
    }

}
