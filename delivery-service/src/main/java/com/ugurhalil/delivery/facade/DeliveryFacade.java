package com.ugurhalil.delivery.facade;

import com.ugurhalil.common.constant.DeliveryStatus;
import com.ugurhalil.common.dto.DeliveryDTO;
import com.ugurhalil.common.entity.Delivery;
import com.ugurhalil.common.util.DataConverter;
import com.ugurhalil.delivery.service.DeliveryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DeliveryFacade {

    private final DeliveryService deliveryService;
    private final DataConverter dataConverter;

    public List<DeliveryDTO> getAll() {
        List<Delivery> deliveries = deliveryService.getAll();
        return dataConverter.mapList(deliveries, DeliveryDTO.class);
    }

    public DeliveryDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryService.getDeliveryById(id).orElseThrow();
        return dataConverter.map(delivery, DeliveryDTO.class);
    }

    public DeliveryDTO addDelivery(DeliveryDTO deliveryDTO) {
        deliveryDTO.setStatus(DeliveryStatus.CREATED);
        Delivery delivery = deliveryService.save(dataConverter.map(deliveryDTO, Delivery.class));
        return dataConverter.map(delivery, DeliveryDTO.class);
    }

    public DeliveryDTO updateDelivery(DeliveryDTO deliveryDTO) {
        if (!deliveryService.isDeliveryExist(deliveryDTO.getId())) {
            throw new ObjectNotFoundException(deliveryDTO.getId(), Delivery.class.getName());
        }
        Delivery delivery = deliveryService.save(dataConverter.map(deliveryDTO, Delivery.class));
        return dataConverter.map(delivery, DeliveryDTO.class);
    }

    public void deleteDeliveryById(Long id) {
        deliveryService.deleteDeliveryById(id);
        log.info(id + " has been deleted.");
    }

    public DeliveryDTO updateDeliveryStatus(DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryService.save(dataConverter.map(deliveryDTO, Delivery.class));
        delivery.setStatus(deliveryDTO.getStatus());
        deliveryService.save(delivery);
        return dataConverter.map(delivery, DeliveryDTO.class);
    }
}
