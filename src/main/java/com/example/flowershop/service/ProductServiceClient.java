package com.example.flowershop.service;

import com.example.flowershop.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceClient {

    @Value("${product.service.url}")
    private String productServiceUrl;

    private final RestTemplate restTemplate;

    public ProductDTO getProductById(Long id) {
        String url = productServiceUrl + "/" + id;
        try {
            return restTemplate.getForObject(url, ProductDTO.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {

            return null;
        }
    }

    public List<ProductDTO> getRelatedProducts(Long id) {
        // URL для конечной точки связанных товаров
        String url = productServiceUrl + "/" + id + "/related";
        try {
            ProductDTO[] relatedProductsArray = restTemplate.getForObject(url, ProductDTO[].class);
            // Преобразуем массив в список и возвращаем его
            return Arrays.asList(relatedProductsArray);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return Collections.emptyList();
        }
    }



    public List<ProductDTO> findProductsByName(String name) {
        String url = productServiceUrl + "/search?name=" + name;

        // Выполняем запрос и обрабатываем результат
        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDTO>>() {}
        );

        return response.getBody();
    }

    public List<ProductDTO> getAllProducts() {
        String url = productServiceUrl;
        ProductDTO[] products = restTemplate.getForObject(url, ProductDTO[].class);
        return products != null ? List.of(products) : List.of();
    }

    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        String url = productServiceUrl + "/category/" + categoryId;
        ProductDTO[] productsByCategory = restTemplate.getForObject(url, ProductDTO[].class);
        return productsByCategory != null ? Arrays.asList(productsByCategory) : List.of();
    }

}
