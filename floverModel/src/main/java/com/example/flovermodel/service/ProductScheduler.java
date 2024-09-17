package com.example.flovermodel.service;

import com.example.flovermodel.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductScheduler {

    private final ProductService productService;


//    @Scheduled(cron = "0 * * * * ?") // Запускается каждую минуту
//    public void checkStockQuantities() {
//        List<ProductDTO> allProducts = productService.getAllProducts();
//        for (ProductDTO product : allProducts) {
//            if (product.getStockQuantity() != null && product.getStockQuantity() > 0) {
//                System.out.println("Product Name: " + product.getName());
//                System.out.println("Stock Quantity: " + product.getStockQuantity());
//            }
//        }
//    }
//}


    @Scheduled(cron = "0 0 * * * ?") // Запускается каждый час
    public void checkStockQuantities() {
        List<ProductDTO> allProducts = productService.getAllProducts();
        for (ProductDTO product : allProducts) {
            if (product.getStockQuantity() != null && product.getStockQuantity() > 0) {
            }
        }
    }
}