package com.example.flowershop.controller;

import com.example.flowershop.dto.ProductDTO;
import com.example.flowershop.service.ProductServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {


    private final ProductServiceClient productServiceClient;

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        ProductDTO product = productServiceClient.getProductById(id);
            model.addAttribute("product", product);
            return "products/productInfo";
        }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<ProductDTO> products = productServiceClient.findProductsByName(name);
        model.addAttribute("products", products);
        return "products/search";
    }


    @GetMapping
    public String getAllProducts(Model model) {
        List<ProductDTO> products = productServiceClient.getAllProducts();
        model.addAttribute("products", products);
        return "products/products"; // Шаблон для отображения всех продуктов
    }

    // Получение продуктов по категории
    @GetMapping("/category/{categoryId}")
    public String getProductByCategory(@PathVariable Long categoryId, Model model) {
        List<ProductDTO> productDTOListByCategory = productServiceClient.getProductsByCategory(categoryId);
        model.addAttribute("productByCategory", productDTOListByCategory);
        return "products/listProductsByCategory";
    }



}