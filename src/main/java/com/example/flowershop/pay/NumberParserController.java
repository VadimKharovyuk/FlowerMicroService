package com.example.flowershop.pay;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/number")
public class NumberParserController {

    // Метод, который принимает строку и возвращает трехзначное число
    @GetMapping("/parse")
    public ResponseEntity<String> parseNumber(@RequestParam String input) {
        try {
            int result = NumberParser.parseToThreeDigitNumber(input);
            return ResponseEntity.ok("Трехзначное число: " + result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
    // Метод для проверки валидности CVV
    @PostMapping("/validate-cvv")
    public ResponseEntity<String> validateCvv(@RequestParam String cvv) {
        try {
            NumberParser.validateCvv(cvv);
            return ResponseEntity.ok("CVV валиден.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
}