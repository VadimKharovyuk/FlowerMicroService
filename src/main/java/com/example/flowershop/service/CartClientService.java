package com.example.flowershop.service;

import com.example.flowershop.dto.CartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CartClientService {

    @Value("${cart.service.url}")
    private String cartServiceUrl;

    private final RestTemplate restTemplate;

    public CartDTO getCartById(Long cartId) {
        try {
            return restTemplate.getForObject(cartServiceUrl + "/" + cartId, CartDTO.class);
        } catch (Exception e) {
            // Обработка ошибок
            return null;
        }
    }

    public void addItemToCart(Long cartId, Long productId, int quantity) {
        try {
            restTemplate.postForObject(cartServiceUrl + "/" + cartId + "/items?productId=" + productId + "&quantity=" + quantity, null, Void.class);
        } catch (Exception e) {
            // Обработка ошибок
        }
    }

    public CartDTO createCart() {
        try {
            return restTemplate.postForObject(cartServiceUrl, null, CartDTO.class);
        } catch (Exception e) {
            // Обработка ошибок
            return null;
        }
    }
}