package com.entrega.productos.tienda.service;

import com.entrega.productos.tienda.dto.ItemPedidoDTO;
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
    public Pedido procesarPedido(List<ItemPedidoDTO> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un producto.");
        }

        Pedido nuevoPedido = new Pedido();

        for (ItemPedidoDTO item : items) {
            if (item.cantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
            }

            Producto producto = productoService.buscarProducto(item.productoId());
            productoService.reducirStock(producto.getId(), item.cantidad());

            LineaPedido linea = new LineaPedido(producto, item.cantidad());
            nuevoPedido.agregarLinea(linea);
        }

        return pedidoRepository.save(nuevoPedido);
    }
}