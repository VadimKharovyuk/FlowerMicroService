package com.example.flovermodel.controller;

import com.example.flovermodel.Fasade.ProductFacade;
import com.example.flovermodel.dto.ProductDTO;
import com.example.flovermodel.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductFacade productFacade;
    @GetMapping("/stock")
    @Cacheable("productsInStock")
    public ResponseEntity<List<ProductDTO>> getProductsInStock() {
        List<ProductDTO> allProducts = productService.getAllProducts();
        List<ProductDTO> productsInStock = allProducts.stream()
                .filter(product -> product.getStockQuantity() != null && product.getStockQuantity() > 0)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsInStock);
    }
    // Обновление продукта
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productFacade.updateProduct(id, productDTO);
        return updatedProduct != null ? new ResponseEntity<>(updatedProduct, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return productDTO != null ? new ResponseEntity<>(productDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}/related")
    public ResponseEntity<List<ProductDTO>> getRelatedProducts(@PathVariable Long id) {
        List<ProductDTO> relatedProducts = productService.findRelatedProducts(id);
        return ResponseEntity.ok(relatedProducts);
    }




   // поиска продуктов по имени
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam String name) {
        // Вызываем сервис для поиска продуктов
        List<ProductDTO> products = productService.findProductsByName(name);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Получение продуктов по категории
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Удаление продукта
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
