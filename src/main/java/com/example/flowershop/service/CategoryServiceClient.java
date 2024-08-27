package com.example.flowershop.service;

import com.example.flowershop.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceClient {

    @Value("${category.service.url}")
    private String categoryServiceUrl;

    private final RestTemplate restTemplate;


    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        String url = categoryServiceUrl + "/new";
        return restTemplate.postForObject(url, categoryDTO, CategoryDTO.class);
    }

    public List<CategoryDTO> getAllCategories() {
        CategoryDTO[] categories = restTemplate.getForObject(categoryServiceUrl, CategoryDTO[].class);
        return List.of(categories);
    }

    public CategoryDTO getCategoryById(Long id) {
        String url = categoryServiceUrl + "/" + id;
        return restTemplate.getForObject(url, CategoryDTO.class);
    }
}
