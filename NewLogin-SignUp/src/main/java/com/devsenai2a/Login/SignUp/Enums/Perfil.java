package com.devsenai2a.Login.SignUp.Enums;

public enum Perfil {
    ADMIN,
    USER;

    public static Perfil fromString(String valor) {
        return Perfil.valueOf(valor.toUpperCase());
    }
}
