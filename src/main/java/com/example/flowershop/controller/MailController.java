package com.example.flowershop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class MailController {
    @GetMapping()
    public String honePage(){
        return "home";
    }
}
