package com.helpdesk.incidencias.domain.entities;

/**
 * Enum que define los tipos de eventos del historial de incidencias
 */
public enum TipoEvento {
    
    /**
     * Creación de la incidencia
     */
    CREACION("CREACION", "Creación"),
    
    /**
     * Cambio de estado
     */
    CAMBIO_ESTADO("CAMBIO_ESTADO", "Cambio de Estado"),
    
    /**
     * Asignación a técnico
     */
    ASIGNACION("ASIGNACION", "Asignación"),
    
    /**
     * Cambio de prioridad
     */
    CAMBIO_PRIORIDAD("CAMBIO_PRIORIDAD", "Cambio de Prioridad"),
    
    /**
     * Cambio de categoría
     */
    CAMBIO_CATEGORIA("CAMBIO_CATEGORIA", "Cambio de Categoría"),
    
    /**
     * Agregado comentario
     */
    COMENTARIO("COMENTARIO", "Comentario"),
    
    /**
     * Agregado archivo adjunto
     */
    ADJUNTO("ADJUNTO", "Archivo Adjunto"),
    
    /**
     * Resolución de la incidencia
     */
    RESOLUCION("RESOLUCION", "Resolución"),
    
    /**
     * Cierre de la incidencia
     */
    CIERRE("CIERRE", "Cierre"),
    
    /**
     * Reapertura de la incidencia
     */
    REAPERTURA("REAPERTURA", "Reapertura"),
    
    /**
     * Escalación de la incidencia
     */
    ESCALACION("ESCALACION", "Escalación"),
    
    /**
     * Cambio de tiempo estimado
     */
    CAMBIO_TIEMPO("CAMBIO_TIEMPO", "Cambio de Tiempo"),
    
    /**
     * Actualización de solución
     */
    ACTUALIZACION_SOLUCION("ACTUALIZACION_SOLUCION", "Actualización de Solución"),
    
    /**
     * Notificación enviada
     */
    NOTIFICACION("NOTIFICACION", "Notificación"),
    
    /**
     * Otro tipo de evento
     */
    OTRO("OTRO", "Otro");
    
    private final String codigo;
    private final String descripcion;
    
    TipoEvento(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Verifica si el evento es de creación
     */
    public boolean esCreacion() {
        return this == CREACION;
    }
    
    /**
     * Verifica si el evento es de resolución
     */
    public boolean esResolucion() {
        return this == RESOLUCION || this == CIERRE;
    }
    
    /**
     * Verifica si el evento es de cambio de estado
     */
    public boolean esCambioEstado() {
        return this == CAMBIO_ESTADO || this == RESOLUCION || this == CIERRE || this == REAPERTURA;
    }
    
    /**
     * Verifica si el evento es de asignación
     */
    public boolean esAsignacion() {
        return this == ASIGNACION || this == ESCALACION;
    }
    
    /**
     * Verifica si el evento es importante para el seguimiento
     */
    public boolean esImportante() {
        return this == CREACION || this == CAMBIO_ESTADO || this == ASIGNACION || 
               this == RESOLUCION || this == CIERRE || this == REAPERTURA || this == ESCALACION;
    }
    
    /**
     * Obtiene el tipo por código
     */
    public static TipoEvento porCodigo(String codigo) {
        for (TipoEvento tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de evento no válido: " + codigo);
    }
} 