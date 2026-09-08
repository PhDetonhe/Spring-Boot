package com.example.userauth.exception;

/** Lancada quando um recurso (ex.: usuario) nao e encontrado. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
