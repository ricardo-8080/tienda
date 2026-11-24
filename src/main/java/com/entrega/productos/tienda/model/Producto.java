package com.entrega.productos.tienda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, unique = true)
    private String nombre;

    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private double precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private int stock;


    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    @Column(length = 500)
    private String descripcion;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    @Override
    public String toString() {
        return "ID: " + id +
                " | " + nombre +
                " | Descripción: " + descripcion +
                " | Imagen: " + imagenUrl +
                " | $" + precio +
                " | Stock: " + stock +
                " | Categoría: " + (categoria != null ? categoria.getNombre() : "Sin categoría");
    }
}