package com.example.reviewservice.controller;

import com.example.reviewservice.dto.ReviewDto;
import com.example.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    //удаление
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable  Long id){
        reviewService.delete(id);
        return ResponseEntity.noContent().build();

    }

    // Создание нового отзыва
    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@Validated @RequestBody ReviewDto reviewDto) {
        log.info("Received request to create review: {}", reviewDto);
        ReviewDto createdReview = reviewService.saveReview(reviewDto);
        log.info("Review created successfully: {}", createdReview);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    // Получение всех отзывов для определенного продукта
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByProductId(@PathVariable Long productId) {
        List<ReviewDto> reviews = reviewService.getReviewsByProductId(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}