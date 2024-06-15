package com.example.contabancaria;

import com.example.contabancaria.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(req -> req.getServletPath().equals("/auth/login")).permitAll()
                        .requestMatchers(req -> req.getServletPath().equals("/auth/registro")).permitAll()
                )
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}


