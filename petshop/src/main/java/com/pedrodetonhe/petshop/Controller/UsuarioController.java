package com.pedrodetonhe.petshop.Controller;

import com.pedrodetonhe.petshop.DTO.RegisterRequest;
import com.pedrodetonhe.petshop.Model.Usuario;
import com.pedrodetonhe.petshop.Service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET /usuarios — apenas ADMIN (protegido no SecurityConfig)
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    // GET /usuarios/{id} — autenticado
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // PUT /usuarios/{id} — autenticado (usuário edita próprios dados)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id,
                                              @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(usuarioService.atualizar(id, request));
    }

    // DELETE /usuarios/{id} — apenas ADMIN (protegido no SecurityConfig)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}