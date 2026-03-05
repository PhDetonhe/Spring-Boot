package com.devsenai2a.cadastro_usuario2.Enums;

public enum Perfil {
    ADMIN,
    USER;

    public static Perfil fromString(String valor) {
        return Perfil.valueOf(valor.toUpperCase());
    }
}
