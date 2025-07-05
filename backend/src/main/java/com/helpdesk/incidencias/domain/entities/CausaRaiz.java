package com.helpdesk.incidencias.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "causas_raiz")
public class CausaRaiz {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "La descripción de la causa raíz es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;
    
    @Size(max = 1000, message = "La solución propuesta no puede exceder 1000 caracteres")
    @Column(name = "solucion_propuesta", columnDefinition = "TEXT")
    private String solucionPropuesta;
    
    @Column(name = "prioridad")
    private Integer prioridad;
    
    @Column(name = "probabilidad")
    private Integer probabilidad;
    
    @Column(name = "impacto")
    private Integer impacto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analisis_causa_id", nullable = false)
    private AnalisisCausa analisisCausa;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_causa_id")
    private CategoriaCausa categoriaCausa;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "es_activo", nullable = false)
    private Boolean esActivo = true;
    
    // Constructor por defecto
    public CausaRaiz() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Constructor con campos básicos
    public CausaRaiz(String descripcion, AnalisisCausa analisisCausa) {
        this();
        this.descripcion = descripcion;
        this.analisisCausa = analisisCausa;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getSolucionPropuesta() {
        return solucionPropuesta;
    }
    
    public void setSolucionPropuesta(String solucionPropuesta) {
        this.solucionPropuesta = solucionPropuesta;
    }
    
    public Integer getPrioridad() {
        return prioridad;
    }
    
    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }
    
    public Integer getProbabilidad() {
        return probabilidad;
    }
    
    public void setProbabilidad(Integer probabilidad) {
        this.probabilidad = probabilidad;
    }
    
    public Integer getImpacto() {
        return impacto;
    }
    
    public void setImpacto(Integer impacto) {
        this.impacto = impacto;
    }
    
    public AnalisisCausa getAnalisisCausa() {
        return analisisCausa;
    }
    
    public void setAnalisisCausa(AnalisisCausa analisisCausa) {
        this.analisisCausa = analisisCausa;
    }
    
    public CategoriaCausa getCategoriaCausa() {
        return categoriaCausa;
    }
    
    public void setCategoriaCausa(CategoriaCausa categoriaCausa) {
        this.categoriaCausa = categoriaCausa;
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
    
    public int getRiesgo() {
        if (probabilidad != null && impacto != null) {
            return probabilidad * impacto;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "CausaRaiz{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", analisisCausa=" + (analisisCausa != null ? analisisCausa.getId() : null) +
                ", categoriaCausa=" + (categoriaCausa != null ? categoriaCausa.getId() : null) +
                ", esActivo=" + esActivo +
                '}';
    }
} 