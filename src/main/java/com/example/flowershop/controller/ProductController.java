package com.example.flowershop.controller;

import com.example.flowershop.dto.ProductDTO;
import com.example.flowershop.service.ProductServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceClient productServiceClient;

    // Получение продуктов по категории
    @GetMapping("/category/{categoryId}")
    public String getProductByCategory(@PathVariable Long categoryId, Model model) {
        List<ProductDTO> productDTOListByCategory = productServiceClient.getProductsByCategory(categoryId);
        model.addAttribute("productByCategory", productDTOListByCategory);
        return "products/listProductsByCategory"; // Шаблон для отображения продуктов по категории
    }

    // Получение всех продуктов
    @GetMapping
    public String getAllProducts(Model model) {
        List<ProductDTO> products = productServiceClient.getAllProducts();
        model.addAttribute("products", products);
        return "products/products"; // Шаблон для отображения всех продуктов
    }
}