package com.adilzhan.workload.config;

import com.adilzhan.workload.service.security.ServiceJwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final ServiceJwtAuthFilter serviceJwtAuthFilter;

    public SecurityConfig(ServiceJwtAuthFilter serviceJwtAuthFilter) {
        this.serviceJwtAuthFilter = serviceJwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/v1/workload/**").authenticated()
                        .anyRequest().permitAll()
                );
        http.addFilterBefore(serviceJwtAuthFilter, AnonymousAuthenticationFilter.class);
        return http.build();
    }
}
