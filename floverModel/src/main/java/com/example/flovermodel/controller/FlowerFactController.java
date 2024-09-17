package com.example.flovermodel.controller;
import com.example.flovermodel.service.FlowerFactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facts")
@RequiredArgsConstructor
public class FlowerFactController {

    private final FlowerFactService flowerFactService;



    @GetMapping("/random")
    public ResponseEntity<String> getRandomFact() {
        String fact = flowerFactService.getRandomFact().getFact();
        return ResponseEntity.ok(fact);
    }
}