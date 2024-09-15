package com.example.flowershop.service;

import com.example.flowershop.dto.DiscountDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class DiscountClientService {

    @Value("${discount.service.url}")
    private String discountClientUrl;

    private final RestTemplate restTemplate;
    public void deleteById(Long id) {
        String url = discountClientUrl + "/api/discounts/delete/" + id;
        restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
    }

    public List<DiscountDTO> getAll() {
        String url = discountClientUrl + "/api/discounts/all";
        log.debug("Sending GET request to URL: {}", url);
        ResponseEntity<List<DiscountDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DiscountDTO>>() {
                }
        );

        // Проверяем успешность запроса и возвращаем результат
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            log.info("Successfully retrieved {} discounts", response.getBody().size());
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve discounts from URL: " + url);
        }
    }

    public DiscountDTO createDiscount(DiscountDTO discountDTO) {
        String url = discountClientUrl + "/api/discounts";
        log.debug("Sending POST request to URL: {}", url);

        // Формируем тело запроса
        HttpEntity<DiscountDTO> request = new HttpEntity<>(discountDTO);

        // Выполняем POST-запрос к микросервису скидок
        ResponseEntity<DiscountDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                DiscountDTO.class
        );

        // Возвращаем созданную скидку из ответа
        return response.getBody();
    }

    // Метод для получения скидки по ID
    public DiscountDTO getDiscountById(Long id) {
        String url = discountClientUrl + "/api/discounts/" + id;
        log.debug("Sending GET request to URL: {}", url);

        try {
            ResponseEntity<DiscountDTO> response = restTemplate.getForEntity(url, DiscountDTO.class);
            return response.getBody();
        } catch (ResourceAccessException e) {
            log.error("Failed to retrieve discount by ID: {}", e.getMessage());
            throw e;
        }
    }

}