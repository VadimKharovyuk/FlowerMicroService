package com.example.flowershop.service;

import com.example.flowershop.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class EmailServiceClient {

    private final RestTemplate restTemplate;

    @Value("${email.service.url}")
    private String emailServiceUrl;


    public void sendEmail(EmailDto emailDto) {
        String url = emailServiceUrl + "/api/email/send";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<EmailDto> requestEntity = new HttpEntity<>(emailDto, headers);

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to send email. HTTP Status: " + response.getStatusCode());
        }
    }
}