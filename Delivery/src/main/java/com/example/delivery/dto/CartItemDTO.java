package com.example.delivery.dto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long productId;
    private String productName;
    private String productDescription;
    private String imgPath;
    private Integer quantity;
    private BigDecimal totalPrice;
}
