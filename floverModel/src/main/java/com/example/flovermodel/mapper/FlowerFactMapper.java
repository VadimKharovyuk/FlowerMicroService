package com.example.flovermodel.mapper;

import com.example.flovermodel.dto.FlowerFactDTO;
import com.example.flovermodel.model.FlowerFact;
import lombok.experimental.UtilityClass;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FlowerFactMapper {
    public static FlowerFactDTO toDto(FlowerFact flowerFact){
        return FlowerFactDTO.builder()
                .fact(flowerFact.getFact())
                .id(flowerFact.getId())
                .build();
    }
    public  static  FlowerFact toEntity(FlowerFactDTO flowerFactDTO) {
        FlowerFact flowerFact = new FlowerFact();
        flowerFact.setId(flowerFactDTO.getId());
        flowerFact.setFact(flowerFactDTO.getFact());
        return flowerFact;
    }
}
