package com.pedrodetonhe.petshop.Service;

import com.pedrodetonhe.petshop.Model.ItensPedido;
import com.pedrodetonhe.petshop.Model.Pedido;
import com.pedrodetonhe.petshop.Repository.ItensPedidoRepository;
import com.pedrodetonhe.petshop.Repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItensPedidoService itensPedidoService;
    private final ItensPedidoRepository itensPedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ItensPedidoService itensPedidoService,
                         ItensPedidoRepository itensPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.itensPedidoService = itensPedidoService;
        this.itensPedidoRepository = itensPedidoRepository;
    }

    @Transactional
    public Pedido adicionarAoCarrinho(Integer idUsuario, Integer idProduto, Integer quantidade) {
        Pedido pedido = pedidoRepository.findByIdUsuarioFkAndStatus(idUsuario, "ABERTO")
                .orElseGet(() -> {
                    Pedido novoPedido = new Pedido();
                    novoPedido.setIdUsuarioFk(idUsuario);
                    novoPedido.setStatus("ABERTO");
                    novoPedido.setValorTotal(0.0);
                    return pedidoRepository.save(novoPedido);
                });

        itensPedidoService.adicionarOuAtualizarItem(pedido.getIdPedido(), idProduto, quantidade);
        return atualizarFinanceiro(pedido.getIdPedido());
    }

    @Transactional
    public Pedido alterarQuantidadeItem(Integer idItemPedido, Integer idPedido, Integer quantidade) {
        itensPedidoService.alterarQuantidade(idItemPedido, quantidade);
        return atualizarFinanceiro(idPedido);
    }

    @Transactional
    public Pedido removerItemDoCarrinho(Integer idItemPedido, Integer idPedido) {
        itensPedidoService.remover(idItemPedido);
        return atualizarFinanceiro(idPedido);
    }

    public Pedido atualizarFinanceiro(Integer idPedido) {
        List<ItensPedido> itens = itensPedidoService.listarPorPedido(idPedido);
        double total = itens.stream()
                .mapToDouble(item -> item.getPrecoUnitario().doubleValue() * item.getQuantidade())
                .sum();

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));
        pedido.setValorTotal(total);
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarCarrinhoAberto(Integer idUsuario) {
        return pedidoRepository.findByIdUsuarioFkAndStatus(idUsuario, "ABERTO")
                .orElseThrow(() -> new RuntimeException("Carrinho vazio ou nao encontrado para este usuario"));
    }

    @Transactional
    public Pedido finalizarPedido(Integer idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));

        if (!"ABERTO".equals(pedido.getStatus())) {
            throw new RuntimeException("Este pedido nao pode ser finalizado pois seu status e: " + pedido.getStatus());
        }

        List<ItensPedido> itens = itensPedidoService.listarPorPedido(idPedido);
        if (itens.isEmpty()) {
            throw new RuntimeException("Nao e possivel finalizar um pedido sem itens");
        }

        pedido.setStatus("FINALIZADO");
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido cancelarPedido(Integer idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));

        if ("CANCELADO".equals(pedido.getStatus())) {
            throw new RuntimeException("Este pedido ja foi cancelado");
        }

        if ("ENTREGUE".equals(pedido.getStatus())) {
            throw new RuntimeException("Pedidos entregues nao podem ser cancelados");
        }

        pedido.setStatus("CANCELADO");
        return pedidoRepository.save(pedido);
    }

    public List<ItensPedido> buscarItensPorPedido(Integer idPedido) {
        return itensPedidoRepository.findByIdPedidoFk(idPedido);
    }
}
