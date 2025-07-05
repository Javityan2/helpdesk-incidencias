package com.helpdesk.incidencias.application.dtos;

import com.helpdesk.incidencias.domain.entities.EstadoIncidencia;
import com.helpdesk.incidencias.domain.entities.TipoEvento;
import java.time.LocalDateTime;

/**
 * DTO para transferencia de datos del historial de incidencias
 */
public class HistorialIncidenciaDTO {
    
    private Long id;
    private TipoEvento tipoEvento;
    private String descripcion;
    private EstadoIncidencia estadoAnterior;
    private EstadoIncidencia estadoNuevo;
    private LocalDateTime fechaEvento;
    
    // Usuario que realizó el cambio
    private Long usuarioId;
    private String usuarioEmpleadoId;
    private String usuarioNombre;
    private String usuarioApellido;
    private String usuarioEmail;
    private String usuarioDepartamento;
    
    // Incidencia relacionada
    private Long incidenciaId;
    private String incidenciaTitulo;
    
    // Constructor por defecto
    public HistorialIncidenciaDTO() {}
    
    // Constructor con campos básicos
    public HistorialIncidenciaDTO(Long id, TipoEvento tipoEvento, String descripcion, 
                                 EstadoIncidencia estadoAnterior, EstadoIncidencia estadoNuevo, 
                                 LocalDateTime fechaEvento) {
        this.id = id;
        this.tipoEvento = tipoEvento;
        this.descripcion = descripcion;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.fechaEvento = fechaEvento;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }
    
    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public EstadoIncidencia getEstadoAnterior() {
        return estadoAnterior;
    }
    
    public void setEstadoAnterior(EstadoIncidencia estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }
    
    public EstadoIncidencia getEstadoNuevo() {
        return estadoNuevo;
    }
    
    public void setEstadoNuevo(EstadoIncidencia estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }
    
    public LocalDateTime getFechaEvento() {
        return fechaEvento;
    }
    
    public void setFechaEvento(LocalDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
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
    
    public Long getIncidenciaId() {
        return incidenciaId;
    }
    
    public void setIncidenciaId(Long incidenciaId) {
        this.incidenciaId = incidenciaId;
    }
    
    public String getIncidenciaTitulo() {
        return incidenciaTitulo;
    }
    
    public void setIncidenciaTitulo(String incidenciaTitulo) {
        this.incidenciaTitulo = incidenciaTitulo;
    }
    
    // Métodos de utilidad
    public String getNombreCompletoUsuario() {
        return usuarioNombre + " " + usuarioApellido;
    }
    
    public boolean esCambioDeEstado() {
        return estadoAnterior != null && estadoNuevo != null && !estadoAnterior.equals(estadoNuevo);
    }
    
    public boolean esCreacion() {
        return tipoEvento == TipoEvento.CREACION;
    }
    
    public boolean esActualizacion() {
        return tipoEvento == TipoEvento.CAMBIO_ESTADO;
    }
    
    public boolean esAsignacion() {
        return tipoEvento == TipoEvento.ASIGNACION;
    }
    
    public boolean esResolucion() {
        return tipoEvento == TipoEvento.RESOLUCION;
    }
    
    public boolean esCierre() {
        return tipoEvento == TipoEvento.CIERRE;
    }
    
    @Override
    public String toString() {
        return "HistorialIncidenciaDTO{" +
                "id=" + id +
                ", tipoEvento=" + tipoEvento +
                ", descripcion='" + descripcion + '\'' +
                ", estadoAnterior=" + estadoAnterior +
                ", estadoNuevo=" + estadoNuevo +
                ", usuario='" + getNombreCompletoUsuario() + '\'' +
                ", fecha=" + fechaEvento +
                '}';
    }
} 