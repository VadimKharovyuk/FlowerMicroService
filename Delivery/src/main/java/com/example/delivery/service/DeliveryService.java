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

    public void createDelivery(DeliveryDTO deliveryDTO) {
        try {
            // Маппинг DTO в сущность Delivery с помощью маппера
            Delivery delivery = deliveryMapper.toEntity(deliveryDTO);

            // Сохраняем сущность в базе данных
            deliveryRepository.save(delivery);

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to save delivery to the database", e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while processing the delivery", e);
        }
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