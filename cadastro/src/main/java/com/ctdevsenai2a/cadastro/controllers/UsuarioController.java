package com.ctdevsenai2a.cadastro.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctdevsenai2a.cadastro.entitys.Usuario;
import com.ctdevsenai2a.cadastro.services.UsuarioService;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario>  listaUsuarios(){
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Usuario>  cadastrarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity
         .status(HttpStatus.CREATED)
         .body(service.cadastrar(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletarUsuario(@PathVariable Long id) {
        service.deletarUsuario(id);
        return
                ResponseEntity.noContent().build();
    }
    
}

