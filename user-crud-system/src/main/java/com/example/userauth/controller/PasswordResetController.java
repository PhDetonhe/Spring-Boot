package com.example.userauth.controller;

import com.example.userauth.dto.ApiMessageDTO;
import com.example.userauth.dto.ForgotPasswordDTO;
import com.example.userauth.dto.ResetPasswordDTO;
import com.example.userauth.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints publicos do fluxo "esqueci minha senha". */
@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot")
    public ResponseEntity<ApiMessageDTO> forgotPassword(@Valid @RequestBody ForgotPasswordDTO dto) {
        passwordResetService.forgotPassword(dto);
        // Mensagem generica de proposito: nao revela se o e-mail existe ou nao na base.
        return ResponseEntity.ok(ApiMessageDTO.builder()
                .message("Se este e-mail estiver cadastrado, enviaremos um link de recuperacao em instantes.")
                .build());
    }

    @PostMapping("/reset")
    public ResponseEntity<ApiMessageDTO> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        passwordResetService.resetPassword(dto);
        return ResponseEntity.ok(ApiMessageDTO.builder()
                .message("Senha redefinida com sucesso. Voce ja pode fazer login com a nova senha.")
                .build());
    }
}
