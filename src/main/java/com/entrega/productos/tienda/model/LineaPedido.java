package com.entrega.productos.tienda.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lineas_pedido")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonIgnore
    private Pedido pedido;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private double subtotal;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecio() * cantidad;
    }
}