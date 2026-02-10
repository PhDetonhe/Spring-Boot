package com.senaidev2a.cadastrotunado.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senaidev2a.cadastrotunado.Entitys.Usuario;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
 Usuario findByEmail(String email);
}
