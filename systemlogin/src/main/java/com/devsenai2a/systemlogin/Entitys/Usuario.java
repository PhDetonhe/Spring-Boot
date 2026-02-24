package com.devsenai2a.systemlogin.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
private String nome;
private String email;
private String senha;
private String perfil; // ADMIN ou USER
private String endereco;
private String bairro;
private String complemento;
private String cep;
private String cidade;
private String estado;


public Usuario(){

}


public Long getId() {
    return id;
}
public void setId(Long id) {
    this.id = id;
}
public String getNome() {
    return nome;
}
public void setNome(String nome) {
    this.nome = nome;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getSenha() {
    return senha;
}
public void setSenha(String senha) {
    this.senha = senha;
}
public String getPerfil() {
    return perfil;
}
public void setPerfil(String perfil) {
    this.perfil = perfil;
}
public String getEndereco() {
    return endereco;
}
public void setEndereco(String endereco) {
    this.endereco = endereco;
}
public String getBairro() {
    return bairro;
}
public void setBairro(String bairro) {
    this.bairro = bairro;
}
public String getComplemento() {
    return complemento;
}
public void setComplemento(String complemento) {
    this.complemento = complemento;
}
public String getCep() {
    return cep;
}
public void setCep(String cep) {
    this.cep = cep;
}
public String getCidade() {
    return cidade;
}
public void setCidade(String cidade) {
    this.cidade = cidade;
}
public String getEstado() {
    return estado;
}
public void setEstado(String estado) {
    this.estado = estado;
}
}
