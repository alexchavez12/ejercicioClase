package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.punto.venta.dto.ProductoDTO;
import com.punto.venta.entity.Categoria;
import com.punto.venta.entity.Producto;
import com.punto.venta.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoDTO> findAll() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> mostrarActivos() {
        return productoRepository.findByEstadoTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> mostrarActivosFiltro(String nombre) {
        return productoRepository
                .findByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> mostrarActivosFiltroTop2(String nombre) {
        return productoRepository
                .findTop2ByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO crearProducto(ProductoDTO dto) {

        boolean duplicado = productoRepository.existsByNombreIgnoreCase(dto.getNombre());

        if (duplicado) {
            throw new RuntimeException("El producto ya existe");
        }

        return convertToDTO(productoRepository.save(convertToEntity(dto)));
    }

    public void eliminarProducto(Integer idProducto) {

        if (!productoRepository.existsById(idProducto)) {
            throw new RuntimeException("El producto no existe con id " + idProducto);
        }

        productoRepository.deleteById(idProducto);
    }

    public ProductoDTO anularProducto(Integer idProducto) {

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("El producto no existe con id " + idProducto));

        producto.setEstado(false);

        Producto savedProducto = productoRepository.save(producto);

        return convertToDTO(savedProducto);
    }

    public ProductoDTO modificarProducto(
            Integer idProducto,
            ProductoDTO productoDTO) {

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("El producto no existe con id " + idProducto));

        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());

        Producto savedProducto = productoRepository.save(producto);

        return convertToDTO(savedProducto);
    }

    private ProductoDTO convertToDTO(Producto p) {

        ProductoDTO dto = new ProductoDTO();

        dto.setIdProducto(p.getIdProducto());
        dto.setEstado(p.getEstado());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setStock(p.getStock());

        if (p.getIdCategoria() != null) {
            dto.setIdCategoria(p.getIdCategoria().getIdCategoria());
        }

        return dto;
    }

    private Producto convertToEntity(ProductoDTO dto) {

        Producto producto = new Producto();

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setEstado(true);

        if (dto.getIdCategoria() != null) {
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(dto.getIdCategoria());
            producto.setIdCategoria(categoria);
        }

        return producto;
    }
}