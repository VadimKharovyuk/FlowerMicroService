package com.example.flowershop.controller;

import com.example.flowershop.dto.EmailDto;
import com.example.flowershop.service.EmailServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailServiceClient emailServiceClient;

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("emailDto", new EmailDto());
        return "email/form"; // Имя HTML-шаблона для отображения формы
    }

    @PostMapping("/send")
    public String sendEmail(@ModelAttribute EmailDto emailDto, Model model) {
        try {
            emailServiceClient.sendEmail(emailDto);
            model.addAttribute("message", "Email sent successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Failed to send email: " + e.getMessage());
        }
        return "email/result"; // Имя HTML-шаблона для отображения результата
    }
}