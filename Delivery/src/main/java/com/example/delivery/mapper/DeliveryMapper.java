package com.example.delivery.mapper;

import com.example.delivery.dto.CartItemDTO;
import com.example.delivery.dto.DeliveryDTO;
import com.example.delivery.model.Delivery;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

    public Delivery toEntity(DeliveryDTO deliveryDTO) {
        if (deliveryDTO == null || deliveryDTO.getCartItem() == null) {
            throw new IllegalArgumentException("DeliveryDTO or CartItemDTO cannot be null");
        }

        Delivery delivery = new Delivery();
        // Удалите следующую строку
        // delivery.setId(delivery.getId());
        delivery.setName(deliveryDTO.getName());
        delivery.setPhone(deliveryDTO.getPhone());
        delivery.setEmail(deliveryDTO.getEmail());
        delivery.setAddress(deliveryDTO.getAddress());
        delivery.setPaid(deliveryDTO.isPaid());

        CartItemDTO cartItemDTO = deliveryDTO.getCartItem();
        delivery.setProductId(cartItemDTO.getProductId());
        delivery.setProductName(cartItemDTO.getProductName());
        delivery.setProductDescription(cartItemDTO.getProductDescription());
        delivery.setImgPath(cartItemDTO.getImgPath());
        delivery.setQuantity(cartItemDTO.getQuantity());
        delivery.setTotalPrice(cartItemDTO.getTotalPrice());

        return delivery;
    }

    public DeliveryDTO toDTO(Delivery delivery) {
        if (delivery == null) {
            throw new IllegalArgumentException("Delivery cannot be null");
        }

        DeliveryDTO deliveryDTO = new DeliveryDTO();
        deliveryDTO.setId(delivery.getId()); // Исправлено
        deliveryDTO.setName(delivery.getName());
        deliveryDTO.setPhone(delivery.getPhone());
        deliveryDTO.setEmail(delivery.getEmail());
        deliveryDTO.setAddress(delivery.getAddress());
        deliveryDTO.setPaid(delivery.isPaid());

        // Создание CartItemDTO на основе Delivery
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setProductId(delivery.getProductId());
        cartItemDTO.setProductName(delivery.getProductName());
        cartItemDTO.setProductDescription(delivery.getProductDescription());
        cartItemDTO.setImgPath(delivery.getImgPath());
        cartItemDTO.setQuantity(delivery.getQuantity());
        cartItemDTO.setTotalPrice(delivery.getTotalPrice());

        deliveryDTO.setCartItem(cartItemDTO);

        return deliveryDTO;
    }
}