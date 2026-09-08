package com.example.userauth.controller;

import com.example.userauth.dto.ApiMessageDTO;
import com.example.userauth.dto.UserResponseDTO;
import com.example.userauth.dto.UserUpdateDTO;
import com.example.userauth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints de CRUD de usuarios (leitura, atualizacao e exclusao).
 * A criacao (cadastro) fica em AuthController#register.
 * Todos os endpoints aqui exigem sessao autenticada (ver SessionAuthFilter).
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessageDTO> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(ApiMessageDTO.builder().message("Usuario excluido com sucesso.").build());
    }
}
