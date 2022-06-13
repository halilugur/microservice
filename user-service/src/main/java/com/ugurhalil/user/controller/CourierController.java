package com.ugurhalil.user.controller;

import com.ugurhalil.common.dto.UserDTO;
import com.ugurhalil.user.facade.CourierFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/couriers")
public class CourierController {
    private final CourierFacade courierFacade;

    @GetMapping
    private List<UserDTO> getAll() {
        return courierFacade.getAllCourier();
    }

    @PostMapping
    private UserDTO addCourier(@RequestBody UserDTO userDTO) {
        return courierFacade.add(userDTO);
    }

    @PutMapping("/{id}")
    private UserDTO update(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        if (!Objects.equals(id, userDTO.getId())) {
            throw new IllegalArgumentException("Please check user id.");
        }
        return courierFacade.update(userDTO);
    }

}
