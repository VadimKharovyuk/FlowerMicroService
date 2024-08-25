package com.example.flowershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Double weight;
    private String countryOfOrigin;
    private BigDecimal price;
    private String imageUrl;
    private Integer stockQuantity;
}
