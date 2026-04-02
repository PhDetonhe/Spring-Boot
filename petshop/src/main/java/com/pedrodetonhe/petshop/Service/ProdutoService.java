package com.pedrodetonhe.petshop.Service;

import java.util.List;

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

//função de listar todos os produtos da categoria, evidenciado pelo list, o nome da função do getAll
    public List<Produto> getAll(){
        return produtoRepository.findAll();
    }

//função de adicionar um porduto na categoria, o nome da função d afunção é adicionarProduto, é necessario o id da categoria, como se trata de uma relação 1:N significa que a tabela produto é dependente da de categoria
    public Produto adicionarProduto(Integer id_categoria, Produto produto) {

        Categoria categoria = categoriaRepository.findById(id_categoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }
//função de deletar um prodto de acordo com o seu id, nome da função é deleteProduto
    public void deleteProduto(Integer id){
        produtoRepository.deleteById(id);     

    }
//função de dar update em um produto, identificamos seu id assim podemos acessasr suas caracteristicas(get) e mudar elas(set) e se não acha por id retorna que o produto não foi achado, o mesmo pra outros runtimeExeptions desse service especifico
    public Produto updateProduto(Integer id, Produto produto){

        Produto produtoExistente = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setImage(produto.getImage());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setPreco_desconto(produto.getPreco_desconto());
        produtoExistente.setQtnd_estoque(produto.getQtnd_estoque());
        
        return produtoRepository.save(produtoExistente);
        
    }
//OUTRA função de listar, porem utilizando seu id, é necesario para que as informações que queremos que apareça no update de fato apareçam.
    public Produto ListId(Integer id){
        return produtoRepository.findById(id).orElseThrow(()-> new RuntimeException("produto não encontrada!"));
    }

   

}
