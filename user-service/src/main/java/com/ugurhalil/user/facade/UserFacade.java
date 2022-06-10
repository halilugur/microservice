package com.ugurhalil.user.facade;

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
public class UserFacade {

    private final UserService userService;
    private final DataConverter dataConverter;

    public List<UserDTO> getAllUser() {
        List<User> users = userService.getAllUser();
        return dataConverter.mapList(users, UserDTO.class);
    }

    public UserDTO getUserById(Long id) {
        User user = userService.getUserById(id).orElseThrow();
        return dataConverter.map(user, UserDTO.class);
    }

    public UserDTO add(UserDTO userDTO) {
        User user = userService.save(dataConverter.map(userDTO, User.class));
        return dataConverter.map(user, UserDTO.class);
    }

    public UserDTO update(UserDTO userDTO) {
        if (!userService.isUserExist(userDTO.getId())) {
            throw new ObjectNotFoundException(userDTO.getId(), User.class.getName());
        }
        User user = userService.save(dataConverter.map(userDTO, User.class));
        return dataConverter.map(user, UserDTO.class);
    }

    public void deleteUserById(Long id) {
        log.info(id + " has been deleted.");
        userService.deleteUserById(id);
    }
}
