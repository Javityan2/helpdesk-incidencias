package com.helpdesk.incidencias.application.dtos;

import com.helpdesk.incidencias.domain.entities.EstadoIncidencia;
import com.helpdesk.incidencias.domain.entities.Prioridad;
import com.helpdesk.incidencias.domain.entities.CategoriaIncidencia;
import com.helpdesk.incidencias.domain.entities.Rol;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para transferencia de datos de incidencias
 */
public class IncidenciaDTO {
    
    private Long id;
    private String titulo;
    private String descripcion;
    private Prioridad prioridad;
    private EstadoIncidencia estado;
    private CategoriaIncidencia categoria;
    
    // Usuario que creó la incidencia
    private Long usuarioId;
    private String usuarioEmpleadoId;
    private String usuarioNombre;
    private String usuarioApellido;
    private String usuarioEmail;
    private String usuarioDepartamento;
    
    // Técnico asignado
    private Long asignadoId;
    private String asignadoEmpleadoId;
    private String asignadoNombre;
    private String asignadoApellido;
    private String asignadoEmail;
    private Rol asignadoRol;
    
    // Fechas
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime fechaResolucion;
    
    // Tiempos
    private Integer tiempoEstimadoHoras;
    private Integer tiempoRealHoras;
    
    // Contenido
    private String solucion;
    private String comentariosInternos;
    
    // Estadísticas
    private Integer numeroComentarios;
    private Integer numeroHistorial;
    private Integer numeroDiagramas;
    
    // Listas relacionadas
    private List<ComentarioDTO> comentarios;
    private List<HistorialIncidenciaDTO> historial;
    
    // Constructor por defecto
    public IncidenciaDTO() {}
    
    // Constructor con campos básicos
    public IncidenciaDTO(Long id, String titulo, String descripcion, Prioridad prioridad, 
                        EstadoIncidencia estado, CategoriaIncidencia categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.categoria = categoria;
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
    }
    
    public CategoriaIncidencia getCategoria() {
        return categoria;
    }
    
    public void setCategoria(CategoriaIncidencia categoria) {
        this.categoria = categoria;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getUsuarioEmpleadoId() {
        return usuarioEmpleadoId;
    }
    
    public void setUsuarioEmpleadoId(String usuarioEmpleadoId) {
        this.usuarioEmpleadoId = usuarioEmpleadoId;
    }
    
    public String getUsuarioNombre() {
        return usuarioNombre;
    }
    
    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }
    
    public String getUsuarioApellido() {
        return usuarioApellido;
    }
    
    public void setUsuarioApellido(String usuarioApellido) {
        this.usuarioApellido = usuarioApellido;
    }
    
    public String getUsuarioEmail() {
        return usuarioEmail;
    }
    
    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }
    
    public String getUsuarioDepartamento() {
        return usuarioDepartamento;
    }
    
    public void setUsuarioDepartamento(String usuarioDepartamento) {
        this.usuarioDepartamento = usuarioDepartamento;
    }
    
    public Long getAsignadoId() {
        return asignadoId;
    }
    
    public void setAsignadoId(Long asignadoId) {
        this.asignadoId = asignadoId;
    }
    
    public String getAsignadoEmpleadoId() {
        return asignadoEmpleadoId;
    }
    
    public void setAsignadoEmpleadoId(String asignadoEmpleadoId) {
        this.asignadoEmpleadoId = asignadoEmpleadoId;
    }
    
    public String getAsignadoNombre() {
        return asignadoNombre;
    }
    
    public void setAsignadoNombre(String asignadoNombre) {
        this.asignadoNombre = asignadoNombre;
    }
    
    public String getAsignadoApellido() {
        return asignadoApellido;
    }
    
    public void setAsignadoApellido(String asignadoApellido) {
        this.asignadoApellido = asignadoApellido;
    }
    
    public String getAsignadoEmail() {
        return asignadoEmail;
    }
    
    public void setAsignadoEmail(String asignadoEmail) {
        this.asignadoEmail = asignadoEmail;
    }
    
    public Rol getAsignadoRol() {
        return asignadoRol;
    }
    
    public void setAsignadoRol(Rol asignadoRol) {
        this.asignadoRol = asignadoRol;
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
    
    public Integer getNumeroComentarios() {
        return numeroComentarios;
    }
    
    public void setNumeroComentarios(Integer numeroComentarios) {
        this.numeroComentarios = numeroComentarios;
    }
    
    public Integer getNumeroHistorial() {
        return numeroHistorial;
    }
    
    public void setNumeroHistorial(Integer numeroHistorial) {
        this.numeroHistorial = numeroHistorial;
    }
    
    public Integer getNumeroDiagramas() {
        return numeroDiagramas;
    }
    
    public void setNumeroDiagramas(Integer numeroDiagramas) {
        this.numeroDiagramas = numeroDiagramas;
    }
    
    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }
    
    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }
    
    public List<HistorialIncidenciaDTO> getHistorial() {
        return historial;
    }
    
    public void setHistorial(List<HistorialIncidenciaDTO> historial) {
        this.historial = historial;
    }
    
    // Métodos de utilidad
    public String getNombreCompletoUsuario() {
        return usuarioNombre + " " + usuarioApellido;
    }
    
    public String getNombreCompletoAsignado() {
        if (asignadoNombre != null && asignadoApellido != null) {
            return asignadoNombre + " " + asignadoApellido;
        }
        return null;
    }
    
    public boolean estaAbierta() {
        return estado == EstadoIncidencia.ABIERTA;
    }
    
    public boolean estaResuelta() {
        return estado == EstadoIncidencia.RESUELTA;
    }
    
    public boolean esCritica() {
        return prioridad == Prioridad.CRITICA;
    }
    
    @Override
    public String toString() {
        return "IncidenciaDTO{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", estado=" + estado +
                ", prioridad=" + prioridad +
                ", usuario='" + getNombreCompletoUsuario() + '\'' +
                '}';
    }
} 