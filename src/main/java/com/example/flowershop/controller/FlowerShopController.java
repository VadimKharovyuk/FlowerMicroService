package com.example.flowershop.controller;

import com.example.flowershop.dto.CartDTO;
import com.example.flowershop.dto.CartItemDTO;
import com.example.flowershop.service.CartClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/flowershop/cart")
@RequiredArgsConstructor
public class FlowerShopController {

    private final CartClientService cartClientService;

    @GetMapping
    public String viewCart( @PathVariable Long id, Model model) {
        CartItemDTO cartItemDTO = cartClientService.getCartItemById(id);
        model.addAttribute("cartItems", cartItemDTO);
        return "cart/viewCart"; // Название HTML-шаблона для отображения корзины
    }

    @PostMapping("/add")
    public String addProductToCart(@RequestParam Long productId, @RequestParam Integer quantity, Model model) {
        CartItemDTO cartItem = cartClientService.addProductToCart(productId, quantity);
        if (cartItem != null) {
            return "redirect:/products";
        }
        model.addAttribute("errorMessage", "Failed to add product to cart");
        return "errorPage";  // Можно заменить на страницу с ошибкой, если требуется
    }

    @GetMapping("/all")
    public String getAll(Model model){
        List<CartItemDTO> cartItemDTOList = cartClientService.getAll();
        model.addAttribute("all",cartItemDTOList );
        return "cart/cartItemAdded";
    }

}