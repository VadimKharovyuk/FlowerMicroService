package com.example.flovermodel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
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

    private Long categoryId; // Идентификатор категории


    private BigDecimal discountedPrice;
    private BigDecimal OriginalPrice ;
}
