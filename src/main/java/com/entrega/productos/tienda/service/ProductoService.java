package com.entrega.productos.tienda.service;

import com.entrega.productos.tienda.exception.ProductosDuplicadoException;
import com.entrega.productos.tienda.exception.ProductosNoEncontradoException;
import com.entrega.productos.tienda.exception.StockInsuficienteException;
import com.entrega.productos.tienda.model.Producto;
import com.entrega.productos.tienda.repository.ProductoRepository;
import com.entrega.productos.tienda.util.Validador;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Producto agregarProducto(Producto producto) {
        Validador.validarNombre(producto.getNombre());
        Validador.validarPrecio(producto.getPrecio());
        Validador.validarStock(producto.getStock());
        Validador.validarCategoria(producto.getCategoria());

        if (productoRepository.existsByNombreIgnoreCase(producto.getNombre())) {
            throw new ProductosDuplicadoException("Error: ya existe un producto con el nombre: " + producto.getNombre());
        }

        producto.setId(null);
        return productoRepository.save(producto);
    }

    public Producto buscarProducto(int id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductosNoEncontradoException("Error: no existe un producto con el ID: " + id));
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Page<Producto> buscarConFiltros(
            String nombre, String categoria, Double precioMin, Double precioMax, Pageable pageable) {
        if (precioMin != null && precioMax != null && precioMin > precioMax) {
            throw new IllegalArgumentException("El precio mínimo no puede ser mayor que el precio máximo.");
        }

        String nombreFiltro = (nombre != null) ? nombre.trim() : "";
        String categoriaFiltro = (categoria != null) ? categoria.trim() : "";
        double min = (precioMin != null) ? precioMin : 0.0;
        double max = (precioMax != null) ? precioMax : Double.MAX_VALUE;

        return productoRepository.buscarConFiltros(nombreFiltro, categoriaFiltro, min, max, pageable);
    }

    @Transactional
    public void reducirStock(int id, int cantidadComprada) {
        Producto producto = buscarProducto(id);

        if (cantidadComprada <= 0) {
            throw new IllegalArgumentException("La cantidad a comprar debe ser mayor que cero.");
        }

        if (producto.getStock() < cantidadComprada) {
            throw new StockInsuficienteException("Error: stock insuficiente para el producto " + producto.getNombre() +
                    ". Stock disponible: " + producto.getStock() +
                    ", cantidad solicitada: " + cantidadComprada);
        }

        producto.setStock(producto.getStock() - cantidadComprada);
        productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizarProducto(int id, Producto productoActualizado) {
        Producto producto = buscarProducto(id);

        if (!producto.getNombre().equalsIgnoreCase(productoActualizado.getNombre())
                && productoRepository.existsByNombreIgnoreCase(productoActualizado.getNombre())) {
            throw new ProductosDuplicadoException("Error: ya existe un producto con el nombre: " + productoActualizado.getNombre());
        }

        producto.setNombre(productoActualizado.getNombre());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        producto.setCategoria(productoActualizado.getCategoria());

        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminarProducto(int id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductosNoEncontradoException("Error: no se puede eliminar. No existe un producto con el ID: " + id);
        }
        productoRepository.deleteById(id);
    }

}