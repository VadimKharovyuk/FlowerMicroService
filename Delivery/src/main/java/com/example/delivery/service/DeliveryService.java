package com.example.delivery.service;

import com.example.delivery.dto.DeliveryDTO;
import com.example.delivery.mapper.DeliveryMapper;
import com.example.delivery.model.Delivery;
import com.example.delivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryMapper.toEntity(deliveryDTO);
        Delivery savedDelivery = deliveryRepository.save(delivery);
        return deliveryMapper.toDTO(savedDelivery); // Обязательно возвращай DTO
    }

    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryRepository.findAll().stream()
                .map(deliveryMapper::toDTO)
                .collect(Collectors.toList());
    }
    // Метод для обновления статуса оплаты
    public void markDeliveryAsPaid(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));

        delivery.setPaid(true);
        deliveryRepository.save(delivery);
    }
    public DeliveryDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Delivery not found with id: " + id));
        return deliveryMapper.toDTO(delivery);
    }
    public void updateDeliveryPaymentStatus(Long deliveryId, boolean isPaid) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid delivery ID: " + deliveryId));
        delivery.setPaid(isPaid);
        deliveryRepository.save(delivery);
    }

    public void deleteDelivery(Long id) {
  deliveryRepository.deleteById(id);
    }
}