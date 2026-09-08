package com.example.userauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicacao.
 * <p>
 * Sistema de CRUD de usuarios com:
 * - Cadastro, listagem, edicao e exclusao de usuarios;
 * - Login com senha em hash (BCrypt);
 * - Recuperacao de senha ("esqueci minha senha") com token em hash
 *   e envio de e-mail via Resend.com;
 * - Front-end estatico (HTML + Tailwind + JavaScript/fetch) servido
 *   pelo proprio Spring Boot a partir de /src/main/resources/static.
 */
@SpringBootApplication
public class UserAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserAuthApplication.class, args);
    }

}
