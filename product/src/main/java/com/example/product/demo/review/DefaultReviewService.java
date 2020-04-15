package com.example.product.demo.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultReviewService implements ReviewService{

    private final ReviewRepository reviewRepository;
    @Override
    public Optional<Review> findById(Integer id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Optional<Review> findProductById(Integer productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> getProductReview(Integer productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public void delete(Integer id) {
        reviewRepository.deleteById(id);
    }
}
