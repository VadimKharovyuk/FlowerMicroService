package com.example.flowershop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {
    private Long id;

    private BigDecimal percentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long productId;  // ID продукта, связанного со скидкой



    private String formattedStartDate; // Поле для отформатированной даты начала
    private String formattedEndDate;   // Поле для отформатированной даты окончания

    private String productName; // Имя продукта

    private String description ;
}