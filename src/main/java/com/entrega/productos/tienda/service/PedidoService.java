package com.entrega.productos.tienda.service;

import com.entrega.productos.tienda.dto.LineaPedidoRequest;
import com.entrega.productos.tienda.model.LineaPedido;
import com.entrega.productos.tienda.model.Pedido;
import com.entrega.productos.tienda.model.Producto;
import com.entrega.productos.tienda.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoService productoService;

    public PedidoService(PedidoRepository pedidoRepository, ProductoService productoService) {
        this.pedidoRepository = pedidoRepository;
        this.productoService = productoService;
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido procesarPedido(List<LineaPedidoRequest> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un producto.");
        }

        Pedido nuevoPedido = new Pedido();

        for (LineaPedidoRequest item : items) {
            if (item.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
            }

            Producto producto = productoService.buscarProducto(item.getProductoId());
            productoService.reducirStock(producto.getId(), item.getCantidad());

            LineaPedido linea = new LineaPedido(producto, item.getCantidad());
            nuevoPedido.agregarLinea(linea);
        }

        return pedidoRepository.save(nuevoPedido);
    }
}