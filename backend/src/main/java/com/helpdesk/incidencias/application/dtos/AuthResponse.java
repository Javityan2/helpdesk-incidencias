package com.helpdesk.incidencias.application.dtos;

import com.helpdesk.incidencias.domain.entities.Rol;

/**
 * DTO para respuestas de autenticación
 */
public class AuthResponse {
    
    private String token;
    private String tipo = "Bearer";
    private Long usuarioId;
    private String empleadoId;
    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;
    private String departamento;
    private String cargo;
    
    // Constructor por defecto
    public AuthResponse() {}
    
    // Constructor con parámetros básicos
    public AuthResponse(String token, Long usuarioId, String empleadoId, String nombre, 
                       String apellido, String email, Rol rol) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.empleadoId = empleadoId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
    }
    
    // Constructor completo
    public AuthResponse(String token, Long usuarioId, String empleadoId, String nombre, 
                       String apellido, String email, Rol rol, String departamento, String cargo) {
        this(token, usuarioId, empleadoId, nombre, apellido, email, rol);
        this.departamento = departamento;
        this.cargo = cargo;
    }
    
    // Getters y Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getEmpleadoId() {
        return empleadoId;
    }
    
    public void setEmpleadoId(String empleadoId) {
        this.empleadoId = empleadoId;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Rol getRol() {
        return rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public String getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    public String getCargo() {
        return cargo;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
} 