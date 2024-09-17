package com.example.flowershop.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FlowerFactServiceClient {

    @Value("${flowerfact.client.url}")
    private String flowerFactClientUrl;

    private final RestTemplate restTemplate;


    public String getRandomFact() {
        String url = flowerFactClientUrl + "/api/facts/random";
        // Получаем строку факта
        String response = restTemplate.getForObject(url, String.class);

        return response != null ? response : "Fact not found";
    }
}