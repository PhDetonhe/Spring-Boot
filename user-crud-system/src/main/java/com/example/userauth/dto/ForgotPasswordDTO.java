package com.example.userauth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Dados enviados na tela "esqueci minha senha". */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordDTO {

    @NotBlank(message = "O e-mail e obrigatorio")
    @Email(message = "Informe um e-mail valido")
    private String email;
}
