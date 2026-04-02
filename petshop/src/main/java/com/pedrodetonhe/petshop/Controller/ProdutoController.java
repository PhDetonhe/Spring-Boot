package com.pedrodetonhe.petshop.Controller;

import com.pedrodetonhe.petshop.Model.Produto;
import com.pedrodetonhe.petshop.Service.ProdutoService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping("/categoria/{id_categoria}")
    public Produto adicionarProduto(
            @PathVariable Integer id_categoria,
            @RequestBody Produto produto) {

        return service.adicionarProduto(id_categoria, produto);
    }
}