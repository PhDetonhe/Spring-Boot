package com.pedrodetonhe.petshop.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedrodetonhe.petshop.Model.Categoria;
import com.pedrodetonhe.petshop.Service.CategoriaService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Categoria> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id_categoria}")//Especificamente pra mudar as propriedades da categoria
    public Categoria getById(@PathVariable Integer id_categoria) {
        return service.findById(id_categoria);
    }

    @PutMapping("/{id_categoria}")
    public Categoria update(@PathVariable Integer id_categoria, @RequestBody Categoria categoria) {
        return service.update(id_categoria, categoria);
    }

    @DeleteMapping("/{id_categoria}")
    public void delete(@PathVariable Integer id_categoria) {
        service.delete(id_categoria);
    }

    @PostMapping
    public Categoria create(@RequestBody Categoria categoria){
        return service.save(categoria);
    }


    
    
}
