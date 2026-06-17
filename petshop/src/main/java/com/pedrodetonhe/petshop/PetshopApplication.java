package com.pedrodetonhe.petshop;

import com.pedrodetonhe.petshop.Model.Usuario;
import com.pedrodetonhe.petshop.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PetshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetshopApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UsuarioRepository repository,
                           PasswordEncoder passwordEncoder) {
        return args -> {

            if (!repository.existsByEmail("admin@admin.com")) {

                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEmail("admin@admin.com");
                admin.setSenha(passwordEncoder.encode("123456"));
                admin.setRole(Usuario.Role.ADMIN);

                repository.save(admin);

                System.out.println("ADMIN CRIADO!");
            }

        };
    }
}