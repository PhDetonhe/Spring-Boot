package com.pedrodetonhe.petshop.DTO;

import com.pedrodetonhe.petshop.Model.Usuario;

// DTO para requisição de cadastro
public class RegisterRequest {
    private String nome;
    private String email;
    private String senha;
    private Usuario.Role role; // opcional, default USER

    public RegisterRequest() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Usuario.Role getRole() { return role; }
    public void setRole(Usuario.Role role) { this.role = role; }
}
