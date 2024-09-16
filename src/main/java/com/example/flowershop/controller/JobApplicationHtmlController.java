package com.example.flowershop.controller;

import com.example.flowershop.dto.JobApplicationDTO;
import com.example.flowershop.service.JobApplicationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobApplicationHtmlController {

    private final JobApplicationClient jobApplicationClient;

    // Отображение формы заявки на работу
    @GetMapping("/apply")
    public String showApplicationForm(Model model) {
        model.addAttribute("jobApplicationDTO", new JobApplicationDTO());
        return "job/apply"; // Имя HTML-шаблона
    }

    // Обработка формы заявки на работу
    @PostMapping("/apply")
    public String submitApplicationForm(@ModelAttribute JobApplicationDTO jobApplicationDTO) {
        jobApplicationClient.createJob(jobApplicationDTO);
        return "redirect:/job/success"; // Перенаправление на страницу с подтверждением
    }

    // Отображение всех анкет
    @GetMapping("/all")
    public String showAllApplications(Model model) {
        List<JobApplicationDTO> jobApplications = jobApplicationClient.findAll();
        model.addAttribute("jobApplications", jobApplications);
        return "job/applications"; // Имя HTML-шаблона
    }

    // Отображение заявки по ID
    @GetMapping("/{id}")
    public String showApplicationById(@PathVariable Long id, Model model) {
        JobApplicationDTO jobApplicationDTO = jobApplicationClient.findById(id);
        model.addAttribute("jobApplication", jobApplicationDTO);
        return "job/application"; // Имя HTML-шаблона
    }

    // Отображение страницы подтверждения
    @GetMapping("/success")
    public String showSuccessPage() {
        return "job/success"; // Имя HTML-шаблона
    }
}