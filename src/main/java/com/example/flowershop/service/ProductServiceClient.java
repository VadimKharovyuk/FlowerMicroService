package com.example.flowershop.service;

import com.example.flowershop.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductServiceClient {

    @Value("${product.service.url}")
    private String productServiceUrl;

    private final RestTemplate restTemplate;

    public ProductServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductDTO getProductById(Long id) {
        String url = productServiceUrl + "/" + id;
        return restTemplate.getForObject(url, ProductDTO.class);
    }
    public List<ProductDTO> getAllProducts() {
        String url = productServiceUrl; // Assuming productServiceUrl is the base URL
        ProductDTO[] products = restTemplate.getForObject(url, ProductDTO[].class);
        return products != null ? List.of(products) : List.of();
    }


}
