package com.example.delivery.model;

import com.example.delivery.dto.CartItemDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String phone;
    private String email;
    private String address;

    private boolean paid; // Оплачена ли доставка

    // Данные из CartItemDTO можно сохранить в этих полях
    private Long productId;
    private String productName;
    private String productDescription;
    private String imgPath;
    private Integer quantity;
    private BigDecimal totalPrice;
}
