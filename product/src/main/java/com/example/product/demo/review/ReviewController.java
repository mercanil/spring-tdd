package com.example.product.demo.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/review/{id}")
    public ResponseEntity<?> getReview(@PathVariable("id") Integer id) {
        return reviewService.findById(id)
                .map(review -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .eTag(Integer.toString(review.getVersion()))
                                .location(new URI("/review/" + review.getId()))
                                .body(review);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }

                }).orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("reviews")
    public List<Review> getReviews(@RequestParam(value = "productId", required = false) Optional<String> productId) {
        return productId.map(pid -> {
            return reviewService.findProductById(Integer.valueOf(pid))
                    .map(Arrays::asList)
                    .orElseGet(ArrayList::new);
        }).orElse(reviewService.findAll());
    }

    @PostMapping("/review")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        log.info("Creating new review {} for product id {}", review, review.getProductId());
        review.getReviewEntities().forEach(entity -> entity.setDate(new Date()));
        Review saved = reviewService.save(review);
        log.info("Saved review {}", saved);

        try {
            return ResponseEntity
                    .created(new URI("/review/" + saved.getId()))
                    .eTag(Integer.toString(saved.getVersion()))
                    .body(saved);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/review/{productId}/entry")
    public ResponseEntity<Review> addEntryToReview(@PathVariable("productId") Integer productId, @RequestBody ReviewEntity newReview) {
        Review review = reviewService.getProductReview(productId).orElse(new Review());
        newReview.setDate(new Date());
        review.getReviewEntities().add(newReview);
        reviewService.save(review);
        try {
            return ResponseEntity.
                    created(new URI("/review/" + review.getId() + "/"))
                    .body(review);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") Integer id) {
        Optional<Review> byId = reviewService.findById(id);
        return byId.map(review -> {
            reviewService.delete(review.getId());
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
