package com.example.product.demo.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    ProductService productService;

    @Test
    void testGetProductByIdFound() throws Exception {
        Product mockProduct = new Product(1, "12", 1, "name");
        doReturn(Optional.of(mockProduct)).when(productService).findById(any());
        mockMvc.perform(get("/product/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.id", is("12")))
                .andExpect(jsonPath("$.quantity", is(1)));

    }

    @Test
    void testGetProduct_notFound() throws Exception {
        doReturn(Optional.empty()).when(productService).findById(any());

        mockMvc.perform(get("/product/{id}", 12))
                .andExpect(status().isNotFound());

    }

    @Test
    void testCreateProduct() throws Exception {
        Product savedProduct = Product.builder().id("1").name("name").version(1).quantity(12).build();
        Product mockProduct = Product.builder().name("name").version(1).quantity(12).build();

        doReturn(Optional.empty()).when(productService).save(any());

        mockMvc.perform(post("/product/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockProduct)))
                .andExpect(status().isCreated())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/product/letmetellyout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockProduct)))
                .andExpect(status().isCreated());
        


    }

    @Test
    void testGetProduct() {
    }

    @Test
    void createProduct() {
    }

    @Test
    void updateProduct() {
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}