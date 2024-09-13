package com.example.flowershop.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;  // В случае возврата отзыва с базы, ID может понадобиться

    private Long productId;


    private String review;


    private int rating;


}
