package com.example.flowershop.controller;

import com.example.flowershop.dto.CategoryDTO;
import com.example.flowershop.service.CategoryServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {


    private final CategoryServiceClient categoryServiceClient;

    @GetMapping("/new")
    public String createCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "categories/categoriesForm";  // Форма для создания категории
    }

    @PostMapping
    public String createCategory(@ModelAttribute("category") CategoryDTO categoryDTO) {
        categoryServiceClient.createCategory(categoryDTO);
        return "redirect:/categories";  // Перенаправление после создания
    }

    @GetMapping
    public String viewCategories(Model model) {
        List<CategoryDTO> categories = categoryServiceClient.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/categories"; // Имя HTML-шаблона, например: src/main/resources/templates/categories.html
    }


}
