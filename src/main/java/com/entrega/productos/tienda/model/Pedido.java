package com.entrega.productos.tienda.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false)
    private double total = 0.0;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaPedido> lineas = new ArrayList<>();

    public void agregarLinea(LineaPedido linea) {
        linea.setPedido(this);
        this.lineas.add(linea);
        this.total += linea.getSubtotal();
    }
}