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
    private Double weight;
    private String countryOfOrigin;
    private BigDecimal price;
    private Integer stockQuantity;

    private String imgPath;


    private Long categoryId;


    private BigDecimal discountedPrice;

    private BigDecimal OriginalPrice ;
}
