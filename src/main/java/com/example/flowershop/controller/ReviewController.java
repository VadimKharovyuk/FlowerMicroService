package com.example.flowershop.controller;

import com.example.flowershop.dto.ProductDTO;
import com.example.flowershop.dto.ReviewDto;
import com.example.flowershop.service.ProductServiceClient;
import com.example.flowershop.service.ReviewServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Slf4j
public class ReviewController {

    private final ReviewServiceClient reviewServiceClient;
    private final ProductServiceClient productServiceClient;



    // Получение всех отзывов для определенного продукта
    @GetMapping("/product/{productId}/reviews")
    public String getReviewsByProductId(@PathVariable Long productId, Model model) {
        List<ReviewDto> reviews = reviewServiceClient.getReviewsByProductId(productId);
        model.addAttribute("reviews", reviews);
        // Получение данных о продукте, предполагается, что у вас есть метод в productServiceClient
        ProductDTO product = productServiceClient.getProductById(productId);
        model.addAttribute("product", product);
        return "reviews/reviews-list";
    }


    // Создание нового отзыва
    @PostMapping("/create")
    public String createReview(@RequestParam("review") String review,
                               @RequestParam("rating") int rating,
                               @RequestParam("productId") Long productId,
                               Model model) {
        log.info("Received review: {}, rating: {}, productId: {}", review, rating, productId);

        // Создание объекта ReviewDto из полученных параметров
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReview(review);
        reviewDto.setRating(rating);
        reviewDto.setProductId(productId);

        // Создание отзыва через сервис
        ReviewDto createdReview = reviewServiceClient.createReview(reviewDto);

        // Добавление созданного отзыва в модель
        model.addAttribute("review", createdReview);

        // Возвращение имени представления для отображения
        return "reviews/review-created";  // Страница, которая подтверждает создание отзыва
    }




    // Удаление отзыва
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewServiceClient.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{productId}")
    public String showReviewPage(@PathVariable Long productId, Model model) {
        var  product = productServiceClient.getProductById(productId);
        model.addAttribute("product", product); // Передаем данные о продукте
        model.addAttribute("reviewDto", new ReviewDto()); // Пустая форма для отзыва
        return "reviews/review-page"; // Имя HTML-шаблона
    }

}