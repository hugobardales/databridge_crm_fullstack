package com.databridge_crm_backend.exception; // Ajusta el paquete si es necesario

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Esta función atrapa la excepción que lanzó tu AuthService
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage()); // "Email Already Use"

        // Devolvemos un 409 Conflict (que es lo que espera tu test)
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}