package com.example.product.demo.review;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class DefaultReviewServiceTest {

    @Autowired
    ReviewService classUnderTest;

    @MockBean
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("Test findbyid success")
    void findById_Success() {
        Review mockReview = Review.builder()
                .id(1)
                .version(1)
                .productId(1)
                .reviewEntities(new LinkedList<>())
                .build();
        Date now = new Date();
        mockReview.getReviewEntities().add(new ReviewEntity("test-user", now, "Great"));
        doReturn(Optional.of(mockReview)).when(reviewRepository).findById(1);
        Optional<Review> result = classUnderTest.findById(1);
        Assert.assertTrue(result.isPresent());
        Assert.assertSame(mockReview, mockReview);

    }

    @Test
    void findById_NotFound() {
        doReturn(Optional.empty()).when(reviewRepository).findById(any());
        Optional<Review> byId = classUnderTest.findById(1);
        Assert.assertFalse(byId.isPresent());

    }

    @Test
    void findAll() {
    }

    @Test
    void save() {
    }

    @Test
    void getProductReview() {
    }

    @Test
    void delete() {
    }
}