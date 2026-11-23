package com.entrega.productos.tienda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String timestamp, int status, String error, String mensaje) {}

    @ExceptionHandler(ProductosNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse manejarNoEncontrado(ProductosNoEncontradoException ex) {
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ProductosDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse manejarDuplicado(ProductosDuplicadoException ex) {
        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler({StockInsuficienteException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse manejarSolicitudInvalida(RuntimeException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ErrorResponse construirRespuesta(HttpStatus status, String mensaje) {
        return new ErrorResponse(
                LocalDateTime.now().toString(),
                status.value(),
                status.getReasonPhrase(),
                mensaje
        );
    }
}