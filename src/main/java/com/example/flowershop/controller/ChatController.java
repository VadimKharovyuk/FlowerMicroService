package com.example.flowershop.controller;

import com.example.flowershop.dto.ChatRequestDTO;
import com.example.flowershop.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatGPTService chatGPTService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody Map<String, String> payload) {
        String prompt = payload.get("prompt");

        // Подготовка сообщения с ролью "user"
        ChatRequestDTO.Message message = new ChatRequestDTO.Message("user", prompt);
        ChatRequestDTO chatRequest = new ChatRequestDTO();
        chatRequest.setMessages(List.of(message)); // Можно добавить больше сообщений в список

        // Отправляем запрос в сервис
        String gptResponse = chatGPTService.getResponseFromGPT3(chatRequest);
        return ResponseEntity.ok(gptResponse);
    }
}