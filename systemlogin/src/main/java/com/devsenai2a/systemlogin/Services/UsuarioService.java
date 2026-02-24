package com.devsenai2a.systemlogin.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsenai2a.systemlogin.Entitys.Usuario;
import com.devsenai2a.systemlogin.Repositories.UsuarioRepository;

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

   
    public Usuario atualizarUsuario(Long id, Usuario dados){

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (dados.getEmail() != null){
            usuario.setEmail(dados.getEmail());
        }

         if (dados.getSenha() != null){
            usuario.setSenha(dados.getSenha());
        }
       
        return repository.save(usuario);
    }

    public Usuario login(String email, String senha){

        Usuario usuario = repository.findByEmail(email);

        if(usuario == null){
            return null;
        }

        if(usuario.getSenha().equals(senha)){
            return usuario;
        }

        return null;
    }
}