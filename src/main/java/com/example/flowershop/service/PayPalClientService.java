package com.example.flowershop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayPalClientService {

    @Value("${pay-pal.service.url}")
    private String paypalServiceUrl;

    private final RestTemplate restTemplate;

    public Map<String, String> createPayment(Double total, String currency, String description) {
        String url = paypalServiceUrl + "/api/payment/create";

        Map<String, Object> params = new HashMap<>();
        params.put("total", total);
        params.put("currency", currency);
        params.put("description", description);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

        log.info("Sending request to {}: {}", url, params);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            log.info("Received response: {}", response.getBody());
            return response.getBody();
        } catch (Exception e) {
            log.error("Error creating payment: ", e);
            throw e;
        }
    }


    public Map<String, String> executePayment(String paymentId, String payerId) {
        String url = paypalServiceUrl + "/api/payment/success?paymentId={paymentId}&PayerID={payerId}";

        Map<String, String> params = new HashMap<>();
        params.put("paymentId", paymentId);
        params.put("payerId", payerId);

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class, params);
        return response.getBody();
    }

    public String cancelPayment() {
        String url = paypalServiceUrl + "/api/payment/cancel";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String paymentError() {
        String url = paypalServiceUrl + "/api/payment/error";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}