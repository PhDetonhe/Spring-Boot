package com.pedrodetonhe.petshop.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")
public class ItensPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_pedido")
    private Integer idItemPedido;

    @Column(name = "id_pedido_fk", nullable = false)
    private Integer idPedidoFk;

    @Column(name = "id_produto_fk", nullable = false)
    private Integer idProdutoFk;

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "imagem_produto", columnDefinition = "LONGTEXT")
    private String imagemProduto;

    private Integer quantidade;
    private BigDecimal precoUnitario;

    public Integer getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Integer idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public Integer getIdPedidoFk() {
        return idPedidoFk;
    }

    public void setIdPedidoFk(Integer idPedidoFk) {
        this.idPedidoFk = idPedidoFk;
    }

    public Integer getIdProdutoFk() {
        return idProdutoFk;
    }

    public void setIdProdutoFk(Integer idProdutoFk) {
        this.idProdutoFk = idProdutoFk;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getImagemProduto() {
        return imagemProduto;
    }

    public void setImagemProduto(String imagemProduto) {
        this.imagemProduto = imagemProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
