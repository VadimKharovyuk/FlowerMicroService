package com.example.cart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId; // ID продукта из микросервиса FloverModelApplication

    private Integer quantity; // Количество данного продукта в корзине

    private BigDecimal totalPrice; // Общая цена для данного количества продукта

    // Дополнительные поля для хранения информации о продукте
    private String productName;
    private String productDescription;
    private String imgPath;



    // Метод для обновления общей цены
    public void updateTotalPrice(Double productPrice) {
        this.totalPrice = BigDecimal.valueOf(productPrice).multiply(BigDecimal.valueOf(quantity));
    }
}