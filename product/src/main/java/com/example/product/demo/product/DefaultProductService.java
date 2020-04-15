package com.example.product.demo.product;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultProductService implements ProductService {

    @Override
    public Optional<Product> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public ResponseEntity<?> findAll() {
        return null;
    }

    @Override
    public Product save(Product product) {
            return null;
    }

    @Override
    public Optional<Product> updateProduct(Product product) {
        return Optional.empty();
    }
}
