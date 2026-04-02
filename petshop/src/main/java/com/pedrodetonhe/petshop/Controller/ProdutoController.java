package com.pedrodetonhe.petshop.Controller;


import com.pedrodetonhe.petshop.Model.Produto;
import com.pedrodetonhe.petshop.Service.ProdutoService;

import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/categoria/{id_categoria}")
    public Produto adicionarProduto(
            @PathVariable Integer id_categoria,
            @RequestBody Produto produto) {

        return produtoService.adicionarProduto(id_categoria, produto);
    }

    @GetMapping("/{id}")//get por id pra update exibir os dados do produto
    public Produto ListId(@PathVariable Integer id) {
        return produtoService.ListId(id);
    }

    @PutMapping("/{id}")
    public Produto updateProduto(@PathVariable Integer id, @RequestBody Produto produto) {
        return produtoService.updateProduto(id, produto);
    }
    
    @DeleteMapping("/{id}")
    public void deleteProduto(@PathVariable Integer id){
        produtoService.deleteProduto(id);
    }
    

}