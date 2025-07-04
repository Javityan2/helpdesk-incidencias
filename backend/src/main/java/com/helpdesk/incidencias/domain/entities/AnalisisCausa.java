package com.helpdesk.incidencias.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un análisis de causa-efecto (Ishikawa) para incidencias
 */
@Entity
@Table(name = "analisis_causas")
public class AnalisisCausa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título del análisis es obligatorio")
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
    
    // Datos del análisis en formato JSON
    @Column(name = "datos_analisis", columnDefinition = "TEXT")
    private String datosAnalisis;
    
    // Configuración del diagrama
    @Column(name = "configuracion", columnDefinition = "TEXT")
    private String configuracion;
    
    @OneToMany(mappedBy = "analisisCausa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CategoriaCausa> categorias = new ArrayList<>();
    
    @OneToMany(mappedBy = "analisisCausa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CausaRaiz> causasRaiz = new ArrayList<>();
    
    // Constructor por defecto
    public AnalisisCausa() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Constructor con campos básicos
    public AnalisisCausa(String titulo, Incidencia incidencia, Usuario usuarioCreador) {
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
    
    public String getDatosAnalisis() {
        return datosAnalisis;
    }
    
    public void setDatosAnalisis(String datosAnalisis) {
        this.datosAnalisis = datosAnalisis;
    }
    
    public String getConfiguracion() {
        return configuracion;
    }
    
    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }
    
    public List<CategoriaCausa> getCategorias() {
        return categorias;
    }
    
    public void setCategorias(List<CategoriaCausa> categorias) {
        this.categorias = categorias;
    }
    
    public List<CausaRaiz> getCausasRaiz() {
        return causasRaiz;
    }
    
    public void setCausasRaiz(List<CausaRaiz> causasRaiz) {
        this.causasRaiz = causasRaiz;
    }
    
    // Métodos de utilidad
    public void actualizarFechaModificacion() {
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public void incrementarVersion() {
        this.version++;
    }
    
    public boolean esEditable() {
        return esActivo;
    }
    
    public int getNumeroCategorias() {
        return categorias != null ? categorias.size() : 0;
    }
    
    public int getNumeroCausasRaiz() {
        return causasRaiz != null ? causasRaiz.size() : 0;
    }
    
    @Override
    public String toString() {
        return "AnalisisCausa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", incidencia=" + (incidencia != null ? incidencia.getId() : null) +
                ", usuarioCreador=" + (usuarioCreador != null ? usuarioCreador.getEmpleadoId() : null) +
                ", version=" + version +
                ", esActivo=" + esActivo +
                '}';
    }
} 