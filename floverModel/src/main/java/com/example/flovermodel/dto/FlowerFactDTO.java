package com.example.flovermodel.dto;

import com.example.flovermodel.mapper.FlowerFactMapper;
import com.example.flovermodel.model.FlowerFact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlowerFactDTO {
    private Long id;
    private String fact ;
}
