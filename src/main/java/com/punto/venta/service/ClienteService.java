package com.punto.venta.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

public List<ClienteDTO> mostrarActivos() {
        return clienteRepository.findByEstadoTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

public List<ClienteDTO> mostrarActivosFiltro(String nombre) {
        return clienteRepository
                .findByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteDTO> mostrarActivosFiltroTop2(String nombre) {
        return clienteRepository
                .findTop2ByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO  crearCliente(ClienteDTO dto) {

        boolean duplicado =
            clienteRepository.existsByEmailIgnoreCase(dto.getEmail());

        if (duplicado) {
            throw new RuntimeException("El cliente ya existe");
        }

        return convertToDTO(clienteRepository.save(convertToEntity(dto)));
    }

    public void eliminarCliente(Integer idCliente) {

        if (!clienteRepository.existsById(idCliente)) {
            throw new RuntimeException("El cliente no existe con id " + idCliente);
        }

        clienteRepository.deleteById(idCliente);
    }

    public ClienteDTO anularCliente(Integer idCliente) {

        Cliente cliente = clienteRepository.findById(idCliente)
            .orElseThrow(() -> new RuntimeException("El cliente no existe con id " + idCliente));

        cliente.setEstado(false);

        return convertToDTO(clienteRepository.save(cliente));
    }

    public ClienteDTO modificarCliente(
            Integer idCliente,
            ClienteDTO clienteDTO) {

        Cliente cliente = clienteRepository.findById(idCliente)
            .orElseThrow(() -> new RuntimeException("El cliente no existe con id " + idCliente));

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());

        return convertToDTO(clienteRepository.save(cliente));
    }

    private ClienteDTO convertToDTO(Cliente c) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(c.getIdCliente());
        dto.setEstado(c.getEstado());
        dto.setNombre(c.getNombre());
        dto.setApellido(c.getApellido());
        dto.setEmail(c.getEmail());
        dto.setTelefono(c.getTelefono());
        dto.setFechaRegistro(c.getFechaRegistro());

        return dto;
    }

    private Cliente convertToEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setEstado(true);
        cliente.setFechaRegistro(new Date());

        return cliente;
    }
}