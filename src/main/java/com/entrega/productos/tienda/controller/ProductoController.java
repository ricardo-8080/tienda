package com.entrega.productos.tienda.controller;

import com.entrega.productos.tienda.model.Producto;
import com.entrega.productos.tienda.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        return ResponseEntity.ok(productoService.buscarConFiltros(nombre, categoria, precioMin, precioMax));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(productoService.buscarProducto(id));
    }

    @PostMapping
    public ResponseEntity<Producto> agregar(@RequestBody Producto producto) {
        Producto guardado = productoService.agregarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable int id, @RequestBody Producto datosNuevos) {
        Producto actualizado = productoService.actualizarProducto(id, datosNuevos.getPrecio(), datosNuevos.getStock());
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}