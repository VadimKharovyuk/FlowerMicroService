package com.example.flowershop.service;

import com.example.flowershop.dto.ChatRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGPTService {

    @Value("${openai.url}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public String getResponseFromGPT3(ChatRequestDTO chatRequestDTO) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Формируем тело запроса с учетом списка сообщений
        StringBuilder messagesJson = new StringBuilder();
        messagesJson.append("[");
        for (ChatRequestDTO.Message message : chatRequestDTO.getMessages()) {
            messagesJson.append("{\"role\":\"")
                    .append(message.getRole())
                    .append("\",\"content\":\"")
                    .append(message.getContent())
                    .append("\"},");
        }
        // Удаляем последнюю запятую и закрываем массив
        if (messagesJson.length() > 1) {
            messagesJson.deleteCharAt(messagesJson.length() - 1);
        }
        messagesJson.append("]");

        String body = "{\"model\": \"" + chatRequestDTO.getModel() + "\", " +
                "\"messages\": " + messagesJson.toString() + "," +
                "\"max_tokens\": 150}";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to get response from GPT API");
            }
        } catch (HttpClientErrorException.TooManyRequests e) {
            log.error("API quota exceeded: {}", e.getMessage());
            return "You have exceeded your API quota. Please check your OpenAI plan or try again later.";

        } catch (Exception e) {
            log.error("Error occurred while calling GPT API: {}", e.getMessage());
            return "Error occurred while communicating with GPT API.";
        }
    }
}
