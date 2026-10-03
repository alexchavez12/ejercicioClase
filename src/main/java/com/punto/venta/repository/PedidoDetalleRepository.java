package com.punto.venta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.punto.venta.entity.PedidoDetalle;

public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Integer> {

    List<PedidoDetalle> findByEstadoTrue();

    List<PedidoDetalle> findByEstadoTrueOrderByIdPedidoDetalleDesc();
}