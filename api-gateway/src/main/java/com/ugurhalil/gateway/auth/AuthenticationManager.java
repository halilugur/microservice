package com.ugurhalil.gateway.auth;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final DefaultTokenUtil defaultTokenUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        try {
            if (StringUtils.isBlank(authToken)) {
                return Mono.empty();
            }
            String username = defaultTokenUtil.getUsernameFromToken(authToken);
            if (StringUtils.isBlank(username)) {
                return Mono.empty();
            }

            if (defaultTokenUtil.isTokenExpired(authToken)) {
                return Mono.empty();
            }

            List<SimpleGrantedAuthority> authorities = defaultTokenUtil.getClaimFromToken(authToken,
                    claims -> Stream.of(claims.get(defaultTokenUtil.getAuthorities()))
                            .map(o -> (List<String>) o)
                            .flatMap(Collection::stream)
                            .map(s -> "ROLE_" + s)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );

            return Mono.just(new UsernamePasswordAuthenticationToken(username, null, authorities));
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}