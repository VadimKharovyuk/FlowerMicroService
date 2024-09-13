package com.example.reviewservice.service;

import com.example.reviewservice.dto.ReviewDto;
import com.example.reviewservice.maper.ReviewMapper;
import com.example.reviewservice.model.Review;
import com.example.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;


    public void delete(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            log.info("Отзыв с ID {} успешно удален", id);
        } else {
            log.warn("Отзыв с ID {} не найден", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Отзыв не найден");
        }
    }

    // Сохранение отзыва
    public ReviewDto saveReview(ReviewDto reviewDto) {
        log.info("Received request to save review: {}", reviewDto);

        // Проверяем, существует ли продукт в другом микросервисе
        boolean productExists = checkIfProductExists(reviewDto.getProductId());

        if (!productExists) {
            log.error("Product with ID {} not found", reviewDto.getProductId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Продукт не найден");
        }

        Review review = ReviewMapper.toEntity(reviewDto);
        Review savedReview = reviewRepository.save(review);
        log.info("Review saved successfully with ID {}", savedReview.getId());
        return ReviewMapper.toDto(savedReview);
    }

    private boolean checkIfProductExists(Long productId) {
        if (productId == null) {
            log.error("Product ID is null. Cannot check product existence.");
            return false;
        }

        String productServiceUrl = "http://localhost:9002/api/products/" + productId;
        try {
            restTemplate.getForObject(productServiceUrl, Void.class);
            return true;
        } catch (Exception e) {
            log.error("Error checking product existence: {}", e.getMessage());
            return false;
        }
    }

    // Получение отзывов по productId
    public List<ReviewDto> getReviewsByProductId(Long productId) {
       List<Review> review  = reviewRepository.findByProductId(productId);
        return  review.stream()
                .map(ReviewMapper::toDto)
                .collect(Collectors.toList());
    }

}