package com.pedrodetonhe.petshop.Controller;

import com.pedrodetonhe.petshop.DTO.AuthResponse;
import com.pedrodetonhe.petshop.DTO.LoginRequest;
import com.pedrodetonhe.petshop.DTO.RegisterRequest;
import com.pedrodetonhe.petshop.Service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST /auth/cadastro
    @PostMapping("/cadastro")
    public ResponseEntity<AuthResponse> cadastrar(@RequestBody RegisterRequest request) {
        AuthResponse response = usuarioService.cadastrar(request);
        return ResponseEntity.ok(response);
    }

    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = usuarioService.login(request);
        return ResponseEntity.ok(response);
    }
}
