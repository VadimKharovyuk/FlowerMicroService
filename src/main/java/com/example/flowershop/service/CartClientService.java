package com.example.flowershop.service;

import com.example.flowershop.dto.CartItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartClientService {

    @Value("${cart.service.url}") // Убедитесь, что этот URL настроен в вашем application.properties
    private String cartServiceUrl;

    private final RestTemplate restTemplate;

    // Метод для удаления товара из корзины
    public boolean deleteCartItem(Long id) {
        String url = cartServiceUrl + "/delete/" + id;
        try {
            restTemplate.postForEntity(url, null, Void.class);
            return true; // Удаление успешно
        } catch (HttpClientErrorException e) {
            // Логирование ошибки
            System.err.println("Error deleting cart item: " + e.getMessage());
            return false; // Удаление не удалось
        }
    }

    // Метод для добавления продукта в корзину
    public CartItemDTO addProductToCart(Long productId, Integer quantity) {
        String url = cartServiceUrl + "/add?productId=" + productId + "&quantity=" + quantity;
        ResponseEntity<CartItemDTO> response = restTemplate.exchange(url, HttpMethod.POST, null, CartItemDTO.class);
        return response.getBody();
    }

    // Метод для получения элемента корзины по ID
    public CartItemDTO getCartItemById(Long id) {
        String url = cartServiceUrl + "/" + id;
        ResponseEntity<CartItemDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, CartItemDTO.class);
        return response.getBody();
    }
    public List<CartItemDTO> getAll() {
        String url = cartServiceUrl + "/all";
        ResponseEntity<List<CartItemDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CartItemDTO>>() {}
        );
        return response.getBody();
    }

    public BigDecimal calculateTotalAmount(List<CartItemDTO> cartItems) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItemDTO item : cartItems) {
            // Рассчитываем общую стоимость для каждой позиции в корзине
            BigDecimal itemTotalPrice = item.getTotalPrice();
            totalAmount = totalAmount.add(itemTotalPrice);
        }

        return totalAmount;
    }


    public List<CartItemDTO> getCartItems(Long cartId) {
        // Отправляем запрос к внешнему сервису для получения элементов корзины
        ResponseEntity<List<CartItemDTO>> response = restTemplate.exchange(
                cartServiceUrl + "/api/carts/" + cartId + "/items",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CartItemDTO>>() {}
        );

        return response.getBody();
    }
}