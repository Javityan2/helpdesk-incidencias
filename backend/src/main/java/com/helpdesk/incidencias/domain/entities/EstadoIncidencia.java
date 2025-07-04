package com.helpdesk.incidencias.domain.entities;

/**
 * Enum que define los estados de una incidencia
 */
public enum EstadoIncidencia {
    
    /**
     * Abierta - Incidencia recién creada
     */
    ABIERTA("ABIERTA", "Abierta", 1),
    
    /**
     * En Proceso - Incidencia siendo trabajada
     */
    EN_PROCESO("EN_PROCESO", "En Proceso", 2),
    
    /**
     * En Espera - Esperando información o recursos
     */
    EN_ESPERA("EN_ESPERA", "En Espera", 3),
    
    /**
     * Resuelta - Incidencia solucionada
     */
    RESUELTA("RESUELTA", "Resuelta", 4),
    
    /**
     * Cerrada - Incidencia cerrada definitivamente
     */
    CERRADA("CERRADA", "Cerrada", 5),
    
    /**
     * Cancelada - Incidencia cancelada
     */
    CANCELADA("CANCELADA", "Cancelada", 6);
    
    private final String codigo;
    private final String descripcion;
    private final int orden;
    
    EstadoIncidencia(String codigo, String descripcion, int orden) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.orden = orden;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public int getOrden() {
        return orden;
    }
    
    /**
     * Verifica si el estado es final
     */
    public boolean esFinal() {
        return this == RESUELTA || this == CERRADA || this == CANCELADA;
    }
    
    /**
     * Verifica si el estado es activo
     */
    public boolean esActivo() {
        return this == ABIERTA || this == EN_PROCESO || this == EN_ESPERA;
    }
    
    /**
     * Verifica si se puede cambiar a otro estado
     */
    public boolean puedeCambiarA(EstadoIncidencia nuevoEstado) {
        switch (this) {
            case ABIERTA:
                return nuevoEstado == EN_PROCESO || nuevoEstado == EN_ESPERA || 
                       nuevoEstado == RESUELTA || nuevoEstado == CANCELADA;
            case EN_PROCESO:
                return nuevoEstado == EN_ESPERA || nuevoEstado == RESUELTA || 
                       nuevoEstado == CANCELADA;
            case EN_ESPERA:
                return nuevoEstado == EN_PROCESO || nuevoEstado == RESUELTA || 
                       nuevoEstado == CANCELADA;
            case RESUELTA:
                return nuevoEstado == CERRADA;
            case CERRADA:
            case CANCELADA:
                return false; // Estados finales
            default:
                return false;
        }
    }
    
    /**
     * Obtiene el estado por código
     */
    public static EstadoIncidencia porCodigo(String codigo) {
        for (EstadoIncidencia estado : values()) {
            if (estado.codigo.equals(codigo)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + codigo);
    }
    
    /**
     * Obtiene el estado por orden
     */
    public static EstadoIncidencia porOrden(int orden) {
        for (EstadoIncidencia estado : values()) {
            if (estado.orden == orden) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Orden de estado no válido: " + orden);
    }
} 