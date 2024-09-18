package com.example.support.controller;

import com.example.support.dto.SupportDto;
import com.example.support.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/support")
public class SupportController {

    private final SupportService supportService;

    @PostMapping("/save")
    public SupportDto save(@RequestBody SupportDto supportDto) {
        SupportDto savedSupportDto = supportService.create(supportDto);
        return savedSupportDto;

    }

    @GetMapping("/all")
    public List<SupportDto> dtoList(){
        return supportService.dtoList();

    }

    @GetMapping("/{id}")
    public SupportDto getById(@PathVariable Long id) {
        return supportService.getById(id);
    }




}
