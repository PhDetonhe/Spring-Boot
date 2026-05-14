package com.pedrodetonhe.petshop.DTO;

// DTO de resposta após login/cadastro
public class AuthResponse {
    private String token;
    private String nome;
    private String email;
    private String role;

    public AuthResponse(String token, String nome, String email, String role) {
        this.token = token;
        this.nome = nome;
        this.email = email;
        this.role = role;
    }

    public String getToken() { return token; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
