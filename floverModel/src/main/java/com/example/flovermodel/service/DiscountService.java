package com.example.flovermodel.service;

import com.example.flovermodel.dto.DiscountDTO;
import com.example.flovermodel.mapper.DiscountMapper;
import com.example.flovermodel.model.Discount;
import com.example.flovermodel.model.Product;
import com.example.flovermodel.repository.DiscountRepository;
import com.example.flovermodel.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    @Transactional
    public DiscountDTO createDiscount(DiscountDTO discountDTO) {
        // Найти продукт по ID
        Product product = productRepository.findById(discountDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + discountDTO.getProductId() + " not found"));

        // Сохранить исходную цену продукта в originalPrice, если она ещё не сохранена
        if (product.getOriginalPrice() == null) {
            product.setOriginalPrice(product.getPrice()); // Сохраняем текущую цену в originalPrice
        }

        // Преобразовать DTO в сущность Discount
        Discount discount = DiscountMapper.toEntity(discountDTO);
        discount.setProduct(product); // Установить связь между скидкой и продуктом

        // Сохранить скидку в базе данных
        Discount savedDiscount = discountRepository.save(discount);

        // Обновить цену продукта с учётом скидки
        BigDecimal discountAmount = product.getPrice().multiply(discountDTO.getPercentage().divide(BigDecimal.valueOf(100)));
        BigDecimal newPrice = product.getPrice().subtract(discountAmount);
        product.setPrice(newPrice);
        productRepository.save(product);

        log.info("Created discount: {} for product: {}", savedDiscount.getId(), product.getId());

        // Преобразовать сущность обратно в DTO и вернуть
        return DiscountMapper.toDTO(savedDiscount);
    }

    public DiscountDTO getById(Long id) {
        return discountRepository.findById(id)
                .map(DiscountMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Discount with ID " + id + " not found"));
    }

    @Transactional
    public DiscountDTO deleteById(Long id) {
        // Найти скидку по ID
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Discount with ID " + id + " not found"));

        // Удалить скидку
        discountRepository.deleteById(id);

        // Восстановить исходную цену продукта
        Product product = discount.getProduct();
        if (product.getOriginalPrice() != null) {
            product.setPrice(product.getOriginalPrice()); // Восстановить исходную цену
            product.setOriginalPrice(null); // Очистить сохраненную исходную цену
            productRepository.save(product); // Сохранить обновления в базе данных
        }

        // Преобразовать сущность в DTO и вернуть
        return DiscountMapper.toDTO(discount);
    }

    public List<DiscountDTO> listDiscount() {
        List<Discount> discounts = discountRepository.findAll();
        log.info("Found {} discounts", discounts.size());
        return discounts.stream()
                .map(DiscountMapper::toDTO)
                .collect(Collectors.toList());
    }

}