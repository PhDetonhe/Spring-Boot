package com.pedrodetonhe.petshop.Service;

import org.springframework.stereotype.Service;

import com.pedrodetonhe.petshop.Model.Categoria;
import com.pedrodetonhe.petshop.Model.Produto;
import com.pedrodetonhe.petshop.Repository.CategoriaRepository;
import com.pedrodetonhe.petshop.Repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto adicionarProduto(Integer id_categoria, Produto produto) {

        Categoria categoria = categoriaRepository.findById(id_categoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }
}
