package com.example.cart.service;

import com.example.cart.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductServiceClient {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;


    public ProductDTO getProductById(Long productId) {
        // Формируем URL для запроса к микросервису
        String url = productServiceUrl + "/api/products/" + productId;

        try {
            // Отправляем GET-запрос к другому микросервису и получаем ProductDTO
            return restTemplate.getForObject(url, ProductDTO.class);
        } catch (Exception e) {
            // Обрабатываем исключения, например, если продукт не найден
            System.err.println("Error fetching product with id: " + productId);
            return null;
        }
    }
}