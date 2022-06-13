package com.ugurhalil.common.dto;

import com.ugurhalil.common.constant.CourierType;
import com.ugurhalil.common.constant.UserRole;
import com.ugurhalil.common.entity.Delivery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private UserRole role;
    private CourierType courierType;
    private Set<Delivery> deliveries;
}
