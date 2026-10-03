package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.entity.Pedido;
import com.punto.venta.entity.PedidoDetalle;
import com.punto.venta.entity.Producto;
import com.punto.venta.repository.PedidoDetalleRepository;

@Service
public class PedidoDetalleService {

    private final PedidoDetalleRepository pedidoDetalleRepository;

    PedidoDetalleService(PedidoDetalleRepository pedidoDetalleRepository) {
        this.pedidoDetalleRepository = pedidoDetalleRepository;
    }

    public List<PedidoDetalleDTO> findAll() {
        return pedidoDetalleRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDetalleDTO> mostrarActivos() {
        return pedidoDetalleRepository.findByEstadoTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public PedidoDetalleDTO crearPedidoDetalle(
            PedidoDetalleDTO dto) {

        return convertToDTO(pedidoDetalleRepository.save(convertToEntity(dto)));
    }

    public void eliminarPedidoDetalle(Integer id) {

        if (!pedidoDetalleRepository.existsById(id)) {
            throw new RuntimeException("El detalle no existe con id " + id);
        }

        pedidoDetalleRepository.deleteById(id);
    }
    public PedidoDetalleDTO anularPedidoDetalle(Integer id) {
    PedidoDetalle detalle = pedidoDetalleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El detalle no existe con id " + id));

    detalle.setEstado(false);

    return convertToDTO(pedidoDetalleRepository.save(detalle));
}

    public PedidoDetalleDTO modificarPedidoDetalle(
            Integer id,
            PedidoDetalleDTO dto) {

        PedidoDetalle detalle = pedidoDetalleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El detalle no existe con id " + id));
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setSubtotal(dto.getSubtotal());

        if (dto.getIdPedido() != null) {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(dto.getIdPedido());
            detalle.setPedido(pedido);
        }

        if (dto.getIdProducto() != null) {
            Producto producto = new Producto();
            producto.setIdProducto(dto.getIdProducto());
            detalle.setIdProducto(producto);
        }

        return convertToDTO(pedidoDetalleRepository.save(detalle));
    }

    private PedidoDetalleDTO convertToDTO(PedidoDetalle d) {
        PedidoDetalleDTO dto = new PedidoDetalleDTO();
        dto.setIdPedidoDetalle(d.getIdPedidoDetalle());
        dto.setCantidad(d.getCantidad());
        dto.setPrecioUnitario(d.getPrecioUnitario());
        dto.setSubtotal(d.getSubtotal());

        if (d.getPedido() != null) {
            dto.setIdPedido(d.getPedido().getIdPedido());
        }

        if (d.getIdProducto() != null) {
            dto.setIdProducto(d.getIdProducto().getIdProducto());
        }

        return dto;
    }

    private PedidoDetalle convertToEntity(
            PedidoDetalleDTO dto) {

        PedidoDetalle detalle = new PedidoDetalle();

        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setSubtotal(dto.getSubtotal());

        if (dto.getIdPedido() != null) {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(dto.getIdPedido());
            detalle.setPedido(pedido);
        }

        if (dto.getIdProducto() != null) {
            Producto producto = new Producto();
            producto.setIdProducto(dto.getIdProducto());
            detalle.setIdProducto(producto);
        }

        return detalle;
    }
}