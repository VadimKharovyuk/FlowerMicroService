package com.example.flowershop.controller;

import com.example.flowershop.dto.EmailDto;
import com.example.flowershop.dto.SupportDto;
import com.example.flowershop.service.SupportServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/support")
public class SupportController {
    private final SupportServiceClient supportServiceClient;

    @GetMapping("/all")
    public String all(Model model) {
        List<SupportDto> supportList = supportServiceClient.dtoList();
        model.addAttribute("supportList", supportList);
        return "Support/listSupport"; // Имя шаблона для отображения
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        SupportDto supportDto = supportServiceClient.getById(id);
        if (supportDto != null) {
            model.addAttribute("support", supportDto);
            model.addAttribute("emailDto", new EmailDto());
            return "Support/viewSupport"; // Имя шаблона для отображения деталей
        } else {
            // Обработка случая, когда ресурс не найден
            return "redirect:/support/all"; // Перенаправление на список в случае ошибки
        }
    }

    @PostMapping("/save")
    public String create(@ModelAttribute SupportDto supportDto) {
        supportServiceClient.save(supportDto);
        return "redirect:/support/all";
    }

    @GetMapping("/form")
    public String formSuport(Model model){
        model.addAttribute("form", new SupportDto());
        model.addAttribute("emailDto", new EmailDto());
        return "Support/form";
    }

}
