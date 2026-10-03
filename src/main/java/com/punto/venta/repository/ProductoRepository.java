package com.punto.venta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.punto.venta.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByEstadoTrueOrderByIdProductoDesc();

    List<Producto>findByEstadoTrue();

    List<Producto>findByEstadoTrueAndNombreContainingIgnoreCase(String nombre);

    List<Producto>findTop2ByEstadoTrueAndNombreContainingIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
}