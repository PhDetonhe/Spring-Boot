package com.ctdevsenai2a.cadastro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctdevsenai2a.cadastro.entitys.Usuario;
import com.ctdevsenai2a.cadastro.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> listarTodos(){
        return repository.findAll();
    }

    public Usuario cadastrar(Usuario usuario){
        return repository.save(usuario);
    }

    public void deletarUsuario(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }

    }
/*GGGGGG */
}
