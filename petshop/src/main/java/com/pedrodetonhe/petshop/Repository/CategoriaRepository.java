package com.pedrodetonhe.petshop.Repository;

import com.pedrodetonhe.petshop.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
