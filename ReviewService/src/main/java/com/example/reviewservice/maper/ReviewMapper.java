package com.example.reviewservice.maper;

import com.example.reviewservice.dto.ReviewDto;
import com.example.reviewservice.model.Review;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ReviewMapper {
    // Преобразование из DTO в сущность
    public static Review toEntity(ReviewDto dto) {
        return Review.builder()
                .id(dto.getId())
                .productId(dto.getProductId())
                .review(dto.getReview())
                .rating(dto.getRating())
                .build();
    }

    // Преобразование из сущности в DTO
    public static ReviewDto toDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .productId(review.getProductId())
                .review(review.getReview())
                .rating(review.getRating())
                .build();
    }
}
