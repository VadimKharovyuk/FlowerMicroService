package com.example.paypal.controller;

import com.example.paypal.dto.PaymentRequest;
import com.example.paypal.model.PaymentRecord;
import com.example.paypal.service.PayService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PayController {
    private final PayService payService;
    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(
            @RequestParam Double total,
            @RequestParam String currency,
            @RequestParam String method,
            @RequestParam String intent,
            @RequestParam String description,
            @RequestParam String cancelUrl,
            @RequestParam String successUrl
    ) {
        Payment payment = payService.createPay(total, currency, method, intent, description, cancelUrl, successUrl);
        return ResponseEntity.ok(payment);
    }

//    @PostMapping("/create")
//    public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequest paymentRequest) {
//        Payment payment = payService.createPay(
//                paymentRequest.getTotal(),
//                paymentRequest.getCurrency(),
//                paymentRequest.getMethod(),
//                paymentRequest.getIntent(),
//                paymentRequest.getDescription(),
//                paymentRequest.getCancelUrl(),
//                paymentRequest.getSuccessUrl()
//        );
//        return ResponseEntity.ok(payment);
//    }


    @PostMapping("/execute")
    public ResponseEntity<Payment> executePayment(
            @RequestParam String paymentId,
            @RequestParam String payerID
    ) throws PayPalRESTException {
        Payment payment = payService.executePay(paymentId, payerID);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentRecord> getPaymentRecord(@PathVariable String paymentId) {
        PaymentRecord paymentRecord = payService.getPaymentRecord(paymentId);
        return ResponseEntity.ok(paymentRecord);
    }
    @GetMapping("/success")
    public String handleSuccess(
            @RequestParam String paymentId,
            @RequestParam String PayerID) {
        try {
            // Завершаем платеж
            Payment executedPayment = payService.executePay(paymentId, PayerID);
            return "payment-success"; // Или возвращаем что-то другое
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return "payment-failure";
        }
    }
}