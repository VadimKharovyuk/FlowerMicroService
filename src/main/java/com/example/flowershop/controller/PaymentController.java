package com.example.flowershop.controller;
import com.example.flowershop.dto.CartItemDTO;
import com.example.flowershop.pay.PaymentRequest;
import com.example.flowershop.service.CartClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final RestTemplate restTemplate;
    private final CartClientService cartClientService;

    @Value("${bank.api.url}") // URL вашего банковского API
    private String bankApiUrl;

    @GetMapping("/checkout")
    public String checkout (){
        return "pay/checkout";

    }

    @GetMapping("/payBank")
    public String formPay(Model model) {
        // Рассчитываем общую сумму корзины
        List<CartItemDTO> cartItemDTOList = cartClientService.getAll();

        // Рассчитываем общую сумму корзины
        BigDecimal totalAmount = cartClientService.calculateTotalAmount(cartItemDTOList);

        model.addAttribute("all", cartItemDTOList);
        model.addAttribute("totalAmount", totalAmount);

        return "pay/PayForm";

    }

    @PostMapping("/process")
    public String processPayment(@RequestParam("amount") BigDecimal amount,
                                 @RequestParam("cardNumber") String cardNumber,
                                 @RequestParam("expiryDate") String expiryDateString,
                                 @RequestParam("cvv") String cvv,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        // Получаем список товаров из корзины
        List<CartItemDTO> cartItems = cartClientService.getAll();

        // Рассчитываем общую сумму корзины
        BigDecimal totalAmount = cartClientService.calculateTotalAmount(cartItems);

        // Проверяем, что amount совпадает с рассчитанной суммой
        if (!amount.equals(totalAmount)) {
            redirectAttributes.addFlashAttribute("error", "Invalid amount provided.");
            return "redirect:/payments/checkout";
        }

        model.addAttribute("totalAmount", totalAmount);
        System.out.println("Received amount: " + amount);

        // Convert MM/YY to a Date object
        Date expiryDate = convertToDate(expiryDateString);

        try {
            // Создаем объект запроса на оплату
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAmount(totalAmount);
            paymentRequest.setCardNumber(cardNumber);
            paymentRequest.setExpirationDate(expiryDate);
            paymentRequest.setCvv(cvv);

            // Отправляем запрос в банк
            String response = restTemplate.postForObject(bankApiUrl + "/api/payments", paymentRequest, String.class);

            // Обрабатываем ответ от банка
            if ("Payment successful".equals(response)) {
                redirectAttributes.addFlashAttribute("message", "Payment processed successfully!");
                return "redirect:/payments/checkout";
            } else {
                redirectAttributes.addFlashAttribute("error", response);
                return "redirect:/payments/checkout";
            }
        } catch (RestClientException e) {
            // Обработка исключений от RestTemplate
            redirectAttributes.addFlashAttribute("error", "Payment failed: " + e.getMessage());
            return "redirect:/payments/checkout";
        }
    }

    // Convert MM/YY to java.sql.Date
    private Date convertToDate(String expiryDateString) {
        String[] parts = expiryDateString.split("/");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid expiry date format. Use MM/YY.");
        }
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]) + 2000; // Assuming the year is in 2000s
        return new Date(year - 1900, month - 1, 1); // java.sql.Date is year - 1900, month - 1
    }

}