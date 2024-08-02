package com.example.product_catalog_service.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())// Disable CSRF protection for testing
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("product-catalog/nxt/new").authenticated() // Protect your endpoint
                                .anyRequest().permitAll()
                );
        return http.build();
    }
}
