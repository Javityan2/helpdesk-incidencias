package com.helpdesk.incidencias.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entidad que representa un comentario en una incidencia
 */
@Entity
@Table(name = "comentarios")
public class Comentario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El contenido del comentario es obligatorio")
    @Size(max = 2000, message = "El comentario no puede exceder 2000 caracteres")
    @Column(name = "contenido", columnDefinition = "TEXT", nullable = false)
    private String contenido;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incidencia_id", nullable = false)
    private Incidencia incidencia;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoComentario tipo = TipoComentario.GENERAL;
    
    @Column(name = "es_interno", nullable = false)
    private Boolean esInterno = false;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "likes")
    private Integer likes = 0;
    
    @Column(name = "dislikes")
    private Integer dislikes = 0;
    
    // Constructor por defecto
    public Comentario() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Constructor con campos básicos
    public Comentario(String contenido, Usuario usuario, Incidencia incidencia) {
        this();
        this.contenido = contenido;
        this.usuario = usuario;
        this.incidencia = incidencia;
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
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Incidencia getIncidencia() {
        return incidencia;
    }
    
    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }
    
    public TipoComentario getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoComentario tipo) {
        this.tipo = tipo;
    }
    
    public Boolean getEsInterno() {
        return esInterno;
    }
    
    public void setEsInterno(Boolean esInterno) {
        this.esInterno = esInterno;
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
    
    public Integer getLikes() {
        return likes;
    }
    
    public void setLikes(Integer likes) {
        this.likes = likes;
    }
    
    public Integer getDislikes() {
        return dislikes;
    }
    
    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }
    
    // Métodos de utilidad
    public void incrementarLikes() {
        this.likes++;
    }
    
    public void incrementarDislikes() {
        this.dislikes++;
    }
    
    public void decrementarLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }
    
    public void decrementarDislikes() {
        if (this.dislikes > 0) {
            this.dislikes--;
        }
    }
    
    public int getPuntuacion() {
        return likes - dislikes;
    }
    
    public boolean esPositivo() {
        return getPuntuacion() > 0;
    }
    
    public boolean esNegativo() {
        return getPuntuacion() < 0;
    }
    
    public boolean esNeutral() {
        return getPuntuacion() == 0;
    }
    
    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", contenido='" + contenido + '\'' +
                ", usuario=" + (usuario != null ? usuario.getEmpleadoId() : null) +
                ", incidencia=" + (incidencia != null ? incidencia.getId() : null) +
                ", tipo=" + tipo +
                ", esInterno=" + esInterno +
                ", fechaCreacion=" + fechaCreacion +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }
} 