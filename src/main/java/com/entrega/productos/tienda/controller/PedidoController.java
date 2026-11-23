package com.entrega.productos.tienda.controller;

import com.entrega.productos.tienda.dto.ItemPedidoDTO;
import com.entrega.productos.tienda.model.Pedido;
import com.entrega.productos.tienda.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody List<ItemPedidoDTO> items) {
        Pedido pedidoConfirmado = pedidoService.procesarPedido(items);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoConfirmado);
    }
}