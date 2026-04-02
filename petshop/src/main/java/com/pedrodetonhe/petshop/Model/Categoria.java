package com.pedrodetonhe.petshop.Model;

import jakarta.persistence.*;


@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id_categoria;
    private String nome;
    private String descricao;
    private boolean ativo;

    public Categoria(Integer id_categoria, String nome, String descricao, boolean ativo) {
        this.id_categoria = id_categoria;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public Categoria() {
    }

    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    

    
}


