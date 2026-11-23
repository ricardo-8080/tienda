package com.entrega.productos.tienda.service;

import com.entrega.productos.tienda.model.Categoria;
import com.entrega.productos.tienda.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe una categoría con el ID: " + id));
    }

    public Categoria crear(Categoria categoria) {
        if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre())) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + categoria.getNombre());
        }
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizar(Long id, Categoria categoriaActualizada) {
        Categoria existente = obtenerPorId(id);

        if (!existente.getNombre().equalsIgnoreCase(categoriaActualizada.getNombre())
                && categoriaRepository.existsByNombreIgnoreCase(categoriaActualizada.getNombre())) {
            throw new IllegalArgumentException("Ya existe otra categoría con el nombre: " + categoriaActualizada.getNombre());
        }

        existente.setNombre(categoriaActualizada.getNombre());
        return categoriaRepository.save(existente);
    }

    public void eliminar(Long id) {
        Categoria existente = obtenerPorId(id);
        categoriaRepository.delete(existente);
    }
}
