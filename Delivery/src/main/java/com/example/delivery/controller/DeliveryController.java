
package com.example.delivery.controller;

import com.example.delivery.dto.DeliveryDTO;
import com.example.delivery.service.DeliveryService;
import com.example.delivery.mapper.DeliveryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> getDeliveryById(@PathVariable Long id) {
        try {
            DeliveryDTO deliveryDTO = deliveryService.getDeliveryById(id);
            return new ResponseEntity<>(deliveryDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        try {
            // Устанавливаем статус оплаченности в false при создании
            deliveryDTO.setPaid(false);

            // Сохраняем доставку через сервис
            deliveryService.createDelivery(deliveryDTO);

            return new ResponseEntity<>("Delivery created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            // Обрабатываем исключение и возвращаем ошибку
            return new ResponseEntity<>("Failed to create delivery: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<String> payForDelivery(@PathVariable Long id) {
        try {
            deliveryService.markDeliveryAsPaid(id);
            return new ResponseEntity<>("Delivery marked as paid successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to mark delivery as paid: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        List<DeliveryDTO> deliveries = deliveryService.getAllDeliveries();
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDelivery(@PathVariable Long id) {
        try {
            deliveryService.deleteDelivery(id);
            return new ResponseEntity<>("Delivery deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete delivery: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}