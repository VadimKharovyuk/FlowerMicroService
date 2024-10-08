package com.example.flowershop.controller;
import com.example.flowershop.dto.CartItemDTO;
import com.example.flowershop.dto.CategoryDTO;
import com.example.flowershop.service.CartClientService;
import com.example.flowershop.service.CategoryServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/flowershop/cart")
@RequiredArgsConstructor
public class FlowerShopController {

    private final CartClientService cartClientService;
    private final CategoryServiceClient categoryServiceClient;

    @PostMapping("/removeFromCart/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId) {
        cartClientService.deleteCartItem(cartItemId);
        return "redirect:/flowershop/cart/all";
    }

    @GetMapping
    public String viewCart( @PathVariable Long id, Model model) {
        CartItemDTO cartItemDTO = cartClientService.getCartItemById(id);

        model.addAttribute("cartItems", cartItemDTO);
        return "cart/viewCart"; // Название HTML-шаблона для отображения корзины
    }

    @PostMapping("/add")
    public String addProductToCart(@RequestParam Long productId, @RequestParam Integer quantity, Model model) {
        CartItemDTO cartItem = cartClientService.addProductToCart(productId, quantity);
        if (cartItem != null) {
            return "redirect:/products";
        }
        model.addAttribute("errorMessage", "Failed to add product to cart");
        return "errorPage";  // Можно заменить на страницу с ошибкой, если требуется
    }


    @GetMapping("/all")
    public String getAll(Model model) {
        // Получаем список всех элементов корзины
        List<CartItemDTO> cartItemDTOList = cartClientService.getAll();

        // Рассчитываем общую сумму корзины
        BigDecimal totalAmount = cartClientService.calculateTotalAmount(cartItemDTOList);

        // Получаем список всех категорий
        List<CategoryDTO> categories = categoryServiceClient.getAllCategories();

        // Добавляем данные в модель
        model.addAttribute("categories", categories);
        model.addAttribute("all", cartItemDTOList);
        model.addAttribute("totalAmount", totalAmount);

        return "cart/cartItemAdded"; // Название HTML-шаблона для отображения корзины
    }

}