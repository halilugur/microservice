package com.ugurhalil.delivery.controller;

import com.ugurhalil.common.dto.DeliveryDTO;
import com.ugurhalil.common.dto.UserDTO;
import com.ugurhalil.delivery.facade.DeliveryFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/deliveries")
public class DeliveryController {
    private DeliveryFacade deliveryFacade;

    @GetMapping
    private List<DeliveryDTO> getAll() {
        return deliveryFacade.getAll();
    }

    @GetMapping("/user/{id}")
    private List<DeliveryDTO> getAll(@PathVariable Long id) {
        return deliveryFacade.getDeliveriesByUser(UserDTO.builder().id(id).build());
    }

    @GetMapping("/{id}")
    private DeliveryDTO getDeliveryById(@PathVariable Long id) {
        return deliveryFacade.getDeliveryById(id);
    }

    @GetMapping("/{id}/cancel")
    private void cancelDeliveryById(@PathVariable Long id) {
        deliveryFacade.cancelDelivery(id);
    }

    @PostMapping("/{id}/dest")
    private DeliveryDTO changeDestination(@RequestBody DeliveryDTO deliveryDTO) {
        return deliveryFacade.changeDeliveryDestination(deliveryDTO);
    }

    @PostMapping
    private DeliveryDTO add(@RequestBody DeliveryDTO deliveryDTO) {
        return deliveryFacade.addDelivery(deliveryDTO);
    }

    @PutMapping("/{id}")
    private DeliveryDTO update(@RequestBody DeliveryDTO deliveryDTO, @PathVariable Long id) {
        checkDTOWithId(deliveryDTO, id);
        return deliveryFacade.updateDelivery(deliveryDTO);
    }

    @PatchMapping("/{id}")
    private DeliveryDTO assignCourier(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        return deliveryFacade.changeAssign(DeliveryDTO.builder().user(userDTO).id(id).build());
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        deliveryFacade.deleteDeliveryById(id);
    }

    @PostMapping("/{id}/status")
    private DeliveryDTO updateStatus(@RequestBody DeliveryDTO deliveryDTO, @PathVariable Long id) {
        checkDTOWithId(deliveryDTO, id);
        return deliveryFacade.updateDeliveryStatus(deliveryDTO);
    }

    private void checkDTOWithId(DeliveryDTO deliveryDTO, Long id) {
        if (!Objects.equals(id, deliveryDTO.getId())) {
            throw new IllegalArgumentException("Please check delivery id.");
        }
    }
}
