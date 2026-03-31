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
        return CategoriaRepository.findAll();
    }

    public Categoria findById(Integer id){
        return CategoriaRepository.findById(id).orElseThrow(()-> new RuntimeException("Categoria não encontrada!"));
    }
}
