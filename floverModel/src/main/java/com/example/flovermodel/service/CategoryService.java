package com.example.flovermodel.service;

import com.example.flovermodel.dto.CategoryDTO;
import com.example.flovermodel.dto.ProductDTO;
import com.example.flovermodel.mapper.CategoryMapper;
import com.example.flovermodel.mapper.ProductMapper;
import com.example.flovermodel.model.Category;
import com.example.flovermodel.repository.CategoryRepository;
import com.example.flovermodel.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;


    // Метод для получения всех категорий
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Метод для получения товаров по категории
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        return category.getProducts().stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Создание новой категории
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toDTO(savedCategory);
    }

    // Получение категории по ID
    public CategoryDTO getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(CategoryMapper::toDTO).orElse(null);
    }


    // Обновление категории
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            Category category = CategoryMapper.toEntity(categoryDTO);
            category.setId(id); // Устанавливаем ID существующей категории
            Category updatedCategory = categoryRepository.save(category);
            return CategoryMapper.toDTO(updatedCategory);
        }
        return null;
    }

    // Удаление категории
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }



}