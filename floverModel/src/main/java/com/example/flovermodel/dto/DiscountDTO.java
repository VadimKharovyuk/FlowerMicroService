package com.example.flovermodel.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class DiscountDTO {
    private Long id;
    private BigDecimal percentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long productId;  // ID продукта, связанного со скидкой
    private String description;


}
