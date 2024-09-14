package com.example.paypal.controller;

import com.example.paypal.service.PayService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/payment")
public class PayPalControlle {
    private final PayService payService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam("total") Double total,
                                    @RequestParam("currency") String currency,
                                    @RequestParam("description") String description) {
        log.info("Received create payment request: total={}, currency={}, description={}", total, currency, description);
        try {
            String cancelUrl = "http://localhost:9005/api/payment/cancel";
            String successUrl = "http://localhost:9005/api/payment/success";

            // Убедимся, что валюта передается корректно
            String cleanCurrency = currency.split(",")[0].trim();

            Payment payment = payService.createPay(
                    total,
                    cleanCurrency,
                    "paypal",
                    "sale",
                    description,
                    cancelUrl,
                    successUrl
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    Map<String, String> response = new HashMap<>();
                    response.put("redirect_url", links.getHref());
                    return ResponseEntity.ok(response);
                }
            }
        } catch (Exception e) {
            log.error("Error occurred during payment creation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment creation failed");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Approval URL not found");
    }

    @GetMapping("/success")
    public ResponseEntity<?> paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        log.info("Payment success callback received: paymentId={}, PayerID={}", paymentId, payerId);
        try {
            Payment payment = payService.executePay(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                log.info("Payment approved: {}", payment.toJSON());
                // Здесь можно добавить дополнительную логику, например, обновление статуса заказа в вашей системе
                Map<String, String> response = new HashMap<>();
                response.put("status", "success");
                response.put("transaction_id", payment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId());
                return ResponseEntity.ok(response);
            } else {
                log.warn("Payment not approved. Status: {}", payment.getState());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment not approved");
            }
        } catch (PayPalRESTException exception) {
            log.error("Error during payment execution", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed");
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> paymentCancel() {
        return ResponseEntity.ok("Payment cancelled");
    }

    @GetMapping("/error")
    public ResponseEntity<?> paymentError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment error occurred");
    }
}