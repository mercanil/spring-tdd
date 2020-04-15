package com.example.product.demo.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Product {
    private Integer version;
    private String id;
    private Integer quantity;
    private String name;
}
