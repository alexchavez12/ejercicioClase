package com.punto.venta.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.CategoriaDTO;
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.service.CategoriaService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaDTO> getAllCategorias() {
        return categoriaService.findAll();
    }

    @GetMapping("/mostrarActivos")
    public List<CategoriaDTO> mostrarActivos() {
        return categoriaService.mostrarActivos();
    }

    @GetMapping("/mostrarActivosFiltro")
    public List<CategoriaDTO> mostrarActivosFiltro(@RequestParam String nombre) {
        return categoriaService.mostrarActivosFiltro(nombre);
    }

    @GetMapping("/mostrarActivosFiltroTop")
    public List<CategoriaDTO> mostrarActivosFiltroTop2(@RequestParam String nombre) {
        return categoriaService.mostrarActivosFiltroTop2(nombre);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createCategoria(
            @RequestBody CategoriaDTO categoriaDTO) {

        try {
            categoriaService.crearCategoria(categoriaDTO);

            return ResponseEntity.ok(new MessageResponse("Categoría creada con éxito"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: La categoría ya existe"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> modificarCategoria(
            @PathVariable Integer id,
            @RequestBody CategoriaDTO categoriaDTO) {

        return ResponseEntity.ok(categoriaService.modificarCategoria(id, categoriaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminarCategoria(
            @PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);

        return ResponseEntity.ok(new MessageResponse("Categoría eliminada con éxito"));
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<CategoriaDTO> anularCategoria(
            @PathVariable Integer id) {

        return ResponseEntity.ok(categoriaService.anularCategoria(id));
    }
}