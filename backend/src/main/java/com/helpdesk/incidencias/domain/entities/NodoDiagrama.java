package com.helpdesk.incidencias.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "nodos_diagrama")
public class NodoDiagrama {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título del nodo es obligatorio")
    @Size(max = 100, message = "El título no puede exceder 100 caracteres")
    @Column(name = "titulo", nullable = false)
    private String titulo;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "tipo_nodo", length = 50)
    private String tipoNodo;
    
    @Column(name = "posicion_x")
    private Double posicionX;
    
    @Column(name = "posicion_y")
    private Double posicionY;
    
    @Column(name = "ancho")
    private Double ancho;
    
    @Column(name = "alto")
    private Double alto;
    
    @Column(name = "color", length = 7)
    private String color;
    
    @Column(name = "orden")
    private Integer orden;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagrama_flujo_id", nullable = false)
    private DiagramaFlujo diagramaFlujo;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "es_activo", nullable = false)
    private Boolean esActivo = true;
    
    // Constructor por defecto
    public NodoDiagrama() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Constructor con campos básicos
    public NodoDiagrama(String titulo, DiagramaFlujo diagramaFlujo) {
        this();
        this.titulo = titulo;
        this.diagramaFlujo = diagramaFlujo;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public String getTipoNodo() {
        return tipoNodo;
    }
    
    public void setTipoNodo(String tipoNodo) {
        this.tipoNodo = tipoNodo;
    }
    
    public Double getPosicionX() {
        return posicionX;
    }
    
    public void setPosicionX(Double posicionX) {
        this.posicionX = posicionX;
    }
    
    public Double getPosicionY() {
        return posicionY;
    }
    
    public void setPosicionY(Double posicionY) {
        this.posicionY = posicionY;
    }
    
    public Double getAncho() {
        return ancho;
    }
    
    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }
    
    public Double getAlto() {
        return alto;
    }
    
    public void setAlto(Double alto) {
        this.alto = alto;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public Integer getOrden() {
        return orden;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    
    public DiagramaFlujo getDiagramaFlujo() {
        return diagramaFlujo;
    }
    
    public void setDiagramaFlujo(DiagramaFlujo diagramaFlujo) {
        this.diagramaFlujo = diagramaFlujo;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    public Boolean getEsActivo() {
        return esActivo;
    }
    
    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }
    
    // Métodos de utilidad
    public void actualizarFechaModificacion() {
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "NodoDiagrama{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", tipoNodo='" + tipoNodo + '\'' +
                ", diagramaFlujo=" + (diagramaFlujo != null ? diagramaFlujo.getId() : null) +
                ", esActivo=" + esActivo +
                '}';
    }
} 