package com.example.cart.service;

import com.example.cart.dto.ProductDTO;
import com.example.cart.model.CartItem;
import com.example.cart.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String productServiceUrl = "http://localhost:9002/api/products/";

    public CartItem addProductToCart(Long productId, Integer quantity) {
        try {
            // Получение информации о продукте
            ProductDTO product = restTemplate.getForObject(productServiceUrl + productId, ProductDTO.class);

            if (product != null) {
                CartItem cartItem = new CartItem();
                cartItem.setProductId(product.getId());
                cartItem.setProductName(product.getName());
                cartItem.setProductDescription(product.getDescription());
                cartItem.setImgPath(product.getImgPath());
                cartItem.setQuantity(quantity);
                cartItem.updateTotalPrice(product.getPrice().doubleValue()); // Обновление общей цены

                return cartItemRepository.save(cartItem);
            }
        } catch (HttpClientErrorException e) {
            // Логирование ошибки
            System.err.println("Error fetching product: " + e.getMessage());
        }
        return null; // или выбросить исключение, если продукт не найден
    }

    public CartItem getCartItemById(Long id) {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        return cartItem.orElse(null); // Вернуть null, если элемент не найден
    }

    public List<CartItem> getAll(){
       return cartItemRepository.findAll();
    }
}