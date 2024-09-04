package com.example.cart.Fasade;

import com.example.cart.dto.CartDTO;
import com.example.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartFacade {


    private final CartService cartService;  // Используем интерфейс

    // Метод для добавления товара в корзину
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        cartService.addItemToCart(cartId, productId, quantity);
    }

    // Метод для создания корзины
    public CartDTO createCart() {
        return cartService.createCart();
    }

    // Метод для удаления товара из корзины
    public void removeItemFromCart(Long cartId, Long cartItemId) {
        cartService.removeItemFromCart(cartId, cartItemId);
    }

    // Метод для получения корзины по id
    public CartDTO getCartById(Long cartId) {
        return cartService.getCartById(cartId);
    }

    // Метод для чекаута корзины
    public void checkoutCart(Long cartId) {
        cartService.checkoutCart(cartId);
    }
}