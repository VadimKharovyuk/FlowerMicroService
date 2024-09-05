package com.example.flowershop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlowerShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowerShopApplication.class, args);
    }


    //-delete product +корзина -корзина продуктов по сессии
    // - доставка (флаг оплаченная )
    //-статус для расписание (progress/)
    // - оплата товара по флагу
    // - добавить расписание (шедулер и количества товаров )
   //отзыввы для товара
    //поиск по странам товар


}
