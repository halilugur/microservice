package com.ugurhalil.gateway.auth;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .logout().disable()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authenticationManager(this.authenticationManager)
                .securityContextRepository(this.securityContextRepository)
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() ->
                        swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                )).accessDeniedHandler((swe, e) -> Mono.fromRunnable(() ->
                        swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))).and()
                .authorizeExchange()
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/users/**").hasRole("USER")
                .pathMatchers("/couriers/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.POST, "/deliveries/**/status").hasAnyRole("COURIER", "ADMIN")
                .pathMatchers(HttpMethod.PATCH, "/deliveries/{id}").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/deliveries/user/**").hasRole("COURIER")
                .pathMatchers(HttpMethod.GET, "/deliveries").hasAnyRole("ADMIN", "COURIER")
                .pathMatchers("/deliveries/**").hasAnyRole("USER")
                .anyExchange().authenticated()
                .and().build();
    }
}
