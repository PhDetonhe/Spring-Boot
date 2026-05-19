package com.pedrodetonhe.petshop.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // FIX: RuntimeException vira 400 Bad Request com a mensagem real
    // Sem isso, o frontend recebia 500 e nunca sabia o motivo real do erro
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // 403 para quem não tem permissão (ex: USER tentando criar ADMIN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(403).body("Apenas administradores podem fazer essa ação.");
    }
}
