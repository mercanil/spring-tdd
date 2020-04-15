package com.example.product.demo.review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Optional<Review> findById(Integer id);

    Optional<Review> findProductById(Integer productId);

    List<Review> findAll();

    Review save(Review review);

    Optional<Review> getProductReview(Integer productId);

    void delete(Integer id);
}
