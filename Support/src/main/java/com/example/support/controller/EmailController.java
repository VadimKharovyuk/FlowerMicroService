package com.example.support.controller;

import com.example.support.dto.EmailDto;
import com.example.support.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    @PostMapping("/send")
    public void sendEmail(@RequestBody EmailDto emailDto) {
        emailService.sendEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getText());
    }




//
//    @PostMapping("/send")
//    public void sendEmail(
//            @RequestParam String to,
//            @RequestParam String subject,
//            @RequestParam String text) {
//        emailService.sendEmail(to, subject, text);
//    }

}