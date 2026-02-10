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

    public Usuario atualizar(Long id, Usuario dados) {
    Usuario usuario = repository.findById(id).orElse(null);

        if (usuario == null) {
        return null;
        }

            usuario.setNome(dados.getNome());
            usuario.setEmail(dados.getEmail());
            usuario.setSenha(dados.getSenha());
            usuario.setPerfil(dados.getPerfil());
            usuario.setEndereco(dados.getEndereco());
            usuario.setBairro(dados.getBairro());
            usuario.setComplemento(dados.getComplemento());
            usuario.setCep(dados.getCep());
            usuario.setCidade(dados.getCidade());
            usuario.setEstado(dados.getEstado());

        return repository.save(usuario);
        }


    }

