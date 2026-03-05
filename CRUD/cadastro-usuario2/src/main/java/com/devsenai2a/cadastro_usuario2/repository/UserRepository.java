package com.devsenai2a.cadastro_usuario2.repository;

import com.devsenai2a.cadastro_usuario2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
