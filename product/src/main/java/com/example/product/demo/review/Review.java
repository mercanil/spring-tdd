package com.example.product.demo.review;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collation = "Reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Review {
    private Integer id;
    private Integer productId;
    private Integer version = 1;
    private List<ReviewEntity> reviewEntities = new ArrayList<ReviewEntity>();
}
