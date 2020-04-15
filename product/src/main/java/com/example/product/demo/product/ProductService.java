package com.example.product.demo.product;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Integer id);

    ResponseEntity<?> findAll();

    Product save(Product product);

    Optional<Product> updateProduct(Product product);
}
