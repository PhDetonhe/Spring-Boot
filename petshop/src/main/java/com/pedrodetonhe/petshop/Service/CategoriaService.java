package com.pedrodetonhe.petshop.Service;

import com.pedrodetonhe.petshop.Model.Categoria;
import com.pedrodetonhe.petshop.Repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> getAll(){
        return repository.findAll();
    }

    public Categoria findById(Integer id_categoria){
        return repository.findById(id_categoria).orElseThrow(()-> new RuntimeException("Categoria não encontrada!"));
    }

    public Categoria save(Categoria categoria){
        return repository.save(categoria);  
    }

    public void delete(Integer id_categoria){
        repository.deleteById(id_categoria);
    }

     public Categoria update(Integer id_categoria, Categoria categoria) {

        Categoria categoriaExistente = repository.findById(id_categoria)
                .orElseThrow(() -> new RuntimeException("categoria não encontrada"));

        categoriaExistente.setNome(categoria.getNome());
        categoriaExistente.setDescricao(categoria.getDescricao());
        categoriaExistente.setAtivo(categoria.isAtivo());

        return repository.save(categoriaExistente);
    }


}
