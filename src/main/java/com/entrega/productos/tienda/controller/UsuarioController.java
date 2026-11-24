package com.entrega.productos.tienda.controller;

import com.entrega.productos.tienda.model.Pedido;
import com.entrega.productos.tienda.model.Usuario;
import com.entrega.productos.tienda.repository.PedidoRepository;
import com.entrega.productos.tienda.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    public UsuarioController(UsuarioRepository usuarioRepository, PedidoRepository pedidoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario) {
        if (usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        usuario.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
    }

    @GetMapping("/{id}/pedidos")
    public ResponseEntity<List<Pedido>> obtenerHistorialPedidos(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe un usuario con el ID: " + id);
        }
        return ResponseEntity.ok(pedidoRepository.findByUsuarioId(id));
    }
}