package com.senaidev2a.cadastrotunado.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senaidev2a.cadastrotunado.Entitys.Usuario;
import com.senaidev2a.cadastrotunado.Services.UsuarioService;

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

   @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {

        Usuario usuarioAtualizado = service.atualizar(id, usuario);

        if (usuarioAtualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dados) {

        String email = dados.get("email");
        String senha = dados.get("senha");

        Usuario usuario = service.login(email, senha);

        if (usuario == null) {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }

        return ResponseEntity.ok(usuario);
    }

}


    


