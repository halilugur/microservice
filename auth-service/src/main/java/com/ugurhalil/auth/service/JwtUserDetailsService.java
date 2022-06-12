package com.ugurhalil.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.ugurhalil.common.constant.UserRole;
import com.ugurhalil.common.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = restTemplate.getForObject("http://user-service/users/{username}/check", UserDTO.class, username);
        if (Objects.isNull(userDTO)) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(userDTO.getUsername(), userDTO.getPassword(), getAuthorities(userDTO.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserRole role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }
}