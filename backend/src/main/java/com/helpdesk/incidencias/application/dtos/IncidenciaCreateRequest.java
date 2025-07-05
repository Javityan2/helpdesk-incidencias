package com.helpdesk.incidencias.application.dtos;

import com.helpdesk.incidencias.domain.entities.Prioridad;
import com.helpdesk.incidencias.domain.entities.CategoriaIncidencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para solicitudes de creación de incidencias
 */
public class IncidenciaCreateRequest {
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    private String descripcion;
    
    @NotNull(message = "La prioridad es obligatoria")
    private Prioridad prioridad;
    
    private CategoriaIncidencia categoria;
    
    private Integer tiempoEstimadoHoras;
    
    private String comentariosInternos;
    
    // Constructor por defecto
    public IncidenciaCreateRequest() {}
    
    // Constructor con campos básicos
    public IncidenciaCreateRequest(String titulo, String descripcion, Prioridad prioridad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }
    
    // Constructor completo
    public IncidenciaCreateRequest(String titulo, String descripcion, Prioridad prioridad, 
                                 CategoriaIncidencia categoria, Integer tiempoEstimadoHoras, 
                                 String comentariosInternos) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.categoria = categoria;
        this.tiempoEstimadoHoras = tiempoEstimadoHoras;
        this.comentariosInternos = comentariosInternos;
    }
    
    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Prioridad getPrioridad() {
        return prioridad;
    }
    
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }
    
    public CategoriaIncidencia getCategoria() {
        return categoria;
    }
    
    public void setCategoria(CategoriaIncidencia categoria) {
        this.categoria = categoria;
    }
    
    public Integer getTiempoEstimadoHoras() {
        return tiempoEstimadoHoras;
    }
    
    public void setTiempoEstimadoHoras(Integer tiempoEstimadoHoras) {
        this.tiempoEstimadoHoras = tiempoEstimadoHoras;
    }
    
    public String getComentariosInternos() {
        return comentariosInternos;
    }
    
    public void setComentariosInternos(String comentariosInternos) {
        this.comentariosInternos = comentariosInternos;
    }
    
    @Override
    public String toString() {
        return "IncidenciaCreateRequest{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", prioridad=" + prioridad +
                ", categoria=" + categoria +
                ", tiempoEstimadoHoras=" + tiempoEstimadoHoras +
                '}';
    }
} 