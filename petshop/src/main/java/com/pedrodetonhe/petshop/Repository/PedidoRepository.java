package com.pedrodetonhe.petshop.Repository;

import com.pedrodetonhe.petshop.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    Optional<Pedido> findByIdUsuarioFkAndStatus(Integer idUsuarioFk, String status);
}
