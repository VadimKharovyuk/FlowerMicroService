package com.example.paypal.controller;

import com.example.paypal.service.PayService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PayPalController {
    private final PayService payService;


    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/payment/create")
    public RedirectView create(@RequestParam("total") Double total,
                               @RequestParam("currency") String currency,
                               @RequestParam("description") String description) {
        try {
            String cancelUrl = "http://localhost:9005/payment/cancel";
            String successUrl = "http://localhost:9005/payment/success";

            Payment payment = payService.createPay(
                    total,
                    currency,
                    "paypal",
                    "sale",
                    description,
                    cancelUrl,
                    successUrl
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (Exception e) {
            log.error("Error occurred during payment creation: {}", e.getMessage());
        }

        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String paymentSuccsess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("paymentId") String payerId
    ) {
        try {
            Payment payment = payService.executePay(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "Success";
            }
        } catch (PayPalRESTException exception) {
            log.error("Erorr", exception);
        }
        return "Success";
    }


    @GetMapping("/payment/cansel")
    public String paymentCansel() {
        return "Cancel";
    }

    @GetMapping("/payment/error")
    public String paymenterror() {
        return "error";
    }
}
