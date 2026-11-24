package com.entrega.productos.tienda.controller;

import com.entrega.productos.tienda.dto.LineaPedidoRequest;
import com.entrega.productos.tienda.model.EstadoPedido;
import com.entrega.productos.tienda.model.Pedido;
import com.entrega.productos.tienda.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

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
    public ResponseEntity<Pedido> crearPedido(
            @RequestBody @NotEmpty(message = "El pedido debe tener al menos un producto") List<@Valid LineaPedidoRequest> items) {
        Pedido pedidoConfirmado = pedidoService.procesarPedido(items);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoConfirmado);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.actualizarEstado(id, estado));
    }
}