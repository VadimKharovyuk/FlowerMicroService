package com.example.flowershop.service;

import com.example.flowershop.dto.SupportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SupportServiceClient {

    private final RestTemplate restTemplate;

    @Value("${support.service.url}")
    private String supportUrl;

    public SupportDto save(SupportDto supportDto) {
        String url = supportUrl + "/api/support/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SupportDto> request = new HttpEntity<>(supportDto, headers);
        ResponseEntity<SupportDto> response = restTemplate.postForEntity(url, request, SupportDto.class);
        return response.getBody();
    }

    public List<SupportDto> dtoList() {
        String url = supportUrl + "/api/support/all";
        ResponseEntity<List<SupportDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SupportDto>>() {});
        return response.getBody();
    }

    public SupportDto getById(Long id) {
        String url = supportUrl + "/api/support/" + id;
        try {
            ResponseEntity<SupportDto> response = restTemplate.getForEntity(url, SupportDto.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to get SupportDto with id: " + id + ", Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // Логирование ошибки (можно использовать библиотеку для логирования)
            System.err.println("Error occurred while getting SupportDto with id: " + id);
            e.printStackTrace();
            throw new RuntimeException("Error occurred while getting SupportDto with id: " + id, e);
        }
    }
}