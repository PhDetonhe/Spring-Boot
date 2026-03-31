package com.pedrodetonhe.petshop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/categorias/**").permitAll()
                .anyRequest().authenticated()
    }
);
return http.build();
    // Bean do BCryptPasswordEncoder para criptografar e validar senhas
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

