package com.example.flovermodel.mapper;

import com.example.flovermodel.dto.ProductDTO;
import com.example.flovermodel.model.Category;
import com.example.flovermodel.model.Product;

public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setWeight(product.getWeight());
        productDTO.setCountryOfOrigin(product.getCountryOfOrigin());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());

        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
        }

        return productDTO;
    }
    // Маппинг из ProductDTO в Product
    public static Product toEntity(ProductDTO productDTO, Category category) {
        if (productDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setWeight(productDTO.getWeight());
        product.setCountryOfOrigin(productDTO.getCountryOfOrigin());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());

        // Маппинг категории
        if (category != null) {
            product.setCategory(category);
        }

        return product;
    }
}
