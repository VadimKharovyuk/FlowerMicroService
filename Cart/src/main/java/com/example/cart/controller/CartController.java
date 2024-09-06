package com.example.cart.controller;

import com.example.cart.model.CartItem;
import com.example.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Метод для добавления продукта в корзину
    @PostMapping("/add")
    public ResponseEntity<CartItem> addProductToCart(@RequestParam Long productId, @RequestParam Integer quantity) {
        CartItem cartItem = cartService.addProductToCart(productId, quantity);
        return cartItem != null ? new ResponseEntity<>(cartItem, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Метод для получения элемента корзины по ID
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        CartItem cartItem = cartService.getCartItemById(id);
        return cartItem != null ? new ResponseEntity<>(cartItem, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public List<CartItem> getAll() {
        return cartService.getAll();
    }
}