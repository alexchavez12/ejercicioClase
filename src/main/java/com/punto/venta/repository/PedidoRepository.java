package com.punto.venta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.punto.venta.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByEstadoTrueOrderByIdPedidoDesc();

    List<Pedido> findByEstadoTrue();

    List<Pedido> findByEstadoTrueAndIdPedido(Integer idPedido);

    List<Pedido> findTop2ByEstadoTrueOrderByIdPedidoDesc();
}
