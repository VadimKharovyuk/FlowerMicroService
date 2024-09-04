package com.example.cart.mapper;

import com.example.cart.dto.CartDTO;
import com.example.cart.dto.CartItemDTO;
import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;
import java.util.stream.Collectors;

public class CartMapper {

    // Преобразование Cart -> CartDTO
    public static CartDTO toCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setPaid(cart.isPaid());
        cartDTO.setTotalAmount(cart.getTotalAmount());

        if (cart.getItems() != null) {
            cartDTO.setItems(cart.getItems().stream()
                    .map(CartMapper::toCartItemDTO)
                    .collect(Collectors.toList()));
        }

        return cartDTO;
    }

    // Преобразование CartDTO -> Cart
    public static Cart toCart(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setPaid(cartDTO.isPaid());
        cart.setTotalAmount(cartDTO.getTotalAmount());

        if (cartDTO.getItems() != null) {
            cart.setItems(cartDTO.getItems().stream()
                    .map(CartMapper::toCartItem)
                    .collect(Collectors.toList()));
        }

        return cart;
    }

    // Преобразование CartItem -> CartItemDTO
    public static CartItemDTO toCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setProduct(cartItem.getProduct());  // ProductDTO already present

        return cartItemDTO;
    }

    // Преобразование CartItemDTO -> CartItem
    public static CartItem toCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setProduct(cartItemDTO.getProduct());  // Transient ProductDTO

        return cartItem;
    }
}