package com.helpdesk.incidencias.application.dtos;

import com.helpdesk.incidencias.domain.entities.TipoComentario;
import java.time.LocalDateTime;

/**
 * DTO para transferencia de datos de comentarios
 */
public class ComentarioDTO {
    
    private Long id;
    private String contenido;
    private TipoComentario tipo;
    private LocalDateTime fechaCreacion;
    
    // Usuario que creó el comentario
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
    public ComentarioDTO() {}
    
    // Constructor con campos básicos
    public ComentarioDTO(Long id, String contenido, TipoComentario tipo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.contenido = contenido;
        this.tipo = tipo;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public TipoComentario getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoComentario tipo) {
        this.tipo = tipo;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
    
    public boolean esComentarioInterno() {
        return tipo == TipoComentario.INTERNO;
    }
    
    public boolean esComentarioPublico() {
        return tipo == TipoComentario.GENERAL;
    }
    
    @Override
    public String toString() {
        return "ComentarioDTO{" +
                "id=" + id +
                ", contenido='" + contenido + '\'' +
                ", tipo=" + tipo +
                ", usuario='" + getNombreCompletoUsuario() + '\'' +
                ", fecha=" + fechaCreacion +
                '}';
    }
} 