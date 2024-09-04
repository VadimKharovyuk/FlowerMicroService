package com.example.cart.service;

import com.example.cart.dto.CartDTO;
import com.example.cart.dto.ProductDTO;
import com.example.cart.mapper.CartMapper;
import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;
import com.example.cart.repository.CartRepository;
import com.example.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;

    @Override
    public CartDTO createCart() {
        Cart cart = new Cart();
        cart.setPaid(false);
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
        return CartMapper.toCartDTO(cart);
    }

    @Override
    public CartDTO getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        return CartMapper.toCartDTO(cart);
    }

    @Override
    @Transactional
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        ProductDTO productDTO = productServiceClient.getProductById(productId);  // Получение товара из микросервиса
        if (productDTO == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setProduct(productDTO);
        cartItem.setCart(cart);

        cart.getItems().add(cartItem);
        updateCartTotal(cartId);

        cartRepository.save(cart);  // Сохраняем изменения в корзине
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        Optional<CartItem> itemToRemove = cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst();

        if (itemToRemove.isPresent()) {
            cart.getItems().remove(itemToRemove.get());
            updateCartTotal(cartId);
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("Cart item not found with id: " + cartItemId);
        }
    }

    @Override
    public void updateCartTotal(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        BigDecimal totalAmount = cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void checkoutCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot checkout an empty cart");
        }

        cart.setPaid(true);
        cartRepository.save(cart);
    }
}