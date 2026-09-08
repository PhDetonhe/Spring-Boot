package com.example.userauth.controller;

import com.example.userauth.dto.*;
import com.example.userauth.entity.User;
import com.example.userauth.exception.UnauthorizedException;
import com.example.userauth.service.AuthService;
import com.example.userauth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Endpoints publicos de autenticacao: cadastro, login, logout e usuario logado. */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String SESSION_USER_ID = "userId";

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterDTO dto) {
        UserResponseDTO created = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody LoginDTO dto, HttpServletRequest request) {
        User user = authService.authenticate(dto);

        // Evita fixacao de sessao: gera uma sessao nova a cada login.
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_USER_ID, user.getId());

        return ResponseEntity.ok(authService.toResponseDTO(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiMessageDTO> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(ApiMessageDTO.builder().message("Logout realizado com sucesso.").build());
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = session != null ? (Long) session.getAttribute(SESSION_USER_ID) : null;

        if (userId == null) {
            throw new UnauthorizedException("Nenhum usuario autenticado.");
        }

        return ResponseEntity.ok(userService.findById(userId));
    }
}
