package com.example.cart.mapper;

import com.example.cart.dto.CartDTO;
import com.example.cart.dto.CartItemDTO;
import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CartMapper {

    public static CartDTO toDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setPaid(cart.isPaid());
        cartDTO.setItems(cart.getItems().stream().map(CartMapper::toDTO).collect(Collectors.toList()));
        // Здесь можно добавить расчет totalAmount
        return cartDTO;
    }

    public static CartItemDTO toDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setProductId(cartItem.getProductId());
        dto.setProductName(cartItem.getProductName());
        dto.setProductPrice(cartItem.getProductPrice());
        dto.setQuantity(cartItem.getQuantity());
        return dto;
    }

    public static Cart toEntity(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setPaid(cartDTO.isPaid());
        cart.setItems((List<CartItem>) cartDTO.getItems().stream().map(CartMapper::toEntity).collect(Collectors.toSet()));
        return cart;
    }

    public static CartItem toEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());
        cartItem.setProductId(cartItemDTO.getProductId());
        cartItem.setProductName(cartItemDTO.getProductName());
        cartItem.setProductPrice(cartItemDTO.getProductPrice());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        return cartItem;
    }
}