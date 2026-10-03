package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<PedidoDTO> getAllPedidos() {
        return pedidoService.findAll();
    }

    @GetMapping("/mostrarActivos")
    public List<PedidoDTO> mostrarActivos() {
        return pedidoService.mostrarActivos();
    }

    @GetMapping("/mostrarActivosFiltro")
    public List<PedidoDTO> mostrarActivosFiltro(
            @RequestParam Integer idPedido) {

        return pedidoService.mostrarActivosFiltro(idPedido);
    }

    @GetMapping("/mostrarActivosFiltroTop")
    public List<PedidoDTO> mostrarActivosFiltroTop2() {

        return pedidoService.mostrarActivosFiltroTop2();
    }

    @PostMapping
    public ResponseEntity<MessageResponse> crearPedido(
            @RequestBody PedidoDTO pedidoDTO) {

        pedidoService.crearPedido(pedidoDTO);

        return ResponseEntity.ok(new MessageResponse("Pedido creado con éxito"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> modificarPedido(
            @PathVariable Integer id,
            @RequestBody PedidoDTO pedidoDTO) {

        return ResponseEntity.ok(pedidoService.modificarPedido(id, pedidoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminarPedido(
            @PathVariable Integer id) {
        pedidoService.eliminarPedido(id);

        return ResponseEntity.ok(new MessageResponse("Pedido eliminado con éxito"));
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<PedidoDTO> anularPedido(
            @PathVariable Integer id) {

        return ResponseEntity.ok(pedidoService.anularPedido(id));
    }
}