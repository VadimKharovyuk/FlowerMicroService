package com.example.flovermodel.service;

import com.example.flovermodel.dto.FlowerFactDTO;
import com.example.flovermodel.mapper.FlowerFactMapper;
import com.example.flovermodel.model.FlowerFact;
import com.example.flovermodel.repository.FlowerFactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FlowerFactService {

    private final FlowerFactRepository flowerFactRepository;
    private final FlowerFactMapper flowerFactMapper;
    private final Random random = new Random();

    public FlowerFactDTO getRandomFact() {
        // Получаем все факты из базы данных
        List<FlowerFact> facts = flowerFactRepository.findAll();

        if (facts.isEmpty()) {
            throw new RuntimeException("No facts found in the database");
        }

        // Выбираем случайный индекс
        int randomIndex = random.nextInt(facts.size());

        // Возвращаем случайный факт, преобразованный в DTO
        FlowerFact flowerFact = facts.get(randomIndex);
        return FlowerFactMapper.toDto(flowerFact);
    }
}