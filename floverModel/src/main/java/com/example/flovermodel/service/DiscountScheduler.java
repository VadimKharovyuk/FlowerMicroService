//package com.example.flovermodel.service;
//
//import com.example.flovermodel.model.Discount;
//import com.example.flovermodel.model.Product;
//import com.example.flovermodel.repository.DiscountRepository;
//import com.example.flovermodel.repository.ProductRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class DiscountScheduler {
//
//    private final DiscountRepository discountRepository;
//    private final ProductRepository productRepository;
//
//    @Scheduled(cron = "0 0 0 * * ?") // Запуск каждый день в полночь
//    public void restorePrices() {
//        List<Discount> expiredDiscounts = discountRepository.findExpiredDiscounts();
//        for (Discount discount : expiredDiscounts) {
//            Product product = discount.getProduct();
//            restoreProductPrice(product, discount);
//            discountRepository.delete(discount); // Удалить скидку, если она больше не актуальна
//        }
//    }
//
//
//    private void restoreProductPrice(Product product, Discount discount) {
//        BigDecimal discountAmount = product.getPrice().multiply(discount.getPercentage().divide(BigDecimal.valueOf(100)));
//        BigDecimal originalPrice = product.getPrice().add(discountAmount);
//        product.setPrice(originalPrice);
//        productRepository.save(product);
//        log.info("Restored price for product: {} to: {}", product.getId(), originalPrice);
//    }
//}