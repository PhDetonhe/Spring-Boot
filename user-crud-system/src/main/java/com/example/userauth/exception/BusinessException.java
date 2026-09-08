package com.example.userauth.exception;

/** Lancada para violacoes de regra de negocio (ex.: e-mail duplicado, credenciais invalidas). */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
