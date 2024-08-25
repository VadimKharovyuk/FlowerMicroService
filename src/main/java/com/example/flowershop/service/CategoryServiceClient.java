package com.example.flowershop.service;

import com.example.flowershop.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CategoryServiceClient {

    @Value("${category.service.url}")
    private String categoryServiceUrl;

    private final RestTemplate restTemplate;

    public CategoryServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CategoryDTO> getAllCategories() {
        // Обращаемся по базовому URL, без добавления /api/categories здесь
        CategoryDTO[] categories = restTemplate.getForObject(categoryServiceUrl, CategoryDTO[].class);
        return List.of(categories);
    }

    public CategoryDTO getCategoryById(Long id) {
        String url = categoryServiceUrl + "/" + id;
        return restTemplate.getForObject(url, CategoryDTO.class);
    }
}
