package com.helpdesk.incidencias.application.dtos;

import com.helpdesk.incidencias.domain.entities.TipoComentario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para solicitudes de creación de comentarios
 */
public class ComentarioCreateRequest {
    
    @NotBlank(message = "El contenido es obligatorio")
    @Size(max = 2000, message = "El contenido no puede exceder 2000 caracteres")
    private String contenido;
    
    @NotNull(message = "El tipo de comentario es obligatorio")
    private TipoComentario tipo;
    
    @NotNull(message = "El ID de la incidencia es obligatorio")
    private Long incidenciaId;
    
    // Constructor por defecto
    public ComentarioCreateRequest() {}
    
    // Constructor con campos básicos
    public ComentarioCreateRequest(String contenido, TipoComentario tipo, Long incidenciaId) {
        this.contenido = contenido;
        this.tipo = tipo;
        this.incidenciaId = incidenciaId;
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
    
    public Long getIncidenciaId() {
        return incidenciaId;
    }
    
    public void setIncidenciaId(Long incidenciaId) {
        this.incidenciaId = incidenciaId;
    }
    
    @Override
    public String toString() {
        return "ComentarioCreateRequest{" +
                "contenido='" + contenido + '\'' +
                ", tipo=" + tipo +
                ", incidenciaId=" + incidenciaId +
                '}';
    }
} 