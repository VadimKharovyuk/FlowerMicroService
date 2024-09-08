//package com.example.cart.service;
//
//import com.example.cart.dto.ProductDTO;
//import com.example.cart.model.CartItem;
//import com.example.cart.repository.CartItemRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CartService {
//
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private final String productServiceUrl = "http://localhost:9002/api/products/";
//
//    public CartItem addProductToCart(Long productId, Integer quantity) {
//        try {
//            // Получение информации о продукте
//            ProductDTO product = restTemplate.getForObject(productServiceUrl + productId, ProductDTO.class);
//
//            if (product != null) {
//                CartItem cartItem = new CartItem();
//                cartItem.setProductId(product.getId());
//                cartItem.setProductName(product.getName());
//                cartItem.setProductDescription(product.getDescription());
//                cartItem.setImgPath(product.getImgPath());
//                cartItem.setQuantity(quantity);
//                cartItem.updateTotalPrice(product.getPrice().doubleValue()); // Обновление общей цены
//
//                return cartItemRepository.save(cartItem);
//            }
//        } catch (HttpClientErrorException e) {
//            // Логирование ошибки
//            System.err.println("Error fetching product: " + e.getMessage());
//        }
//        return null; // или выбросить исключение, если продукт не найден
//    }
//
//    public CartItem getCartItemById(Long id) {
//        Optional<CartItem> cartItem = cartItemRepository.findById(id);
//        return cartItem.orElse(null); // Вернуть null, если элемент не найден
//    }
//
//    public List<CartItem> getAll(){
//       return cartItemRepository.findAll();
//    }
//}

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
            // Получение информации о продукте через REST-запрос
            ProductDTO product = restTemplate.getForObject(productServiceUrl + productId, ProductDTO.class);

            if (product != null) {
                // Создаем элемент корзины и устанавливаем необходимые параметры
                CartItem cartItem = new CartItem();
                cartItem.setProductId(product.getId());
                cartItem.setProductName(product.getName());
                cartItem.setProductDescription(product.getDescription());
                cartItem.setImgPath(product.getImgPath());
                cartItem.setQuantity(quantity);
                cartItem.updateTotalPrice(product.getPrice().doubleValue()); // Обновляем общую цену

                // Сохраняем элемент корзины в базе данных
                CartItem savedItem = cartItemRepository.save(cartItem);

                // Уменьшаем количество товара на складе
                decreaseProductStock(productId, quantity);

                return savedItem;
            }
        } catch (HttpClientErrorException e) {
            // Логирование ошибки при получении информации о продукте
            System.err.println("Error fetching product: " + e.getMessage());
        }
        return null; // Возвращаем null, если продукт не найден или произошла ошибка
    }

    public CartItem getCartItemById(Long id) {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        return cartItem.orElse(null); // Возвращаем элемент корзины или null, если он не найден
    }

    public List<CartItem> getAll() {
        return cartItemRepository.findAll(); // Возвращаем все элементы корзины
    }

    // Вспомогательный метод для уменьшения количества товара в продуктовом микросервисе
    private void decreaseProductStock(Long productId, Integer quantity) {
        String url = productServiceUrl + productId + "/decreaseStock?quantity=" + quantity;
        try {
            restTemplate.put(url, null); // Отправляем запрос на уменьшение количества товара
        } catch (HttpClientErrorException e) {
            // Логирование ошибки при уменьшении количества товара
            System.err.println("Error decreasing stock for product: " + e.getMessage());
        }
    }


    private void increaseProductStock(Long productId, Integer quantity) {
        String url = productServiceUrl + productId + "/increaseStock?quantity=" + quantity;
        try {
            restTemplate.put(url, null); // Отправляем PUT запрос на увеличение количества товара
        } catch (HttpClientErrorException e) {
            // Логирование ошибки при увеличении количества товара
            System.err.println("Error increasing stock for product: " + e.getMessage());
        }
    }

    public boolean deleteCartItem(Long id) {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);

        if (cartItem.isPresent()) {
            CartItem item = cartItem.get();
            Long productId = item.getProductId(); // Предполагаем, что у CartItem есть метод getProductId()
            Integer quantity = item.getQuantity(); // Предполагаем, что у CartItem есть метод getQuantity()

            // Удаляем элемент из корзины
            cartItemRepository.deleteById(id);

            // Возвращаем количество товара на склад
             increaseProductStock(productId, quantity);

            return true; // Удаление успешно
        } else {
            return false; // Элемент не найден
        }
    }
}