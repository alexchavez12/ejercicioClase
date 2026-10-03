package com.punto.venta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.punto.venta.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByEstadoTrueOrderByIdCategoriaDesc();

    List<Categoria>findByEstadoTrue();

    List<Categoria>findByEstadoTrueAndNombreContainingIgnoreCase(String nombre);
    
    List<Categoria>findTop2ByEstadoTrueAndNombreContainingIgnoreCase(String nombre);
    
    boolean existsByNombreIgnoreCase(String nombre);
}
