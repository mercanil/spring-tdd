package com.example.product.demo.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

@DataMongoTest
class ReviewRepositoryTest {


    /*
        Mongodb lifecycle
        @BeforeEach -> prepopulate from a jsonfile
        @Test -> run test against known data
        @Aftereach -> delete pre-populate data
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ReviewRepository classUnderTest;

    private ObjectMapper mapper = new ObjectMapper();
    private static final File SAMPLE_DATA = Paths.get("src", "test", "resources", "data", "sample.json").toFile();

    @BeforeEach
    void setup() throws IOException {
        Review[] review = mapper.readValue(SAMPLE_DATA, Review[].class);
        Arrays.stream(review).forEach(mongoTemplate::save);
    }

    @AfterEach
    void teardown() {
        mongoTemplate.dropCollection("Reviews");
    }

    @Test
    void findByProductId() {
    }
}