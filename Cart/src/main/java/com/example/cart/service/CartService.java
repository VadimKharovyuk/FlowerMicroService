package com.example.cart.service;

import com.example.cart.dto.CartDTO;

public interface CartService {
    CartDTO createCart();
    CartDTO getCartById(Long cartId);
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long cartItemId);
    void updateCartTotal(Long cartId);
    void checkoutCart(Long cartId);  // Оплата корзины
}