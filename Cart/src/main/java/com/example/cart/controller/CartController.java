package com.example.cart.controller;

import com.example.cart.dto.CartDTO;
import com.example.cart.dto.CartItemDTO;
import com.example.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")

public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Добавление товара в корзину
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable Long cartId, @RequestBody CartItemDTO cartItemDTO) {
        CartDTO updatedCart = cartService.addItemToCart(cartId, cartItemDTO);
        return ResponseEntity.ok(updatedCart);
    }

    // Удаление товара из корзины
    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<CartDTO> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        CartDTO updatedCart = cartService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok(updatedCart);
    }
}