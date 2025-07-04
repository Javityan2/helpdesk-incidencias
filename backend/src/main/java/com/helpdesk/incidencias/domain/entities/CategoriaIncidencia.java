package com.helpdesk.incidencias.domain.entities;

/**
 * Enum que define las categorías de incidencias
 */
public enum CategoriaIncidencia {
    
    /**
     * Problemas de hardware
     */
    HARDWARE("HARDWARE", "Hardware"),
    
    /**
     * Problemas de software
     */
    SOFTWARE("SOFTWARE", "Software"),
    
    /**
     * Problemas de red
     */
    RED("RED", "Red"),
    
    /**
     * Problemas de seguridad
     */
    SEGURIDAD("SEGURIDAD", "Seguridad"),
    
    /**
     * Problemas de acceso
     */
    ACCESO("ACCESO", "Acceso"),
    
    /**
     * Problemas de impresión
     */
    IMPRESION("IMPRESION", "Impresión"),
    
    /**
     * Problemas de email
     */
    EMAIL("EMAIL", "Email"),
    
    /**
     * Problemas de base de datos
     */
    BASE_DATOS("BASE_DATOS", "Base de Datos"),
    
    /**
     * Problemas de servidor
     */
    SERVIDOR("SERVIDOR", "Servidor"),
    
    /**
     * Problemas de aplicación
     */
    APLICACION("APLICACION", "Aplicación"),
    
    /**
     * Problemas de configuración
     */
    CONFIGURACION("CONFIGURACION", "Configuración"),
    
    /**
     * Problemas de mantenimiento
     */
    MANTENIMIENTO("MANTENIMIENTO", "Mantenimiento"),
    
    /**
     * Problemas de capacitación
     */
    CAPACITACION("CAPACITACION", "Capacitación"),
    
    /**
     * Otros problemas
     */
    OTROS("OTROS", "Otros");
    
    private final String codigo;
    private final String descripcion;
    
    CategoriaIncidencia(String codigo, String descripcion) {
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
     * Verifica si es una categoría técnica
     */
    public boolean esTecnica() {
        return this == HARDWARE || this == SOFTWARE || this == RED || 
               this == SERVIDOR || this == BASE_DATOS || this == APLICACION;
    }
    
    /**
     * Verifica si es una categoría de infraestructura
     */
    public boolean esInfraestructura() {
        return this == HARDWARE || this == RED || this == SERVIDOR || 
               this == IMPRESION || this == MANTENIMIENTO;
    }
    
    /**
     * Verifica si es una categoría de seguridad
     */
    public boolean esSeguridad() {
        return this == SEGURIDAD || this == ACCESO;
    }
    
    /**
     * Obtiene la categoría por código
     */
    public static CategoriaIncidencia porCodigo(String codigo) {
        for (CategoriaIncidencia categoria : values()) {
            if (categoria.codigo.equals(codigo)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoría no válida: " + codigo);
    }
} 