package com.example.userauth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Dados recebidos para cadastro (registro) de um novo usuario. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotBlank(message = "O nome e obrigatorio")
    @Size(min = 2, max = 150, message = "O nome deve ter entre 2 e 150 caracteres")
    private String name;

    @NotBlank(message = "O e-mail e obrigatorio")
    @Email(message = "Informe um e-mail valido")
    @Size(max = 150, message = "O e-mail deve ter no maximo 150 caracteres")
    private String email;

    @NotBlank(message = "A senha e obrigatoria")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres")
    private String password;
}
