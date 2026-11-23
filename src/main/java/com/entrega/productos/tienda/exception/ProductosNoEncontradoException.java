package com.entrega.productos.tienda.exception;

public class ProductosNoEncontradoException extends RuntimeException {
    public ProductosNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

