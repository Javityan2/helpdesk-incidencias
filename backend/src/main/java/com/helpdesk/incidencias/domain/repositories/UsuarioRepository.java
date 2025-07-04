package com.helpdesk.incidencias.domain.repositories;

import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.Rol;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de usuarios
 */
public interface UsuarioRepository {
    
    /**
     * Guarda un usuario
     */
    Usuario save(Usuario usuario);
    
    /**
     * Busca un usuario por ID
     */
    Optional<Usuario> findById(Long id);
    
    /**
     * Busca un usuario por ID de empleado
     */
    Optional<Usuario> findByEmpleadoId(String empleadoId);
    
    /**
     * Busca un usuario por email
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Busca un usuario por email y contraseña (para autenticación)
     */
    Optional<Usuario> findByEmailAndPassword(String email, String password);
    
    /**
     * Busca un usuario por ID de empleado y contraseña (para autenticación)
     */
    Optional<Usuario> findByEmpleadoIdAndPassword(String empleadoId, String password);
    
    /**
     * Obtiene todos los usuarios
     */
    List<Usuario> findAll();
    
    /**
     * Obtiene todos los usuarios activos
     */
    List<Usuario> findByActivoTrue();
    
    /**
     * Obtiene usuarios por rol
     */
    List<Usuario> findByRol(Rol rol);
    
    /**
     * Obtiene usuarios por departamento
     */
    List<Usuario> findByDepartamento(String departamento);
    
    /**
     * Obtiene técnicos (usuarios con rol técnico o superior)
     */
    List<Usuario> findTecnicos();
    
    /**
     * Obtiene supervisores (usuarios con rol supervisor o superior)
     */
    List<Usuario> findSupervisores();
    
    /**
     * Verifica si existe un usuario con el email dado
     */
    boolean existsByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el ID de empleado dado
     */
    boolean existsByEmpleadoId(String empleadoId);
    
    /**
     * Elimina un usuario por ID
     */
    void deleteById(Long id);
    
    /**
     * Cuenta el total de usuarios
     */
    long count();
    
    /**
     * Cuenta usuarios por rol
     */
    long countByRol(Rol rol);
    
    /**
     * Cuenta usuarios activos
     */
    long countByActivoTrue();
} 