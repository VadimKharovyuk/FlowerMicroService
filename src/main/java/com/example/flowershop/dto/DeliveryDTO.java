package com.example.flowershop.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private boolean paid;
    private CartItemDTO cartItem; // DTO корзины

}
