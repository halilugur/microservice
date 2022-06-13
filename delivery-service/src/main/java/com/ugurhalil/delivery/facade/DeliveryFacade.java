package com.ugurhalil.delivery.facade;

import com.ugurhalil.common.constant.DeliveryStatus;
import com.ugurhalil.common.dto.DeliveryDTO;
import com.ugurhalil.common.dto.UserDTO;
import com.ugurhalil.common.entity.Delivery;
import com.ugurhalil.common.entity.User;
import com.ugurhalil.common.util.DataConverter;
import com.ugurhalil.delivery.service.DeliveryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DeliveryFacade {

    private final DeliveryService deliveryService;
    private final DataConverter dataConverter;
    private final RestTemplate restTemplate;

    public List<DeliveryDTO> getAll() {
        List<Delivery> deliveries = deliveryService.getAll();
        return dataConverter.mapList(deliveries, DeliveryDTO.class);
    }

    public List<DeliveryDTO> getDeliveriesByUser(UserDTO userDTO) {
        UserDTO userData = getUserFromService(userDTO.getId());
        User user = dataConverter.map(userData, User.class);
        List<Delivery> deliveries = deliveryService.getDeliveriesByUser(user);
        return dataConverter.mapList(deliveries, DeliveryDTO.class);
    }

    public DeliveryDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryService.getDeliveryById(id).orElseThrow();
        return dataConverter.map(delivery, DeliveryDTO.class);
    }

    public void cancelDelivery(Long id) {
        Delivery delivery = deliveryService.getDeliveryById(id).orElseThrow();
        if (delivery.getStatus().equals(DeliveryStatus.CREATED)
                && !delivery.getStatus().equals(DeliveryStatus.DELIVERED)) {
            delivery.setStatus(DeliveryStatus.CANCEL);
            log.info(id + " delivery has been canceled.");
        }
    }

    public DeliveryDTO changeDeliveryDestination(DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryService.getDeliveryById(deliveryDTO.getId()).orElseThrow();
        if (!delivery.getStatus().equals(DeliveryStatus.DELIVERED)
                && delivery.getStatus().equals(DeliveryStatus.CREATED)
                && !delivery.getStatus().equals(DeliveryStatus.CANCEL)) {
            delivery.setDestination(deliveryDTO.getDestination());
            log.info(delivery.getId() + " delivery destination has been changed.");
        }
        return dataConverter.map(delivery, DeliveryDTO.class);
    }

    public DeliveryDTO addDelivery(DeliveryDTO deliveryDTO) {
        deliveryDTO.setStatus(DeliveryStatus.CREATED);
        Delivery delivery = dataConverter.map(deliveryDTO, Delivery.class);
        UserDTO userDTO = restTemplate.getForObject("http://user-service/users/{id}",
                UserDTO.class,
                deliveryDTO.getUser().getId());
        delivery.setUser(dataConverter.map(userDTO, User.class));
        Delivery deliveryWithUser = deliveryService.save(delivery);
        return dataConverter.map(deliveryWithUser, DeliveryDTO.class);
    }

    public DeliveryDTO changeAssign(DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryService.getDeliveryById(deliveryDTO.getId()).orElseThrow();
        UserDTO userDTO = getUserFromService(deliveryDTO.getUser().getId());
        delivery.setUser(dataConverter.map(userDTO, User.class));
        Delivery deliveryWithNewUser = deliveryService.save(delivery);
        return dataConverter.map(deliveryWithNewUser, DeliveryDTO.class);
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
        log.info(id + " delivery has been deleted.");
    }

    public DeliveryDTO updateDeliveryStatus(DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryService.getDeliveryById(deliveryDTO.getId()).orElseThrow();
        delivery.setStatus(deliveryDTO.getStatus());
        Delivery statusUpdatedDelivery = deliveryService.save(delivery);
        return dataConverter.map(statusUpdatedDelivery, DeliveryDTO.class);
    }

    private UserDTO getUserFromService(Long id) {
        return restTemplate.getForObject("http://user-service/users/{id}",
                UserDTO.class, id);
    }
}
