package com.example.flovermodel.controller;

import com.example.flovermodel.dto.DiscountDTO;
import com.example.flovermodel.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping("/all")
    public List<DiscountDTO> discountDTOList(){
       return discountService.listDiscount();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        discountService.deleteById(id);
        return ResponseEntity.noContent().build(); // Успешный ответ
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountDTO> getById(@PathVariable Long id) {
        try {
            DiscountDTO discountDTO = discountService.getById(id);
            return ResponseEntity.ok(discountDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Создание новой скидки для продукта
    @PostMapping
    public ResponseEntity<DiscountDTO> createDiscount(@RequestBody DiscountDTO discountDTO) {
        // Вызываем сервис для создания скидки
        DiscountDTO createdDiscount = discountService.createDiscount(discountDTO);

        // Возвращаем созданную скидку с кодом 201 (Created)
        return new ResponseEntity<>(createdDiscount, HttpStatus.CREATED);
    }

}