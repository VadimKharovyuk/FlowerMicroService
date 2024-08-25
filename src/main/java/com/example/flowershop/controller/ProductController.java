package com.example.flowershop.controller;

import com.example.flowershop.dto.ProductDTO;
import com.example.flowershop.service.ProductServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {

    private  final ProductServiceClient productServiceClient;

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<ProductDTO> products = productServiceClient.getAllProducts();
        model.addAttribute("products", products);
        return "products/products"; // Name of the Thymeleaf template (products.html)
    }
}
