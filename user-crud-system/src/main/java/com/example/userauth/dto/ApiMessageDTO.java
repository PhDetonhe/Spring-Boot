package com.example.userauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Envelope simples para respostas apenas com mensagem de sucesso. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiMessageDTO {
    private String message;
}
