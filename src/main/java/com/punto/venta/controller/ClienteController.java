package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.service.ClienteService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteDTO> getAllClientes() {
        return clienteService.findAll();
    }

    @GetMapping("/mostrarActivos")
    public List<ClienteDTO> mostrarActivos() {
        return clienteService.mostrarActivos();
    }

    @GetMapping("/mostrarActivosFiltro")
    public List<ClienteDTO> mostrarActivosFiltro(@RequestParam String nombre) {
        return clienteService.mostrarActivosFiltro(nombre);
    }

    @GetMapping("/mostrarActivosFiltroTop")
    public List<ClienteDTO> mostrarActivosFiltroTop2(@RequestParam String nombre) {
        return clienteService.mostrarActivosFiltroTop2(nombre);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> crearCliente(
            @RequestBody ClienteDTO clienteDTO) {
        try {
            clienteService.crearCliente(clienteDTO);
            return ResponseEntity.ok(new MessageResponse("Cliente creado con éxito"));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: El cliente ya existe"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> modificarCliente(
            @PathVariable Integer id,
            @RequestBody ClienteDTO clienteDTO) {

        return ResponseEntity.ok(
                clienteService.modificarCliente(id, clienteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminarCliente(
            @PathVariable Integer id) {

        clienteService.eliminarCliente(id);

        return ResponseEntity.ok(
                new MessageResponse("Cliente eliminado con éxito"));
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<ClienteDTO> anularCliente(
            @PathVariable Integer id) {

        return ResponseEntity.ok(clienteService.anularCliente(id));
    }
}