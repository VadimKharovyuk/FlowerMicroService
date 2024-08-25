package com.example.flovermodel.repository;


import com.example.flovermodel.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}