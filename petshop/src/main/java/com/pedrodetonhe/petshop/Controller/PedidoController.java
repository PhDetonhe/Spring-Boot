package com.pedrodetonhe.petshop.Controller;

import com.pedrodetonhe.petshop.Model.ItensPedido;
import com.pedrodetonhe.petshop.Model.Pedido;
import com.pedrodetonhe.petshop.Service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin("*")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/carrinho/{usuarioId}")
    public ResponseEntity<Pedido> obterCarrinho(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(pedidoService.buscarCarrinhoAberto(usuarioId));
    }

    @GetMapping("/{idPedido}/itens")
    public ResponseEntity<List<ItensPedido>> listarItens(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(pedidoService.buscarItensPorPedido(idPedido));
    }

    @PostMapping("/carrinho/adicionar")
    public ResponseEntity<Pedido> adicionarAoCarrinho(@RequestParam Integer usuarioId,
                                                      @RequestParam Integer idProduto,
                                                      @RequestParam(defaultValue = "1") Integer quantidade) {
        return ResponseEntity.ok(pedidoService.adicionarAoCarrinho(usuarioId, idProduto, quantidade));
    }

    @PutMapping("/itens/{idItemPedido}")
    public ResponseEntity<Pedido> alterarQuantidade(@PathVariable Integer idItemPedido,
                                                    @RequestParam Integer idPedido,
                                                    @RequestParam Integer quantidade) {
        return ResponseEntity.ok(pedidoService.alterarQuantidadeItem(idItemPedido, idPedido, quantidade));
    }

    @DeleteMapping("/carrinho/remover/{idItemPedido}")
    public ResponseEntity<Pedido> removerItem(@PathVariable Integer idItemPedido,
                                              @RequestParam Integer idPedido) {
        return ResponseEntity.ok(pedidoService.removerItemDoCarrinho(idItemPedido, idPedido));
    }

    @PutMapping("/finalizar/{idPedido}")
    public ResponseEntity<String> finalizarPedido(@PathVariable Integer idPedido) {
        pedidoService.finalizarPedido(idPedido);
        return ResponseEntity.ok("Pedido finalizado com sucesso!");
    }

    @PutMapping("/cancelar/{idPedido}")
    public ResponseEntity<String> cancelarPedido(@PathVariable Integer idPedido) {
        pedidoService.cancelarPedido(idPedido);
        return ResponseEntity.ok("Pedido cancelado com sucesso!");
    }
}
