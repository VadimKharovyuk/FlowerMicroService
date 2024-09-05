package com.example.flowershop.controller;

import com.example.flowershop.dto.CartDTO;
import com.example.flowershop.service.CartClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartClientService cartClientService;

    @GetMapping("/{cartId}")
    public String getCartById(@PathVariable Long cartId, Model model) {
        CartDTO cartDTO = cartClientService.getCartById(cartId);
        if (cartDTO == null) {
            // Отображаем страницу ошибки или перенаправляем на страницу создания корзины
            return "error"; // Замените на соответствующую страницу ошибки
        }
        model.addAttribute("cart", cartDTO);
        return "cart/cart";
    }
    @PostMapping("/create")
    public String createCart() {
        CartDTO cartDTO = cartClientService.createCart(); // Создание новой корзины
        // Перенаправляем на страницу новой корзины после создания
        return "redirect:/carts/" + cartDTO.getId();
    }

    @PostMapping("/{cartId}/items")
    public String addItemToCart(@PathVariable Long cartId,
                                @RequestParam Long productId,
                                @RequestParam int quantity) {
        CartDTO cartDTO = cartClientService.getCartById(cartId);
        if (cartDTO == null) {
            // Если корзина не существует, создаем её
            cartDTO = cartClientService.createCart();
        }
        cartClientService.addItemToCart(cartDTO.getId(), productId, quantity);
        // Перенаправляем обратно на страницу корзины после добавления товара
        return "redirect:/carts/" + cartDTO.getId();
    }
}