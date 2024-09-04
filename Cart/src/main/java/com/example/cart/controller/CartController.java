package com.example.cart.controller;

import com.example.cart.Fasade.CartFacade;
import com.example.cart.dto.CartDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartFacade cartFacade;

    // Добавление товара в корзину
    @PostMapping("/{cartId}/items")
    public ResponseEntity<String> addItemToCart(@PathVariable Long cartId,
                                                @RequestParam Long productId,
                                                @RequestParam int quantity) {
        try {
            cartFacade.addItemToCart(cartId, productId, quantity);
            return new ResponseEntity<>("Item added to cart", HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Создание новой корзины
    @PostMapping
    public ResponseEntity<CartDTO> createCart() {
        CartDTO cartDTO = cartFacade.createCart();
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    // Получение корзины по id
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long cartId) {
        try {
            CartDTO cartDTO = cartFacade.getCartById(cartId);
            return new ResponseEntity<>(cartDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    // Удаление товара из корзины
    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartId,
                                                   @PathVariable Long cartItemId) {
        try {
            cartFacade.removeItemFromCart(cartId, cartItemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Чекаут корзины
    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<Void> checkoutCart(@PathVariable Long cartId) {
        try {
            cartFacade.checkoutCart(cartId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}