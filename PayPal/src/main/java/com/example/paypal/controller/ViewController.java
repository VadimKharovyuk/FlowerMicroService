package com.example.paypal.controller;

import com.paypal.api.payments.Payment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String formPay(Model model){

        model.addAttribute(" pay" , new Payment());
        return "Payment";
    }

    @GetMapping("/cancel")
    public String cansel(){

        return "Cancel";
    }

    @GetMapping("/success")
    public String Success (){
        return "Success";
    }
}
