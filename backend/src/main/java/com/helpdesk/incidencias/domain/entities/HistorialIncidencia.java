package com.helpdesk.incidencias.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entidad que representa el historial de cambios de una incidencia
 */
@Entity
@Table(name = "historial_incidencias")
public class HistorialIncidencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incidencia_id", nullable = false)
    private Incidencia incidencia;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evento", nullable = false)
    private TipoEvento tipoEvento;
    
    @NotBlank(message = "La descripción del evento es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    
    @Column(name = "valor_anterior")
    private String valorAnterior;
    
    @Column(name = "valor_nuevo")
    private String valorNuevo;
    
    @Column(name = "fecha_evento", nullable = false)
    private LocalDateTime fechaEvento;
    
    @Column(name = "ip_usuario")
    private String ipUsuario;
    
    @Column(name = "user_agent")
    private String userAgent;
    
    // Constructor por defecto
    public HistorialIncidencia() {
        this.fechaEvento = LocalDateTime.now();
    }
    
    // Constructor con campos básicos
    public HistorialIncidencia(Incidencia incidencia, Usuario usuario, TipoEvento tipoEvento, String descripcion) {
        this();
        this.incidencia = incidencia;
        this.usuario = usuario;
        this.tipoEvento = tipoEvento;
        this.descripcion = descripcion;
    }
    
    // Constructor para cambios de valor
    public HistorialIncidencia(Incidencia incidencia, Usuario usuario, TipoEvento tipoEvento, 
                              String descripcion, String valorAnterior, String valorNuevo) {
        this(incidencia, usuario, tipoEvento, descripcion);
        this.valorAnterior = valorAnterior;
        this.valorNuevo = valorNuevo;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Incidencia getIncidencia() {
        return incidencia;
    }
    
    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
    
    public String getValorAnterior() {
        return valorAnterior;
    }
    
    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }
    
    public String getValorNuevo() {
        return valorNuevo;
    }
    
    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }
    
    public LocalDateTime getFechaEvento() {
        return fechaEvento;
    }
    
    public void setFechaEvento(LocalDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
    
    public String getIpUsuario() {
        return ipUsuario;
    }
    
    public void setIpUsuario(String ipUsuario) {
        this.ipUsuario = ipUsuario;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    // Métodos de utilidad
    public boolean esCambioDeValor() {
        return valorAnterior != null && valorNuevo != null;
    }
    
    public String getResumenCambio() {
        if (esCambioDeValor()) {
            return valorAnterior + " → " + valorNuevo;
        }
        return descripcion;
    }
    
    public boolean esEventoImportante() {
        return tipoEvento == TipoEvento.CREACION || 
               tipoEvento == TipoEvento.CAMBIO_ESTADO || 
               tipoEvento == TipoEvento.ASIGNACION || 
               tipoEvento == TipoEvento.RESOLUCION;
    }
    
    @Override
    public String toString() {
        return "HistorialIncidencia{" +
                "id=" + id +
                ", incidencia=" + (incidencia != null ? incidencia.getId() : null) +
                ", usuario=" + (usuario != null ? usuario.getEmpleadoId() : null) +
                ", tipoEvento=" + tipoEvento +
                ", descripcion='" + descripcion + '\'' +
                ", fechaEvento=" + fechaEvento +
                '}';
    }
} 