package com.helpdesk.incidencias.application.dtos;

import com.helpdesk.incidencias.domain.entities.TipoComentario;
import jakarta.validation.constraints.Size;

/**
 * DTO para solicitudes de actualización de comentarios
 */
public class ComentarioUpdateRequest {
    
    @Size(max = 2000, message = "El contenido no puede exceder 2000 caracteres")
    private String contenido;
    
    private TipoComentario tipo;
    
    // Constructor por defecto
    public ComentarioUpdateRequest() {}
    
    // Constructor con campos básicos
    public ComentarioUpdateRequest(String contenido, TipoComentario tipo) {
        this.contenido = contenido;
        this.tipo = tipo;
    }
    
    // Getters y Setters
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public TipoComentario getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoComentario tipo) {
        this.tipo = tipo;
    }
    
    // Métodos de utilidad
    public boolean tieneContenido() {
        return contenido != null && !contenido.trim().isEmpty();
    }
    
    public boolean tieneTipo() {
        return tipo != null;
    }
    
    @Override
    public String toString() {
        return "ComentarioUpdateRequest{" +
                "contenido='" + contenido + '\'' +
                ", tipo=" + tipo +
                '}';
    }
} 