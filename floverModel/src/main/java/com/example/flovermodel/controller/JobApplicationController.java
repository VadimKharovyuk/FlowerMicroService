package com.example.flovermodel.controller;

import com.example.flovermodel.dto.JobApplicationDTO;
import com.example.flovermodel.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobApplicationController {

    private final JobApplicationService service;

    // POST-запрос для создания новой анкеты
    @PostMapping
    public ResponseEntity<JobApplicationDTO> create(@RequestBody JobApplicationDTO jobApplicationDTO) {
        JobApplicationDTO createdJobApplication = service.create(jobApplicationDTO);
        return ResponseEntity.status(201).body(createdJobApplication); // Возвращаем статус 201 Created и созданный объект
    }

    // GET-запрос для получения всех анкет
    @GetMapping("/all")
    public ResponseEntity<List<JobApplicationDTO>> findAll() {
        List<JobApplicationDTO> jobApplications = service.list();
        return ResponseEntity.ok(jobApplications); // Возвращаем список анкет со статусом 200 OK
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationDTO> findById(@PathVariable Long id){
        JobApplicationDTO jobApplicationDTO = service.findById(id);
        return ResponseEntity.ok(jobApplicationDTO);
    }
}