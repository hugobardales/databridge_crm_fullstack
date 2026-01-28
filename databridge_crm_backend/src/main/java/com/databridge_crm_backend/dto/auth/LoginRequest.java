package com.databridge_crm_backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Lo que el usuario envia para registrarse
public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password) {
}
