package com.pedrodetonhe.petshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedrodetonhe.petshop.Model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
