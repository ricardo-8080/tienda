package com.entrega.productos.tienda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String categoria;

    @Override
    public String toString() {
        return "ID: " + id +
                " | " + nombre +
                " | $" + precio +
                " | Stock: " + stock +
                " | Categoría: " + categoria;
    }
}