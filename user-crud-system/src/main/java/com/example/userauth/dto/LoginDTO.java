package com.example.userauth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Dados de login (autenticacao) enviados pelo front-end. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "O e-mail e obrigatorio")
    @Email(message = "Informe um e-mail valido")
    private String email;

    @NotBlank(message = "A senha e obrigatoria")
    private String password;
}
