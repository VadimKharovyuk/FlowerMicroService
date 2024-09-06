package com.example.flowershop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String productName;
    private String productDescription;
    private String imgPath;

}