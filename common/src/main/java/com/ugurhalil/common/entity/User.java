package com.ugurhalil.common.entity;

import com.ugurhalil.common.constant.CourierType;
import com.ugurhalil.common.constant.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private UserRole role;
    private CourierType courierType;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Delivery> deliveries;
}
