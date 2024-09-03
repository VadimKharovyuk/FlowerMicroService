package com.example.flovermodel.service;

import com.example.flovermodel.dto.ProductDTO;
import com.example.flovermodel.mapper.ProductMapper;
import com.example.flovermodel.model.Category;
import com.example.flovermodel.model.Product;
import com.example.flovermodel.repository.CategoryRepository;
import com.example.flovermodel.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // Обновление продукта
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();

            // Обновляем поля продукта только при необходимости
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setWeight(productDTO.getWeight());
            existingProduct.setCountryOfOrigin(productDTO.getCountryOfOrigin());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
            existingProduct.setImgPath(productDTO.getImgPath());

            // Проверяем, существует ли новая категория
            if (productDTO.getCategoryId() != null) {
                Category category = categoryRepository.findById(productDTO.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));
                existingProduct.setCategory(category);
            }

            // Сохраняем обновленный продукт
            Product updatedProduct = productRepository.save(existingProduct);
            return ProductMapper.toDTO(updatedProduct);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

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

    public List<ProductDTO> findRelatedProducts(Long id) {
        // Находим продукт по ID
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Находим связанные продукты по категории этого продукта
        List<Product> relatedProducts = productRepository.findByCategory(product.getCategory());

        // Преобразуем список связанных продуктов в список DTO и ограничиваем его до 5 элементов
        return relatedProducts.stream()
                .filter(p -> !p.getId().equals(product.getId()))  // Исключаем исходный продукт из списка
                .limit(5)  // Ограничиваем список до 5 элементов
                .map(ProductMapper::toDTO)  // Преобразуем в ProductDTO
                .collect(Collectors.toList());
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        // Находим категорию по ID, если она указана
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Преобразуем DTO в сущность Product
        Product product = ProductMapper.toEntity(productDTO, category);

        // Сохраняем продукт в базе данных
        Product savedProduct = productRepository.save(product);

        // Возвращаем сохранённый продукт в виде DTO
        return ProductMapper.toDTO(savedProduct);
    }
}
