package com.entrega.productos.tienda.repository;

import com.entrega.productos.tienda.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
        boolean existsByNombreIgnoreCase(String nombre);

        @Query("""
                SELECT p FROM Producto p
                WHERE (:nombre = '' OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
                AND (:categoria = '' OR LOWER(p.categoria) = LOWER(:categoria))
                AND p.precio >= :precioMin
                AND p.precio <= :precioMax
                """)
        Page<Producto> buscarConFiltros(
                @Param("nombre") String nombre,
                @Param("categoria") String categoria,
                @Param("precioMin") double precioMin,
                @Param("precioMax") double precioMax,
                Pageable pageable
        );

        List<Producto> findByStockLessThanEqual(int minimo);

}