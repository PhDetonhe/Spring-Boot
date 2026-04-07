package com.pedrodetonhe.petshop.Repository;

import com.pedrodetonhe.petshop.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}