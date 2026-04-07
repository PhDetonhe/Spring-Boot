package com.pedrodetonhe.petshop.Service;

import com.pedrodetonhe.petshop.Model.Categoria;
import com.pedrodetonhe.petshop.Model.Produto;
import com.pedrodetonhe.petshop.Repository.CategoriaRepository;
import com.pedrodetonhe.petshop.Repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // LISTAR TODOS
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // BUSCAR POR ID
    public Optional<Produto> buscarPorId(Integer id) {
        return produtoRepository.findById(id);
    }

    // SALVAR PRODUTO
    public Produto salvar(Produto produto, Integer idCategoria) {

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    // ATUALIZAR
    public Produto atualizar(Integer id, Produto produtoAtualizado, Integer idCategoria) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setPreco_desconto(produtoAtualizado.getPreco_desconto());
        produto.setImagem(produtoAtualizado.getImagem());
        produto.setQtd_estoque(produtoAtualizado.getQtd_estoque());
        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    // DELETAR
    public void deletar(Integer id) {
        produtoRepository.deleteById(id);
    }
}