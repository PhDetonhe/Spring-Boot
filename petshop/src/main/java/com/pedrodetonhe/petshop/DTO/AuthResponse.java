package com.pedrodetonhe.petshop.DTO;

// DTO de resposta após login/cadastro
public class AuthResponse {
    private Integer id;
    private String token;
    private String nome;
    private String email;
    private String role;

    public AuthResponse(Integer id, String token, String nome, String email, String role) {
        this.id = id;
        this.token = token;
        this.nome = nome;
        this.email = email;
        this.role = role;
    }

    public Integer getId() { return id; }
    public String getToken() { return token; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
