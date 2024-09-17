package com.example.flovermodel.repository;

import com.example.flovermodel.model.FlowerFact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerFactRepository extends JpaRepository<FlowerFact,Long> {
}
