package com.example.flowershop.service;

import com.example.flowershop.dto.DeliveryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryClientService {

    @Value("${delivery.service.url}")
    private String deliveryServiceUrl;

    private final RestTemplate restTemplate;

    public void createDelivery(DeliveryDTO deliveryDTO) {
        String url = deliveryServiceUrl + "/api/deliveries/create";
        restTemplate.postForEntity(url, deliveryDTO, String.class);
    }

    public DeliveryDTO getDeliveryById(Long id) {
        String url = deliveryServiceUrl + "/api/deliveries/" + id;
        return restTemplate.getForObject(url, DeliveryDTO.class);
    }

    public void markDeliveryAsPaid(Long id) {
        String url = deliveryServiceUrl + "/api/deliveries/pay/" + id;
        restTemplate.postForEntity(url, null, String.class);
    }

    public List<DeliveryDTO> getAllDeliveries() {
        String url = deliveryServiceUrl + "/api/deliveries";
        ResponseEntity<List<DeliveryDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DeliveryDTO>>() {}
        );
        return response.getBody();
    }

    public void deleteDelivery(Long id) {
        String url = deliveryServiceUrl + "/api/deliveries/" + id;
        restTemplate.delete(url);
    }
}