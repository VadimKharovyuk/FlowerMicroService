package com.example.flovermodel.repository;

import com.example.flovermodel.model.Category;
import com.example.flovermodel.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategory(Category category);
}