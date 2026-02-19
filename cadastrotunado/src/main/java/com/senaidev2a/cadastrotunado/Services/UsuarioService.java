package com.senaidev2a.cadastrotunado.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senaidev2a.cadastrotunado.Entitys.Usuario;
import com.senaidev2a.cadastrotunado.Repositories.UsuarioRepository;

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

        
        if (dados.getNome() != null){
            usuario.setNome(dados.getNome());
        }

        if (dados.getEmail() != null){
            usuario.setEmail(dados.getEmail());
        }

         if (dados.getSenha() != null){
            usuario.setSenha(dados.getSenha());
        }

         if (dados.getPerfil() != null){
            usuario.setPerfil(dados.getPerfil());
        }
         
         if (dados.getCidade() != null){
            usuario.setCidade(dados.getCidade());
        }
        
        return repository.save(usuario);
    }
}
