package com.ugurhalil.auth.controller;

import com.ugurhalil.auth.model.UserCredentials;
import com.ugurhalil.auth.model.TokenResponse;
import com.ugurhalil.auth.service.DefaultUserDetailsService;
import com.ugurhalil.auth.util.DefaultTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final DefaultTokenUtil defaultTokenUtil;
    private final DefaultUserDetailsService defaultUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public TokenResponse createAuthenticationToken(@RequestBody UserCredentials userCredentials) {
        if (Objects.isNull(userCredentials)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
        }

        UserDetails userDetails = defaultUserDetailsService.loadUserByUsername(userCredentials.getUsername());
        if (Objects.isNull(userDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
        }

        String entryPassword = userCredentials.getPassword();
        String dbPassword = userDetails.getPassword();

        if (passwordEncoder.matches(entryPassword, dbPassword)) {
            final String token = defaultTokenUtil.generateToken(userDetails);
            return new TokenResponse(token, defaultTokenUtil.getPrefix(), defaultTokenUtil.getExpiration());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }
}
