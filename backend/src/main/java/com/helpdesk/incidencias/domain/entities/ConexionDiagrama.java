package com.helpdesk.incidencias.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "conexiones_diagrama")
public class ConexionDiagrama {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 200, message = "La etiqueta no puede exceder 200 caracteres")
    @Column(name = "etiqueta")
    private String etiqueta;
    
    @Column(name = "tipo_conexion", length = 50)
    private String tipoConexion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nodo_origen_id", nullable = false)
    private NodoDiagrama nodoOrigen;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nodo_destino_id", nullable = false)
    private NodoDiagrama nodoDestino;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagrama_flujo_id", nullable = false)
    private DiagramaFlujo diagramaFlujo;
    
    @Column(name = "color", length = 7)
    private String color;
    
    @Column(name = "grosor")
    private Integer grosor;
    
    @Column(name = "orden")
    private Integer orden;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "es_activo", nullable = false)
    private Boolean esActivo = true;
    
    // Constructor por defecto
    public ConexionDiagrama() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Constructor con campos básicos
    public ConexionDiagrama(NodoDiagrama nodoOrigen, NodoDiagrama nodoDestino, DiagramaFlujo diagramaFlujo) {
        this();
        this.nodoOrigen = nodoOrigen;
        this.nodoDestino = nodoDestino;
        this.diagramaFlujo = diagramaFlujo;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEtiqueta() {
        return etiqueta;
    }
    
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
    public String getTipoConexion() {
        return tipoConexion;
    }
    
    public void setTipoConexion(String tipoConexion) {
        this.tipoConexion = tipoConexion;
    }
    
    public NodoDiagrama getNodoOrigen() {
        return nodoOrigen;
    }
    
    public void setNodoOrigen(NodoDiagrama nodoOrigen) {
        this.nodoOrigen = nodoOrigen;
    }
    
    public NodoDiagrama getNodoDestino() {
        return nodoDestino;
    }
    
    public void setNodoDestino(NodoDiagrama nodoDestino) {
        this.nodoDestino = nodoDestino;
    }
    
    public DiagramaFlujo getDiagramaFlujo() {
        return diagramaFlujo;
    }
    
    public void setDiagramaFlujo(DiagramaFlujo diagramaFlujo) {
        this.diagramaFlujo = diagramaFlujo;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public Integer getGrosor() {
        return grosor;
    }
    
    public void setGrosor(Integer grosor) {
        this.grosor = grosor;
    }
    
    public Integer getOrden() {
        return orden;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
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
        return "ConexionDiagrama{" +
                "id=" + id +
                ", etiqueta='" + etiqueta + '\'' +
                ", tipoConexion='" + tipoConexion + '\'' +
                ", nodoOrigen=" + (nodoOrigen != null ? nodoOrigen.getId() : null) +
                ", nodoDestino=" + (nodoDestino != null ? nodoDestino.getId() : null) +
                ", diagramaFlujo=" + (diagramaFlujo != null ? diagramaFlujo.getId() : null) +
                ", esActivo=" + esActivo +
                '}';
    }
} 