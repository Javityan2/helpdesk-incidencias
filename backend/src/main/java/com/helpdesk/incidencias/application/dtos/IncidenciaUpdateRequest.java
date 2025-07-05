package com.helpdesk.incidencias.application.dtos;

import com.helpdesk.incidencias.domain.entities.Prioridad;
import com.helpdesk.incidencias.domain.entities.CategoriaIncidencia;
import jakarta.validation.constraints.Size;

/**
 * DTO para solicitudes de actualización de incidencias
 */
public class IncidenciaUpdateRequest {
    
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;
    
    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    private String descripcion;
    
    private Prioridad prioridad;
    
    private CategoriaIncidencia categoria;
    
    private Integer tiempoEstimadoHoras;
    
    private Integer tiempoRealHoras;
    
    private String solucion;
    
    private String comentariosInternos;
    
    // Constructor por defecto
    public IncidenciaUpdateRequest() {}
    
    // Constructor con campos básicos
    public IncidenciaUpdateRequest(String titulo, String descripcion, Prioridad prioridad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }
    
    // Constructor completo
    public IncidenciaUpdateRequest(String titulo, String descripcion, Prioridad prioridad, 
                                 CategoriaIncidencia categoria, Integer tiempoEstimadoHoras, 
                                 Integer tiempoRealHoras, String solucion, String comentariosInternos) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.categoria = categoria;
        this.tiempoEstimadoHoras = tiempoEstimadoHoras;
        this.tiempoRealHoras = tiempoRealHoras;
        this.solucion = solucion;
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
    
    public Integer getTiempoRealHoras() {
        return tiempoRealHoras;
    }
    
    public void setTiempoRealHoras(Integer tiempoRealHoras) {
        this.tiempoRealHoras = tiempoRealHoras;
    }
    
    public String getSolucion() {
        return solucion;
    }
    
    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }
    
    public String getComentariosInternos() {
        return comentariosInternos;
    }
    
    public void setComentariosInternos(String comentariosInternos) {
        this.comentariosInternos = comentariosInternos;
    }
    
    // Métodos de utilidad
    public boolean tieneTitulo() {
        return titulo != null && !titulo.trim().isEmpty();
    }
    
    public boolean tieneDescripcion() {
        return descripcion != null && !descripcion.trim().isEmpty();
    }
    
    public boolean tienePrioridad() {
        return prioridad != null;
    }
    
    public boolean tieneCategoria() {
        return categoria != null;
    }
    
    public boolean tieneSolucion() {
        return solucion != null && !solucion.trim().isEmpty();
    }
    
    @Override
    public String toString() {
        return "IncidenciaUpdateRequest{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", prioridad=" + prioridad +
                ", categoria=" + categoria +
                ", tiempoEstimadoHoras=" + tiempoEstimadoHoras +
                ", tiempoRealHoras=" + tiempoRealHoras +
                ", solucion='" + solucion + '\'' +
                '}';
    }
} 