package com.example.flowershop.controller;

import com.example.flowershop.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatGPTService chatGPTService;

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt) {
        return chatGPTService.getResponseFromGPT3(prompt);
    }
}