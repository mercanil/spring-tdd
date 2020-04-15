package com.example.product.demo.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private MockMvc mockMvc;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");


    @BeforeAll
    static void setup() {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Test
    @DisplayName("Get /review/reviewId FOUND")
    void testGetReviewById_Found() throws Exception {
        Review mockReview = Review.builder().version(1).id(1).productId(1).reviewEntities(new ArrayList<ReviewEntity>()).build();
        Date now = new Date();
        mockReview.getReviewEntities().add(new ReviewEntity("test-user", now, "Great Product"));
        doReturn(Optional.of(mockReview)).when(reviewService).findById(1);
        mockMvc.perform(get("/review/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.productId", is(1)))
                .andExpect(jsonPath("$.entries.length()", is(1)))
                .andExpect(jsonPath("$.entries[0].username", is("test-user")));

    }

    @Test
    void testGetReviewById_NotFound() throws Exception {
        doReturn(Optional.empty()).when(reviewService).findById(any());
        mockMvc.perform(get("/review/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void createReview() throws Exception {
        Date now = new Date();
        Review postReview = Review.builder().id(1).build();
        postReview.getReviewEntities().add(new ReviewEntity("test-username" , now , "Great!"));
        Review mockReview = Review.builder().id(1).productId(1).version(1).build();
        mockReview.getReviewEntities().add(new ReviewEntity("test-user" , now , "Great!"));
        doReturn(mockReview).when(reviewService).save(any());

        mockMvc.perform(post("/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postReview)))
                .andExpect(status().isCreated());

    }

    private String asJsonString(Review postReview) throws JsonProcessingException {
       return new ObjectMapper().writeValueAsString(postReview);
    }

}