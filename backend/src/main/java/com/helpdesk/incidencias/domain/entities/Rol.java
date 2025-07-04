package com.helpdesk.incidencias.domain.entities;

/**
 * Enum que define los roles de usuario en el sistema
 */
public enum Rol {
    
    /**
     * Empleado básico - puede crear y ver incidencias
     */
    EMPLEADO("EMPLEADO", "Empleado"),
    
    /**
     * Supervisor - puede gestionar incidencias de su departamento
     */
    SUPERVISOR("SUPERVISOR", "Supervisor"),
    
    /**
     * Administrador - acceso completo al sistema
     */
    ADMIN("ADMIN", "Administrador"),
    
    /**
     * Técnico de soporte - especializado en resolución
     */
    TECNICO("TECNICO", "Técnico de Soporte");
    
    private final String codigo;
    private final String descripcion;
    
    Rol(String codigo, String descripcion) {
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
     * Verifica si el rol tiene permisos de administración
     */
    public boolean esAdmin() {
        return this == ADMIN;
    }
    
    /**
     * Verifica si el rol tiene permisos de supervisión
     */
    public boolean esSupervisor() {
        return this == SUPERVISOR || this == ADMIN;
    }
    
    /**
     * Verifica si el rol es técnico
     */
    public boolean esTecnico() {
        return this == TECNICO || this == SUPERVISOR || this == ADMIN;
    }
    
    /**
     * Obtiene el rol por código
     */
    public static Rol porCodigo(String codigo) {
        for (Rol rol : values()) {
            if (rol.codigo.equals(codigo)) {
                return rol;
            }
        }
        throw new IllegalArgumentException("Rol no válido: " + codigo);
    }
} 