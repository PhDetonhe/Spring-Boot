package com.pedrodetonhe.petshop.Repository;

import com.pedrodetonhe.petshop.Model.ItensPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItensPedidoRepository extends JpaRepository<ItensPedido, Integer> {
    Optional<ItensPedido> findByIdPedidoFkAndIdProdutoFk(Integer idPedidoFk, Integer idProdutoFk);

    List<ItensPedido> findByIdPedidoFk(Integer idPedidoFk);
}
