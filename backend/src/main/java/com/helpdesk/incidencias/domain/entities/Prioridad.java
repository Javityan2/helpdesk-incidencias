package com.helpdesk.incidencias.domain.entities;

/**
 * Enum que define los niveles de prioridad de las incidencias
 */
public enum Prioridad {
    
    /**
     * Baja - No afecta operaciones críticas
     */
    BAJA("BAJA", "Baja", 1),
    
    /**
     * Media - Afecta algunas operaciones
     */
    MEDIA("MEDIA", "Media", 2),
    
    /**
     * Alta - Afecta operaciones importantes
     */
    ALTA("ALTA", "Alta", 3),
    
    /**
     * Crítica - Afecta operaciones críticas del negocio
     */
    CRITICA("CRITICA", "Crítica", 4);
    
    private final String codigo;
    private final String descripcion;
    private final int nivel;
    
    Prioridad(String codigo, String descripcion, int nivel) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.nivel = nivel;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public int getNivel() {
        return nivel;
    }
    
    /**
     * Verifica si la prioridad es crítica
     */
    public boolean esCritica() {
        return this == CRITICA;
    }
    
    /**
     * Verifica si la prioridad es alta o crítica
     */
    public boolean esAlta() {
        return this == ALTA || this == CRITICA;
    }
    
    /**
     * Obtiene la prioridad por código
     */
    public static Prioridad porCodigo(String codigo) {
        for (Prioridad prioridad : values()) {
            if (prioridad.codigo.equals(codigo)) {
                return prioridad;
            }
        }
        throw new IllegalArgumentException("Prioridad no válida: " + codigo);
    }
    
    /**
     * Obtiene la prioridad por nivel
     */
    public static Prioridad porNivel(int nivel) {
        for (Prioridad prioridad : values()) {
            if (prioridad.nivel == nivel) {
                return prioridad;
            }
        }
        throw new IllegalArgumentException("Nivel de prioridad no válido: " + nivel);
    }
} 