package com.example.flowershop.controller;

import com.example.flowershop.service.PayPalClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
@Slf4j
public class PayPalWebController {

    private final PayPalClientService payPalClientService;

    @GetMapping
    public String showPaymentForm() {
        return "pay/payment";
    }

    @PostMapping("/create")
    public String createPayment(@RequestParam Double total,
                                @RequestParam String currency,
                                @RequestParam String description,
                                Model model) {
        try {
            Map<String, String> response = payPalClientService.createPayment(total, currency, description);
            String redirectUrl = response.get("redirect_url");
            if (redirectUrl != null) {
                return "redirect:" + redirectUrl;
            } else {
                model.addAttribute("error", "Failed to create payment: No redirect URL received");
                return "pay/payment";
            }
        } catch (HttpClientErrorException e) {
            log.error("Error creating payment: ", e);
            model.addAttribute("error", "Failed to create payment: " + e.getResponseBodyAsString());
            return "pay/payment";
        } catch (Exception e) {
            log.error("Unexpected error creating payment: ", e);
            model.addAttribute("error", "An unexpected error occurred");
            return "pay/payment";
        }
    }

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId,
                                 @RequestParam("PayerID") String payerId,
                                 Model model) {
        Map<String, String> response = payPalClientService.executePayment(paymentId, payerId);
        if ("success".equals(response.get("status"))) {
            model.addAttribute("transactionId", response.get("transaction_id"));
            return "pay/success-page";
        } else {
            model.addAttribute("error", "Payment not approved");
            return "pay/error-page";
        }
    }

    @GetMapping("/cancel")
    public String paymentCancel(Model model) {
        String response = payPalClientService.cancelPayment();
        model.addAttribute("message", response);
        return "cancel";
    }

    @GetMapping("/error")
    public String paymentError(Model model) {
        String response = payPalClientService.paymentError();
        model.addAttribute("error", response);
        return "error";
    }
}