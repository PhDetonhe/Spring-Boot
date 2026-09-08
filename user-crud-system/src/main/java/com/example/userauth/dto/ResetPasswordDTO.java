package com.example.userauth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Dados enviados na tela de redefinicao de senha (token + nova senha). */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDTO {

    @NotBlank(message = "Token invalido")
    private String token;

    @NotBlank(message = "A nova senha e obrigatoria")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres")
    private String newPassword;
}
