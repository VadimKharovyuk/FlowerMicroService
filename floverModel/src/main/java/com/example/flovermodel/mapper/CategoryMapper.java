package com.example.flovermodel.mapper;
import com.example.flovermodel.dto.CategoryDTO;
import com.example.flovermodel.dto.ProductDTO;
import com.example.flovermodel.model.Category;
import com.example.flovermodel.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    // Преобразование из Category в CategoryDTO
    public static CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setImage(category.getImage());
        categoryDTO.setDescription(category.getDescription());

        // Проверяем список продуктов на null и преобразуем его
        categoryDTO.setProducts(category.getProducts() != null ?
                category.getProducts().stream()
                        .map(ProductMapper::toDTO) // Используем ProductMapper для маппинга продуктов
                        .collect(Collectors.toList()) : null);

        return categoryDTO;
    }

    // Преобразование из CategoryDTO в Category
    public static Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }

        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setImage(categoryDTO.getImage());
        category.setDescription(categoryDTO.getDescription());

        // Обработка списка продуктов, проверяем на null
        List<Product> products = categoryDTO.getProducts() != null ?
                categoryDTO.getProducts().stream()
                        .map(dto -> {
                            Product product = ProductMapper.toEntity(dto, null); // Передаем null для категории
                            // Устанавливаем категорию в продукт
                            product.setCategory(category);
                            return product;
                        })
                        .collect(Collectors.toList()) : null;

        category.setProducts(products);

        return category;
    }
}