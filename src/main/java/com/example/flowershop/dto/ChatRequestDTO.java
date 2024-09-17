package com.example.flowershop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestDTO {
    private String model = "gpt-3.5-turbo";
    private List<Message> messages;

    public ChatRequestDTO(String prompt) {
        this.messages = List.of(new Message("user", prompt));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}