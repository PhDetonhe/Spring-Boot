package Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository<Usuario> extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
