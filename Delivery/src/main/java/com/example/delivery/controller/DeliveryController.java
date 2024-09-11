
package com.example.delivery.controller;

import com.example.delivery.dto.DeliveryDTO;
import com.example.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {

    private final DeliveryService deliveryService;


    @PostMapping("/create")
    public ResponseEntity<String> createDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        try {
            // Логирование перед вызовом сервиса для проверки данных
            System.out.println("Received DeliveryDTO: " + deliveryDTO);

            deliveryDTO.setPaid(false);
            deliveryService.createDelivery(deliveryDTO);

            return new ResponseEntity<>("Delivery created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create delivery: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> getDeliveryById(@PathVariable Long id) {
        try {
            DeliveryDTO deliveryDTO = deliveryService.getDeliveryById(id);
            return new ResponseEntity<>(deliveryDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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