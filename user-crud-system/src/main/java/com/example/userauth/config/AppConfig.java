package com.example.userauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/** Beans gerais da aplicacao. */
@Configuration
public class AppConfig {

    /** Encoder usado para gerar/validar o hash das senhas dos usuarios. */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** Cliente HTTP usado para chamar a API do Resend (envio de e-mails). */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
