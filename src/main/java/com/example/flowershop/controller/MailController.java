package com.example.flowershop.controller;
import com.example.flowershop.dto.CategoryDTO;
import com.example.flowershop.service.CategoryServiceClient;
import com.example.flowershop.service.FlowerFactServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MailController {
    private final CategoryServiceClient categoryServiceClient;
    private final FlowerFactServiceClient flowerFactServiceClient;


    @GetMapping()
    public String honePage(Model model) {
        List<CategoryDTO> categories = categoryServiceClient.getAllCategories();
        String srt= flowerFactServiceClient.getRandomFact();

        model.addAttribute("randomFact", srt);
        model.addAttribute("categories", categories);
        return "home";
    }
}
