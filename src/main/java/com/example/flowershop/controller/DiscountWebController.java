package com.example.flowershop.controller;

import com.example.flowershop.dto.DiscountDTO;
import com.example.flowershop.dto.ProductDTO;
import com.example.flowershop.service.DiscountClientService;
import com.example.flowershop.service.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/discount")
public class DiscountWebController {

    private final DiscountClientService discountClientService;
    private final ProductServiceClient productServiceClient;

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        discountClientService.deleteById(id);
        return ResponseEntity.noContent().build(); // Отправка ответа без содержимого
    }

    @GetMapping("/all")
    public String showDiscount(Model model) {
        // Получаем список скидок от клиента
        List<DiscountDTO> discounts = discountClientService.getAll();
        List<ProductDTO> productDTOList = productServiceClient.getAllProducts();

        // Создаем карту для быстрого поиска продукта по ID
        Map<Long, String> productIdToNameMap = productDTOList.stream()
                .collect(Collectors.toMap(ProductDTO::getId, ProductDTO::getName));

        // Добавляем имя продукта в каждый объект DiscountDTO
        for (DiscountDTO discount : discounts) {
            String productName = productIdToNameMap.get(discount.getProductId());
            discount.setProductName(productName);
        }
        // Форматируем даты для каждого объекта
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (DiscountDTO discount : discounts) {
            discount.setFormattedStartDate(discount.getStartDate() != null ? discount.getStartDate().format(formatter) : "");
            discount.setFormattedEndDate(discount.getEndDate() != null ? discount.getEndDate().format(formatter) : "");

            String productName = productIdToNameMap.get(discount.getProductId());
            discount.setProductName(productName);
        }

        // Добавляем список скидок в модель
        model.addAttribute("discounts", discounts);

        // Возвращаем имя шаблона для отображения
        return "discount/showDiscount";
    }

    @GetMapping("/create")
    public String showCreateDiscountForm(Model model) {
        log.debug("Fetching all products to display in the discount creation form.");
        var products = productServiceClient.getAllProducts();
        model.addAttribute("discountDTO", new DiscountDTO());
        model.addAttribute("products", products);
        return "discount/discount-form";
    }

    @PostMapping("/create")
    public String createDiscount(@ModelAttribute DiscountDTO discountDTO, Model model) {
        log.debug("Received discount creation request: {}", discountDTO);
        try {
            DiscountDTO createdDiscount = discountClientService.createDiscount(discountDTO);
            log.info("Discount created successfully with ID: {}", createdDiscount.getId());
            model.addAttribute("message", "Discount created successfully with ID: " + createdDiscount.getId());
            return "redirect:/discount/success";
        } catch (Exception e) {
            log.error("Failed to create discount: {}", e.getMessage(), e);
            model.addAttribute("error", "Failed to create discount: " + e.getMessage());
            return "discount/discount-form";
        }
    }

    @GetMapping("/success")
    public String success(Model model) {
        model.addAttribute("product", new DiscountDTO());
        return "discount/discount-success";
    }



}