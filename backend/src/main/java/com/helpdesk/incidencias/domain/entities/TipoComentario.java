package com.helpdesk.incidencias.domain.entities;

/**
 * Enum que define los tipos de comentarios en las incidencias
 */
public enum TipoComentario {
    
    /**
     * Comentario general
     */
    GENERAL("GENERAL", "General"),
    
    /**
     * Actualización de estado
     */
    ACTUALIZACION("ACTUALIZACION", "Actualización"),
    
    /**
     * Solución propuesta
     */
    SOLUCION("SOLUCION", "Solución"),
    
    /**
     * Pregunta o consulta
     */
    PREGUNTA("PREGUNTA", "Pregunta"),
    
    /**
     * Respuesta a una pregunta
     */
    RESPUESTA("RESPUESTA", "Respuesta"),
    
    /**
     * Comentario técnico
     */
    TECNICO("TECNICO", "Técnico"),
    
    /**
     * Comentario interno (solo para técnicos)
     */
    INTERNO("INTERNO", "Interno"),
    
    /**
     * Nota importante
     */
    NOTA("NOTA", "Nota"),
    
    /**
     * Comentario de seguimiento
     */
    SEGUIMIENTO("SEGUIMIENTO", "Seguimiento"),
    
    /**
     * Comentario de cierre
     */
    CIERRE("CIERRE", "Cierre");
    
    private final String codigo;
    private final String descripcion;
    
    TipoComentario(String codigo, String descripcion) {
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
     * Verifica si el tipo es interno
     */
    public boolean esInterno() {
        return this == INTERNO;
    }
    
    /**
     * Verifica si el tipo es técnico
     */
    public boolean esTecnico() {
        return this == TECNICO || this == INTERNO;
    }
    
    /**
     * Verifica si el tipo es de actualización
     */
    public boolean esActualizacion() {
        return this == ACTUALIZACION || this == SEGUIMIENTO;
    }
    
    /**
     * Verifica si el tipo es de resolución
     */
    public boolean esResolucion() {
        return this == SOLUCION || this == CIERRE;
    }
    
    /**
     * Obtiene el tipo por código
     */
    public static TipoComentario porCodigo(String codigo) {
        for (TipoComentario tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de comentario no válido: " + codigo);
    }
} 