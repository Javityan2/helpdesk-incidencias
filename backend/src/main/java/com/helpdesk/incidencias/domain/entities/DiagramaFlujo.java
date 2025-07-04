package com.helpdesk.incidencias.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un diagrama de flujo para la resolución de incidencias
 */
@Entity
@Table(name = "diagramas_flujo")
public class DiagramaFlujo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título del diagrama es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    @Column(name = "titulo", nullable = false)
    private String titulo;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incidencia_id", nullable = false)
    private Incidencia incidencia;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_creador_id", nullable = false)
    private Usuario usuarioCreador;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "version")
    private Integer version = 1;
    
    @Column(name = "es_activo", nullable = false)
    private Boolean esActivo = true;
    
    @Column(name = "es_publico", nullable = false)
    private Boolean esPublico = true;
    
    // Datos del diagrama en formato JSON
    @Column(name = "datos_diagrama", columnDefinition = "TEXT")
    private String datosDiagrama;
    
    // Configuración del diagrama
    @Column(name = "configuracion", columnDefinition = "TEXT")
    private String configuracion;
    
    @OneToMany(mappedBy = "diagramaFlujo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NodoDiagrama> nodos = new ArrayList<>();
    
    @OneToMany(mappedBy = "diagramaFlujo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConexionDiagrama> conexiones = new ArrayList<>();
    
    // Constructor por defecto
    public DiagramaFlujo() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Constructor con campos básicos
    public DiagramaFlujo(String titulo, Incidencia incidencia, Usuario usuarioCreador) {
        this();
        this.titulo = titulo;
        this.incidencia = incidencia;
        this.usuarioCreador = usuarioCreador;
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
    
    public Incidencia getIncidencia() {
        return incidencia;
    }
    
    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }
    
    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }
    
    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
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
    
    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public Boolean getEsActivo() {
        return esActivo;
    }
    
    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }
    
    public Boolean getEsPublico() {
        return esPublico;
    }
    
    public void setEsPublico(Boolean esPublico) {
        this.esPublico = esPublico;
    }
    
    public String getDatosDiagrama() {
        return datosDiagrama;
    }
    
    public void setDatosDiagrama(String datosDiagrama) {
        this.datosDiagrama = datosDiagrama;
    }
    
    public String getConfiguracion() {
        return configuracion;
    }
    
    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }
    
    public List<NodoDiagrama> getNodos() {
        return nodos;
    }
    
    public void setNodos(List<NodoDiagrama> nodos) {
        this.nodos = nodos;
    }
    
    public List<ConexionDiagrama> getConexiones() {
        return conexiones;
    }
    
    public void setConexiones(List<ConexionDiagrama> conexiones) {
        this.conexiones = conexiones;
    }
    
    // Métodos de utilidad
    public void actualizarFechaModificacion() {
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public void incrementarVersion() {
        this.version++;
    }
    
    public boolean esEditable() {
        return esActivo && esPublico;
    }
    
    public int getNumeroNodos() {
        return nodos != null ? nodos.size() : 0;
    }
    
    public int getNumeroConexiones() {
        return conexiones != null ? conexiones.size() : 0;
    }
    
    @Override
    public String toString() {
        return "DiagramaFlujo{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", incidencia=" + (incidencia != null ? incidencia.getId() : null) +
                ", usuarioCreador=" + (usuarioCreador != null ? usuarioCreador.getEmpleadoId() : null) +
                ", version=" + version +
                ", esActivo=" + esActivo +
                ", esPublico=" + esPublico +
                '}';
    }
} 