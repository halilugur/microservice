package com.ugurhalil.user.facade;

import com.ugurhalil.common.constant.UserRole;
import com.ugurhalil.common.dto.UserDTO;
import com.ugurhalil.common.entity.User;
import com.ugurhalil.common.util.DataConverter;
import com.ugurhalil.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CourierFacade {
    private final UserService userService;
    private final UserFacade userFacade;
    private final DataConverter dataConverter;

    public List<UserDTO> getAllCourier() {
        List<User> users = userService.getUsersByRole(UserRole.COURIER);
        return dataConverter.mapList(users, UserDTO.class);
    }

    public UserDTO add(UserDTO userDTO) {
        userDTO.setRole(UserRole.COURIER);
        return userFacade.add(userDTO);
    }

    public UserDTO update(UserDTO userDTO) {
        if (!userService.isUserExist(userDTO.getId())) {
            throw new ObjectNotFoundException(userDTO.getId(), User.class.getName());
        }
        userDTO.setRole(UserRole.COURIER);
        User user = userService.save(dataConverter.map(userDTO, User.class));
        return dataConverter.map(user, UserDTO.class);
    }
}
