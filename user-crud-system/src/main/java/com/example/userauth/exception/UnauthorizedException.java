package com.example.userauth.exception;

/** Lancada quando uma acao exige um usuario autenticado (sessao valida). */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
