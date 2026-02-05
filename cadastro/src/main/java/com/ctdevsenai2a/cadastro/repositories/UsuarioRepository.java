package com.ctdevsenai2a.cadastro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctdevsenai2a.cadastro.entitys.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {
Usuario findByEmail(String email);
}
