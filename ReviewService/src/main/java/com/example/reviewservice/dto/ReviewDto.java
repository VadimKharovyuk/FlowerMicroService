package com.example.reviewservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
@Builder
public class ReviewDto {
    private Long id;  // В случае возврата отзыва с базы, ID может понадобиться

    private Long productId;

    @NotEmpty(message = "Отзыв не может быть пустым")
    @Length(max = 900)
    private String review;

    @Min(1)
    @Max(5)
    private int rating;



}
