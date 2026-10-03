package com.punto.venta.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.entity.Pedido;
import com.punto.venta.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoDTO> findAll() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> mostrarActivos() {
        return pedidoRepository.findByEstadoTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> mostrarActivosFiltro(Integer idPedido) {
        return pedidoRepository
                .findByEstadoTrueAndIdPedido(idPedido)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> mostrarActivosFiltroTop2() {
        return pedidoRepository
                .findTop2ByEstadoTrueOrderByIdPedidoDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO crearPedido(PedidoDTO dto) {

        return convertToDTO(pedidoRepository.save(convertToEntity(dto)));
    }

    public void eliminarPedido(Integer idPedido) {

        if (!pedidoRepository.existsById(idPedido)) {
            throw new RuntimeException("El pedido no existe con id " + idPedido);
        }

        pedidoRepository.deleteById(idPedido);
    }

    public PedidoDTO anularPedido(Integer idPedido) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("El pedido no existe con id " + idPedido));

        pedido.setEstado(false);

        return convertToDTO(pedidoRepository.save(pedido));
    }

    public PedidoDTO modificarPedido(
            Integer idPedido,
            PedidoDTO dto) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("El pedido no existe con id " + idPedido));

        pedido.setEstadoPedido(dto.getEstadoPedido());
        pedido.setTotal(dto.getTotal());

        if (dto.getIdCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(dto.getIdCliente());
            pedido.setIdCliente(cliente);
        }

        return convertToDTO(pedidoRepository.save(pedido));
    }

    private PedidoDTO convertToDTO(Pedido p) {

        PedidoDTO dto = new PedidoDTO();

        dto.setIdPedido(p.getIdPedido());
        dto.setEstado(p.getEstado());
        dto.setFechaPedido(p.getFechaPedido());
        dto.setEstadoPedido(p.getEstadoPedido());
        dto.setTotal(p.getTotal());

        if (p.getIdCliente() != null) {
            dto.setIdCliente(p.getIdCliente().getIdCliente());
        }

        return dto;
    }

    private Pedido convertToEntity(PedidoDTO dto) {

        Pedido pedido = new Pedido();

        pedido.setEstado(true);
        pedido.setEstadoPedido(dto.getEstadoPedido());
        pedido.setTotal(dto.getTotal());
        pedido.setFechaPedido(new Date());

        if (dto.getIdCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(dto.getIdCliente());
            pedido.setIdCliente(cliente);
        }

        return pedido;
    }
}