package com.example.product.demo.review;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, Integer> {
    Optional<Review> findByProductId(Integer productId);
}
