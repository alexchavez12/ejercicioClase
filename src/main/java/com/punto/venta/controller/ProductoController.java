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
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.dto.ProductoDTO;
import com.punto.venta.service.ProductoService;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoDTO> getAllProductos() {
        return productoService.findAll();
    }

     @GetMapping("/mostrarActivos")
    public List<ProductoDTO> mostrarActivos() {
        return productoService.mostrarActivos();
    }

    @GetMapping("/mostrarActivosFiltro")
    public List<ProductoDTO> mostrarActivosFiltro(@RequestParam String nombre) {
        return productoService.mostrarActivosFiltro(nombre);
    }

    @GetMapping("/mostrarActivosFiltroTop")
    public List<ProductoDTO> mostrarActivosFiltroTop2(@RequestParam String nombre) {
        return productoService.mostrarActivosFiltroTop2(nombre);
    }


    @PostMapping
    public ResponseEntity<MessageResponse> crearProducto(
            @RequestBody ProductoDTO productoDTO) {

        try {
            productoService.crearProducto(productoDTO);
            return ResponseEntity.ok( new MessageResponse("Producto creado con éxito"));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse("Error: El producto ya existe"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> modificarProducto(
            @PathVariable Integer id,
            @RequestBody ProductoDTO productoDTO) {

        return ResponseEntity.ok(productoService.modificarProducto(id, productoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminarProducto(
            @PathVariable Integer id) {
        productoService.eliminarProducto(id);

        return ResponseEntity.ok(new MessageResponse("Producto eliminado con éxito"));
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<ProductoDTO> anularProducto(
            @PathVariable Integer id) {

        return ResponseEntity.ok(productoService.anularProducto(id));
    }
}