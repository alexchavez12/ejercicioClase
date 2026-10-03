package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.service.PedidoDetalleService;

@RestController
@RequestMapping("/pedido-detalles")
public class PedidoDetalleController {

    private final PedidoDetalleService pedidoDetalleService;

    public PedidoDetalleController(
        PedidoDetalleService pedidoDetalleService) {
        this.pedidoDetalleService = pedidoDetalleService;
    }

    @GetMapping
    public List<PedidoDetalleDTO> getAllPedidoDetalles() {
        return pedidoDetalleService.findAll();
    }

    @GetMapping("/mostrarActivos")
    public List<PedidoDetalleDTO> mostrarActivos() {
        return pedidoDetalleService.mostrarActivos();
    }


    @PostMapping
    public ResponseEntity<MessageResponse> crearPedidoDetalle(
            @RequestBody PedidoDetalleDTO dto) {

        pedidoDetalleService.crearPedidoDetalle(dto);

        return ResponseEntity.ok(new MessageResponse("Detalle de pedido creado con éxito"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDetalleDTO> modificarPedidoDetalle(
            @PathVariable Integer id,
            @RequestBody PedidoDetalleDTO dto) {

        return ResponseEntity.ok(pedidoDetalleService.modificarPedidoDetalle(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminarPedidoDetalle(
            @PathVariable Integer id) {

        pedidoDetalleService.eliminarPedidoDetalle(id);

        return ResponseEntity.ok(new MessageResponse("Detalle de pedido eliminado con éxito"));
    }
 @PatchMapping("/{id}/anular")
    public ResponseEntity<PedidoDetalleDTO> anularPedidoDetalle(
            @PathVariable Integer id) {

        return ResponseEntity.ok(pedidoDetalleService.anularPedidoDetalle(id));
    }
}