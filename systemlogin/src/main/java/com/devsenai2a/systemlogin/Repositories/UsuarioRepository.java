package com.devsenai2a.systemlogin.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsenai2a.systemlogin.Entitys.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
 Usuario findByEmail(String email);
}
