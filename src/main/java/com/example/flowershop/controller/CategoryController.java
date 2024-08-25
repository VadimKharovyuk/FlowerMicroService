package com.example.flowershop.controller;

import com.example.flowershop.dto.CategoryDTO;
import com.example.flowershop.service.CategoryServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class CategoryController {

private final CategoryServiceClient categoryServiceClient;

    @GetMapping("/categories")
    public String viewCategories(Model model) {
        List<CategoryDTO> categories = categoryServiceClient.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/categories"; // Имя HTML-шаблона, например: src/main/resources/templates/categories.html
    }
}
