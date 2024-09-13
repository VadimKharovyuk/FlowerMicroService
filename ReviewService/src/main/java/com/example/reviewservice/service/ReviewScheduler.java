package com.example.reviewservice.service;

import com.example.reviewservice.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewScheduler {
    private final ReviewService reviewService;


    //    @Scheduled(cron = "0 */4 * * * *") // Выполняется каждые 4 минуты
//    public void checkReviews() {
//        List<ReviewDto> reviews = reviewService.getAllReviews();
//        reviewService.checkAndDeleteInappropriateReviews(reviews);
//    }
    @Scheduled(cron = "0 0 */5 * * *") // Выполняется каждые 5 часов
    public void checkReviews() {
        List<ReviewDto> reviews = reviewService.getAllReviews(); // Метод для получения всех отзывов
        reviewService.checkAndDeleteInappropriateReviews(reviews);
    }
}
