//package com.example.delivery.mapper;
//
//import com.example.delivery.dto.CartItemDTO;
//import com.example.delivery.dto.DeliveryDTO;
//import com.example.delivery.model.Delivery;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DeliveryMapper {
//
//public Delivery toEntity(DeliveryDTO deliveryDTO) {
//    if (deliveryDTO == null) {
//        throw new IllegalArgumentException("DeliveryDTO cannot be null");
//    }
//
//    Delivery delivery = new Delivery();
//    delivery.setName(deliveryDTO.getName());
//    delivery.setPhone(deliveryDTO.getPhone());
//    delivery.setEmail(deliveryDTO.getEmail());
//    delivery.setAddress(deliveryDTO.getAddress());
//    delivery.setPaid(deliveryDTO.isPaid());
//
//    // Проверяем, что CartItemDTO не null
//    CartItemDTO cartItemDTO = deliveryDTO.getCartItem();
//    if (cartItemDTO != null) {
//        delivery.setProductId(cartItemDTO.getProductId());
//        delivery.setProductName(cartItemDTO.getProductName());
//        delivery.setProductDescription(cartItemDTO.getProductDescription());
//        delivery.setImgPath(cartItemDTO.getImgPath());
//        delivery.setQuantity(cartItemDTO.getQuantity());
//        delivery.setTotalPrice(cartItemDTO.getTotalPrice());
//    }
//
//    return delivery;
//}
//
//    public DeliveryDTO toDTO(Delivery delivery) {
//        if (delivery == null) {
//            throw new IllegalArgumentException("Delivery cannot be null");
//        }
//
//        DeliveryDTO deliveryDTO = new DeliveryDTO();
//        deliveryDTO.setId(delivery.getId()); // Исправлено
//        deliveryDTO.setName(delivery.getName());
//        deliveryDTO.setPhone(delivery.getPhone());
//        deliveryDTO.setEmail(delivery.getEmail());
//        deliveryDTO.setAddress(delivery.getAddress());
//        deliveryDTO.setPaid(delivery.isPaid());
//
//        // Создание CartItemDTO на основе Delivery
//        CartItemDTO cartItemDTO = new CartItemDTO();
//        cartItemDTO.setProductId(delivery.getProductId());
//        cartItemDTO.setProductName(delivery.getProductName());
//        cartItemDTO.setProductDescription(delivery.getProductDescription());
//        cartItemDTO.setImgPath(delivery.getImgPath());
//        cartItemDTO.setQuantity(delivery.getQuantity());
//        cartItemDTO.setTotalPrice(delivery.getTotalPrice());
//
//        deliveryDTO.setCartItem(cartItemDTO);
//
//        return deliveryDTO;
//    }
//
//}

package com.example.delivery.mapper;

import com.example.delivery.dto.CartItemDTO;
import com.example.delivery.dto.DeliveryDTO;
import com.example.delivery.model.Delivery;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

//    public Delivery toEntity(DeliveryDTO deliveryDTO) {
//        if (deliveryDTO == null) {
//            return null;
//        }
//
//        Delivery delivery = new Delivery();
//        delivery.setId(deliveryDTO.getId());
//        delivery.setName(deliveryDTO.getName());
//        delivery.setPhone(deliveryDTO.getPhone());
//        delivery.setEmail(deliveryDTO.getEmail());
//        delivery.setAddress(deliveryDTO.getAddress());
//        delivery.setPaid(deliveryDTO.isPaid());
//
//        if (deliveryDTO.getCartItem() != null) {
//            CartItemDTO cartItemDTO = deliveryDTO.getCartItem();
//            delivery.setProductId(cartItemDTO.getProductId());
//            delivery.setProductName(cartItemDTO.getProductName());  // Убедись, что эти поля заполняются
//            delivery.setProductDescription(cartItemDTO.getProductDescription());
//            delivery.setImgPath(cartItemDTO.getImgPath());
//            delivery.setQuantity(cartItemDTO.getQuantity());
//            delivery.setTotalPrice(cartItemDTO.getTotalPrice());
//        }
//
//        return delivery;
//    }
public static Delivery toEntity(DeliveryDTO deliveryDTO) {
    Delivery delivery = new Delivery();
    delivery.setName(deliveryDTO.getName());
    delivery.setPhone(deliveryDTO.getPhone());
    delivery.setEmail(deliveryDTO.getEmail());
    delivery.setAddress(deliveryDTO.getAddress());
    delivery.setPaid(deliveryDTO.isPaid());

    // Маппинг данных о товаре
    CartItemDTO cartItem = deliveryDTO.getCartItem();
    if (cartItem != null) {
        delivery.setProductId(cartItem.getProductId());
        delivery.setProductName(cartItem.getProductName());
        delivery.setProductDescription(cartItem.getProductDescription());
        delivery.setImgPath(cartItem.getImgPath());
        delivery.setQuantity(cartItem.getQuantity());
        delivery.setTotalPrice(cartItem.getTotalPrice());
    }
    return delivery;
}

    // Метод для преобразования из сущности Delivery в DTO
    public DeliveryDTO toDTO(Delivery delivery) {
        if (delivery == null) {
            return null;
        }

        DeliveryDTO deliveryDTO = new DeliveryDTO();
        deliveryDTO.setId(delivery.getId());
        deliveryDTO.setName(delivery.getName());
        deliveryDTO.setPhone(delivery.getPhone());
        deliveryDTO.setEmail(delivery.getEmail());
        deliveryDTO.setAddress(delivery.getAddress());
        deliveryDTO.setPaid(delivery.isPaid());

        // Маппинг данных в CartItemDTO
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