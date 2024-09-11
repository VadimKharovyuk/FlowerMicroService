package com.example.flowershop.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;


    private Long productId;
    private String productName;
    private String productDescription;
    private String imgPath;
    private Integer quantity;
    private BigDecimal totalPrice;
}