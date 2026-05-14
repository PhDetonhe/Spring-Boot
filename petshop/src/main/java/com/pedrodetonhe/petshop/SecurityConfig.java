package com.pedrodetonhe.petshop;

import com.pedrodetonhe.petshop.Security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // ========== ROTAS PÚBLICAS ==========
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/**").permitAll()                    // Login e cadastro

                // ========== CATEGORIAS ==========
                .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()            // Todos veem
                .requestMatchers(HttpMethod.POST, "/categorias/**").hasRole("ADMIN")      // Só ADMIN cria
                .requestMatchers(HttpMethod.PUT, "/categorias/**").hasRole("ADMIN")       // Só ADMIN edita
                .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasRole("ADMIN")    // Só ADMIN deleta

                // ========== PRODUTOS ==========
                .requestMatchers(HttpMethod.GET, "/produtos/**").permitAll()              // Todos veem
                .requestMatchers(HttpMethod.POST, "/produtos/**").hasRole("ADMIN")        // Só ADMIN cria
                .requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("ADMIN")         // Só ADMIN edita
                .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN")      // Só ADMIN deleta

                // ========== USUÁRIOS ==========
                .requestMatchers(HttpMethod.GET, "/usuarios/**").hasRole("ADMIN")         // Só ADMIN lista todos
                .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN")      // Só ADMIN deleta
                .requestMatchers(HttpMethod.PUT, "/usuarios/**").authenticated()          // Autenticado edita

                // Qualquer outra rota exige autenticação
                .anyRequest().authenticated()
            )
            // Adiciona o filtro JWT antes do filtro padrão do Spring
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}