package com.helpdesk.incidencias.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para peticiones de autenticación
 */
public class AuthRequest {
    
    @NotBlank(message = "El identificador es obligatorio")
    @Size(min = 3, max = 50, message = "El identificador debe tener entre 3 y 50 caracteres")
    private String identificador; // Puede ser email o empleadoId
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
    // Constructor por defecto
    public AuthRequest() {}
    
    // Constructor con parámetros
    public AuthRequest(String identificador, String password) {
        this.identificador = identificador;
        this.password = password;
    }
    
    // Getters y Setters
    public String getIdentificador() {
        return identificador;
    }
    
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
} 