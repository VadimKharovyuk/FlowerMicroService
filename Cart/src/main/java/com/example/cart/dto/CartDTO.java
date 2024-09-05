package com.example.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long id;

    private List<CartItemDTO> items;

    private boolean paid;

    private BigDecimal totalAmount;  // Можем добавить это поле, чтобы возвращать общую сумму корзины
}