package com.pedrodetonhe.petshop.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private String Image;
    private double preco;
    private double preco_desconto;
    private int qtnd_estoque;
    
    
    @ManyToOne
    @JoinColumn(name = "id_categoria") // TEM QUE SER IGUAL AO BANCO
    private Categoria categoria;

    

    public Produto(Integer id, String nome, String descricao, String image, double preco, double preco_desconto, int qtnd_estoque, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        Image = image;
        this.preco = preco;
        this.preco_desconto = preco_desconto;
        this.qtnd_estoque = qtnd_estoque;
        this.categoria = categoria;
    }

    

    public Produto() {
    }



    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
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



    public String getImage() {
        return Image;
    }



    public void setImage(String image) {
        Image = image;
    }



    public double getPreco() {
        return preco;
    }



    public void setPreco(double preco) {
        this.preco = preco;
    }



    public double getPreco_desconto() {
        return preco_desconto;
    }



    public void setPreco_desconto(double preco_desconto) {
        this.preco_desconto = preco_desconto;
    }



    public int getQtnd_estoque() {
        return qtnd_estoque;
    }



    public void setQtnd_estoque(int qtnd_estoque) {
        this.qtnd_estoque = qtnd_estoque;
    }



    public Categoria getCategoria() {
        return categoria;
    }



    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    
    

    
}
