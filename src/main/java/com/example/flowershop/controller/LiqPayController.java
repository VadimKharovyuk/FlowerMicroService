//package com.example.flowershop.controller;
//
//import com.example.flowershop.service.LiqPayService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class LiqPayController {
//
//    @Autowired
//    private LiqPayService liqPayService;
//
//    @GetMapping("/create-payment")
//    @ResponseBody
//    public String createPayment(
//            @RequestParam String amount,
//            @RequestParam String currency,
//            @RequestParam String description,
//            @RequestParam String orderId) {
//        try {
//            // Генерация формы оплаты
//            return liqPayService.generatePaymentForm(amount, currency, description, orderId);
//        } catch (Exception e) {
//            return "Ошибка при создании платежа: " + e.getMessage();
//        }
//    }
//}


package com.example.flowershop.controller;

import com.example.flowershop.service.LiqPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LiqPayController {

    @Autowired
    private LiqPayService liqPayService;

    @GetMapping("/payment-form")
    public String showPaymentForm() {
        return "liqpay/payment-form"; // Возвращает HTML-шаблон с формой
    }

    @GetMapping("/create-payment")
    public String createPayment(
            @RequestParam String amount,
            @RequestParam String currency,
            @RequestParam String description,
            @RequestParam String orderId,
            Model model) {
        try {
            // Генерация формы оплаты через сервис LiqPay
            String paymentFormHtml = liqPayService.generatePaymentForm(amount, currency, description, orderId);
            model.addAttribute("paymentFormHtml", paymentFormHtml);
            return "liqpay/payment-result"; // Шаблон с результатом
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при создании платежа: " + e.getMessage());
            return "payment-error"; // Шаблон для отображения ошибки
        }
    }
}