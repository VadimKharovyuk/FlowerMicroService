package com.example.flowershop.service;

import com.example.flowershop.dto.ChatRequestDTO;
import com.example.flowershop.dto.ChatResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ChatGPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;


    public String getResponseFromGPT3(String prompt) {
        String url = "https://api.openai.com/v1/engines/davinci-codex/completions";

        // Создайте заголовки для запроса
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Создайте тело запроса
        String body = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 150}";

        // Создайте объект HttpEntity
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        // Выполните запрос
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to get response from GPT-3 API");
        }
    }
}