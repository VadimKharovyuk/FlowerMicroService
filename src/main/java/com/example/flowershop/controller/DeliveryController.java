package com.example.flowershop.controller;

import com.example.flowershop.dto.CartItemDTO;
import com.example.flowershop.dto.DeliveryDTO;
import com.example.flowershop.service.CartClientService;
import com.example.flowershop.service.DeliveryClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryClientService deliveryClientService;
    private final CartClientService cartClientService;


    @GetMapping("/{id}")
    public String viewDelivery(@PathVariable Long id, Model model) {
        DeliveryDTO deliveryDTO = deliveryClientService.getDeliveryById(id);
        var cartItemDTOS = cartClientService.getAll();

        model.addAttribute("delivery", deliveryDTO);
        model.addAttribute("cartItemDTOS", cartItemDTOS);
        return "delivery/view";
    }

    @GetMapping
    public String listDeliveries(Model model) {
        List<DeliveryDTO> deliveries = deliveryClientService.getAllDeliveries();
        model.addAttribute("deliveries", deliveries);
        return "delivery/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        // Получаем список всех товаров из корзины по cartId
        List<CartItemDTO> cartItems = cartClientService.getAll();

        model.addAttribute("deliveryDTO", new DeliveryDTO());
        model.addAttribute("cartItems", cartItems);
        return "delivery/create";
    }

    @PostMapping("/create")
    public String createDelivery(@ModelAttribute DeliveryDTO deliveryDTO) {
        // Устанавливаем выбранный товар из корзины в DTO доставки
        deliveryClientService.createDelivery(deliveryDTO);
        return "redirect:/deliveries";
    }
    @PostMapping("/pay/{id}")
    public String payForDelivery(@PathVariable Long id) {
        deliveryClientService.markDeliveryAsPaid(id);
        return "redirect:/deliveries";
    }

    @PostMapping("/delete/{id}")
    public String deleteDelivery(@PathVariable Long id) {
        deliveryClientService.deleteDelivery(id);
        return "redirect:/deliveries";
    }
}