package com.example.flowershop.service;

import com.example.flowershop.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceClient {

    private final RestTemplate restTemplate;
    @Value("${review.service.url}")
    private String REVIEW_SERVICE_URL;

    // Получение всех отзывов для определенного продукта
    public List<ReviewDto> getReviewsByProductId(Long productId) {
        String url = REVIEW_SERVICE_URL + "/product/" + productId;
        ReviewDto[] reviews = restTemplate.getForObject(url, ReviewDto[].class);
        return Arrays.asList(reviews);
    }

    // Создание нового отзыва
    public ReviewDto createReview(ReviewDto reviewDto) {
        return restTemplate.postForObject(REVIEW_SERVICE_URL, reviewDto, ReviewDto.class);
    }

    // Удаление отзыва по ID
    public void deleteReview(Long id) {
        String url = REVIEW_SERVICE_URL + "/delete/" + id;
        restTemplate.delete(url);
    }
}