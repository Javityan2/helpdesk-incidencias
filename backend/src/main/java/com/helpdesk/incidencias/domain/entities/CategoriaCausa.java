package com.helpdesk.incidencias.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias_causa")
public class CategoriaCausa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "color", length = 7)
    private String color;
    
    @Column(name = "orden")
    private Integer orden;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analisis_causa_id", nullable = false)
    private AnalisisCausa analisisCausa;
    
    @OneToMany(mappedBy = "categoriaCausa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CausaRaiz> causasRaiz = new ArrayList<>();
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "es_activo", nullable = false)
    private Boolean esActivo = true;
    
    // Constructor por defecto
    public CategoriaCausa() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Constructor con campos básicos
    public CategoriaCausa(String nombre, AnalisisCausa analisisCausa) {
        this();
        this.nombre = nombre;
        this.analisisCausa = analisisCausa;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    
    public AnalisisCausa getAnalisisCausa() {
        return analisisCausa;
    }
    
    public void setAnalisisCausa(AnalisisCausa analisisCausa) {
        this.analisisCausa = analisisCausa;
    }
    
    public List<CausaRaiz> getCausasRaiz() {
        return causasRaiz;
    }
    
    public void setCausasRaiz(List<CausaRaiz> causasRaiz) {
        this.causasRaiz = causasRaiz;
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
    
    public int getNumeroCausasRaiz() {
        return causasRaiz != null ? causasRaiz.size() : 0;
    }
    
    @Override
    public String toString() {
        return "CategoriaCausa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", analisisCausa=" + (analisisCausa != null ? analisisCausa.getId() : null) +
                ", esActivo=" + esActivo +
                '}';
    }
} 