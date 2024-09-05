package com.example.cart.service;

import com.example.cart.dto.CartDTO;
import com.example.cart.dto.CartItemDTO;
import com.example.cart.mapper.CartMapper;
import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;
import com.example.cart.repository.CartItemRepository;
import com.example.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // Метод для поиска корзины по ID
    @Transactional(readOnly = true)
    public CartDTO findCartById(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (optionalCart.isEmpty()) {
            throw new RuntimeException("Cart not found!");
        }

        return CartMapper.toDTO(optionalCart.get());
    }

    // Метод для добавления товара в корзину
    @Transactional
    public CartDTO addItemToCart(Long cartId, CartItemDTO cartItemDTO) {
        // Ищем корзину по ID или создаем новую, если она не найдена
        Cart cart = cartRepository.findById(cartId).orElseGet(() -> {
            Cart newCart = new Cart();
            // newCart.setId(cartId); // Обычно ID генерируется автоматически базой данных
            newCart.setItems(new ArrayList<>());
            newCart.setPaid(false); // Корзина по умолчанию не оплачена
            return cartRepository.save(newCart);  // Сохраняем новую корзину
        });

        // Маппинг CartItemDTO на CartItem и добавление в корзину
        CartItem cartItem = CartMapper.toEntity(cartItemDTO);
        cartItem.setCart(cart);  // Устанавливаем связь с корзиной

        // Добавляем новый товар в список товаров корзины
        cart.getItems().add(cartItem);

        // Сохраняем изменения в корзине
        cartRepository.save(cart);

        // Возвращаем обновленную корзину как DTO
        return CartMapper.toDTO(cart);
    }

    // Метод для удаления товара из корзины
    @Transactional
    public CartDTO removeItemFromCart(Long cartId, Long cartItemId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (optionalCart.isEmpty()) {
            throw new RuntimeException("Cart not found!");
        }

        Cart cart = optionalCart.get();

        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);

        if (optionalCartItem.isEmpty() || !cart.getItems().contains(optionalCartItem.get())) {
            throw new RuntimeException("CartItem not found in the cart!");
        }

        CartItem cartItem = optionalCartItem.get();

        // Удаляем элемент из корзины
        cart.getItems().remove(cartItem);

        // Удаляем элемент корзины из репозитория
        cartItemRepository.delete(cartItem);

        // Сохраняем изменения в корзине
        cartRepository.save(cart);

        return CartMapper.toDTO(cart);
    }
}