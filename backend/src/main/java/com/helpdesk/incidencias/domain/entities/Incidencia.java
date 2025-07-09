package com.helpdesk.incidencias.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una incidencia o ticket en el sistema
 */
@Entity
@Table(name = "incidencias")
public class Incidencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    @Column(name = "titulo", nullable = false)
    private String titulo;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false)
    private Prioridad prioridad = Prioridad.MEDIA;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoIncidencia estado = EstadoIncidencia.ABIERTA;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaIncidencia categoria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignado_id")
    private Usuario asignado;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;
    
    @Column(name = "tiempo_estimado_horas")
    private Integer tiempoEstimadoHoras;
    
    @Column(name = "tiempo_real_horas")
    private Integer tiempoRealHoras;
    
    @Column(name = "solucion", columnDefinition = "TEXT")
    private String solucion;
    
    @Column(name = "comentarios_internos", columnDefinition = "TEXT")
    private String comentariosInternos;
    
    @Column(name = "frecuencia_busqueda", nullable = false)
    private Integer frecuenciaBusqueda = 0;
    
    @OneToMany(mappedBy = "incidencia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comentario> comentarios = new ArrayList<>();
    
    @OneToMany(mappedBy = "incidencia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorialIncidencia> historial = new ArrayList<>();
    
    @OneToMany(mappedBy = "incidencia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiagramaFlujo> diagramasFlujo = new ArrayList<>();
    
    @OneToMany(mappedBy = "incidencia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnalisisCausa> analisisCausas = new ArrayList<>();
    
    // Constructor por defecto
    public Incidencia() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Constructor con campos básicos
    public Incidencia(String titulo, String descripcion, Usuario usuario) {
        this();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.usuario = usuario;
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
    
    public Prioridad getPrioridad() {
        return prioridad;
    }
    
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }
    
    public EstadoIncidencia getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
        this.fechaActualizacion = LocalDateTime.now();
        
        if (estado == EstadoIncidencia.RESUELTA) {
            this.fechaResolucion = LocalDateTime.now();
        }
    }
    
    public CategoriaIncidencia getCategoria() {
        return categoria;
    }
    
    public void setCategoria(CategoriaIncidencia categoria) {
        this.categoria = categoria;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Usuario getAsignado() {
        return asignado;
    }
    
    public void setAsignado(Usuario asignado) {
        this.asignado = asignado;
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
    
    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }
    
    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
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
    
    public Integer getFrecuenciaBusqueda() {
        return frecuenciaBusqueda;
    }
    
    public void setFrecuenciaBusqueda(Integer frecuenciaBusqueda) {
        this.frecuenciaBusqueda = frecuenciaBusqueda;
    }
    
    public List<Comentario> getComentarios() {
        return comentarios;
    }
    
    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    
    public List<HistorialIncidencia> getHistorial() {
        return historial;
    }
    
    public void setHistorial(List<HistorialIncidencia> historial) {
        this.historial = historial;
    }
    
    public List<DiagramaFlujo> getDiagramasFlujo() {
        return diagramasFlujo;
    }
    
    public void setDiagramasFlujo(List<DiagramaFlujo> diagramasFlujo) {
        this.diagramasFlujo = diagramasFlujo;
    }
    
    public List<AnalisisCausa> getAnalisisCausas() {
        return analisisCausas;
    }
    
    public void setAnalisisCausas(List<AnalisisCausa> analisisCausas) {
        this.analisisCausas = analisisCausas;
    }
    
    // Métodos de utilidad
    public void actualizarFechaModificacion() {
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public boolean estaAbierta() {
        return estado == EstadoIncidencia.ABIERTA || estado == EstadoIncidencia.EN_PROCESO;
    }
    
    public boolean estaResuelta() {
        return estado == EstadoIncidencia.RESUELTA;
    }
    
    public boolean esCritica() {
        return prioridad == Prioridad.CRITICA;
    }
    
    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", prioridad=" + prioridad +
                ", estado=" + estado +
                ", categoria=" + categoria +
                ", usuario=" + (usuario != null ? usuario.getEmpleadoId() : null) +
                ", asignado=" + (asignado != null ? asignado.getEmpleadoId() : null) +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
} 