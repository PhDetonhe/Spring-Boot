package com.pedrodetonhe.petshop.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrodetonhe.petshop.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);

}
