package com.ugurhalil.user.controller;

import com.ugurhalil.common.dto.UserDTO;
import com.ugurhalil.user.facade.UserFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserFacade userFacade;

    @GetMapping
    private List<UserDTO> getAll() {
        return userFacade.getAllUser();
    }

    @GetMapping("{id}")
    private UserDTO getUserById(@PathVariable Long id) {
        return userFacade.getUserById(id);
    }

    @PostMapping
    private UserDTO add(@RequestBody UserDTO userDTO) {
        return userFacade.add(userDTO);
    }

    @PutMapping("{id}")
    private UserDTO update(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        if (!Objects.equals(id, userDTO.getId())) {
            throw new IllegalArgumentException("Please check your id.");
        }
        return userFacade.update(userDTO);
    }

    @DeleteMapping("{id}")
    private void deleteUserById(@PathVariable Long id) {
        userFacade.deleteUserById(id);
    }
}
