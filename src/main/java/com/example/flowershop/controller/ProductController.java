package com.example.flowershop.controller;

import com.example.flowershop.dto.CategoryDTO;
import com.example.flowershop.dto.ProductDTO;
import com.example.flowershop.service.CategoryServiceClient;
import com.example.flowershop.service.ProductServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {


    private final ProductServiceClient productServiceClient;
    private final CategoryServiceClient categoryServiceClient;

    @GetMapping("/new")
    public String addFormCreateProduct(Model model) {
        List<CategoryDTO> categories = categoryServiceClient.getAllCategories();
        model.addAttribute("createProduct", new ProductDTO());
        model.addAttribute("categories", categories);
        return "products/createProductForm";
    }
    // POST-запрос для отправки формы и сохранения продукта
    @PostMapping("/add")
    public String addProduct(@ModelAttribute("createProduct") ProductDTO productDTO) {
        productServiceClient.createProduct(productDTO);
        return "redirect:/products";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        ProductDTO product = productServiceClient.getProductById(id);
        List<ProductDTO> relatedProducts = productServiceClient.getRelatedProducts(id);

        model.addAttribute("relatedProducts", relatedProducts);
        model.addAttribute("product", product);
        return "products/productInfo";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<ProductDTO> products = productServiceClient.findProductsByName(name);
        model.addAttribute("products", products);
        return "products/search";
    }


    @GetMapping
    public String getAllProducts(Model model) {
        List<ProductDTO> products = productServiceClient.getAllProducts();
        model.addAttribute("products", products);
        return "products/products"; // Шаблон для отображения всех продуктов
    }

    // Получение продуктов по категории
    @GetMapping("/category/{categoryId}")
    public String getProductByCategory(@PathVariable Long categoryId, Model model) {
        List<ProductDTO> productDTOListByCategory = productServiceClient.getProductsByCategory(categoryId);
        model.addAttribute("productByCategory", productDTOListByCategory);
        return "products/listProductsByCategory";
    }



}