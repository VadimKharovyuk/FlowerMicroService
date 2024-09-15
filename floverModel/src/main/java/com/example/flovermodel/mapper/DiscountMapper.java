package com.example.flovermodel.mapper;

import com.example.flovermodel.dto.DiscountDTO;
import com.example.flovermodel.model.Discount;
import com.example.flovermodel.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DiscountMapper {

    // Преобразование из сущности Discount в DTO
    public DiscountDTO toDTO(Discount discount) {
        return DiscountDTO.builder()
                .id(discount.getId())
                .percentage(discount.getPercentage())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .productId(discount.getProduct().getId())  // Получаем id продукта
                .build();
    }

    // Преобразование из DTO в сущность Discount
    public Discount toEntity(DiscountDTO discountDTO) {
        Discount discount = new Discount();
        discount.setId(discountDTO.getId());
        discount.setPercentage(discountDTO.getPercentage());
        discount.setStartDate(discountDTO.getStartDate());
        discount.setEndDate(discountDTO.getEndDate());

        // Создаем сущность Product с id, чтобы установить связь
        Product product = new Product();
        product.setId(discountDTO.getProductId());
        discount.setProduct(product);

        return discount;
    }
}