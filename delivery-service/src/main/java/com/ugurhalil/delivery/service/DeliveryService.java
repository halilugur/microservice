package com.ugurhalil.delivery.service;

import com.ugurhalil.common.entity.Delivery;
import com.ugurhalil.delivery.repository.DeliveryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public List<Delivery> getAll() {
        return deliveryRepository.findAll();
    }

    public Optional<Delivery> getDeliveryById(Long id) {
        return deliveryRepository.findById(id);
    }

    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public void deleteDeliveryById(Long id) {
        deliveryRepository.deleteById(id);
    }

    public boolean isDeliveryExist(Long id) {
        return deliveryRepository.existsById(id);
    }
}
