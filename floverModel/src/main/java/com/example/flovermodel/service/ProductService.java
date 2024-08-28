package com.example.flovermodel.service;

import com.example.flovermodel.dto.ProductDTO;
import com.example.flovermodel.mapper.ProductMapper;
import com.example.flovermodel.model.Category;
import com.example.flovermodel.model.Product;
import com.example.flovermodel.repository.CategoryRepository;
import com.example.flovermodel.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Создание нового продукта
    public ProductDTO createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);
        Product product = ProductMapper.toEntity(productDTO, category);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);
    }

    // Получение продукта по ID
    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ProductMapper::toDTO).orElse(null);
    }

    // Получение всех продуктов
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Обновление продукта
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);
            product = ProductMapper.toEntity(productDTO, category);
            product.setId(id); // Устанавливаем ID существующего продукта
            Product updatedProduct = productRepository.save(product);
            return ProductMapper.toDTO(updatedProduct);
        }
        return null;
    }

    // Удаление продукта
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    // Получение продуктов по категории
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            List<Product> products = productRepository.findByCategory(category);
            return products.stream()
                    .map(ProductMapper::toDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
    public List<ProductDTO> findProductsByName(String name) {
        // Получаем список продуктов по имени
        List<Product> products = productRepository.findByNameIgnoreCase(name);
        // Используем маппер для преобразования продуктов в DTO
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }
}
