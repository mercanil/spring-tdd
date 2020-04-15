package com.example.product.demo.review;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ReviewEntity {
    private String username;
    private Date date;
    private String review;
}
