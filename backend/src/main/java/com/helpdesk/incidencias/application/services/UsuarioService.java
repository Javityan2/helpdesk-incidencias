package com.helpdesk.incidencias.application.services;

import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.Rol;
import com.helpdesk.incidencias.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

/**
 * Servicio de aplicación para la gestión de usuarios
 */
@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Crea un nuevo usuario
     */
    public Usuario crearUsuario(Usuario usuario) {
        // Validar que el empleadoId no exista
        if (usuarioRepository.existsByEmpleadoId(usuario.getEmpleadoId())) {
            throw new IllegalArgumentException("Ya existe un usuario con el ID de empleado: " + usuario.getEmpleadoId());
        }
        
        // Validar que el email no exista
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        
        // Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Establecer fecha de creación
        usuario.setFechaCreacion(LocalDateTime.now());
        
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Actualiza un usuario existente
     */
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        
        Usuario usuario = usuarioExistente.get();
        
        // Actualizar campos permitidos
        if (usuarioActualizado.getNombre() != null) {
            usuario.setNombre(usuarioActualizado.getNombre());
        }
        if (usuarioActualizado.getApellido() != null) {
            usuario.setApellido(usuarioActualizado.getApellido());
        }
        if (usuarioActualizado.getEmail() != null && !usuarioActualizado.getEmail().equals(usuario.getEmail())) {
            // Verificar que el nuevo email no exista
            if (usuarioRepository.existsByEmail(usuarioActualizado.getEmail())) {
                throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuarioActualizado.getEmail());
            }
            usuario.setEmail(usuarioActualizado.getEmail());
        }
        if (usuarioActualizado.getDepartamento() != null) {
            usuario.setDepartamento(usuarioActualizado.getDepartamento());
        }
        if (usuarioActualizado.getCargo() != null) {
            usuario.setCargo(usuarioActualizado.getCargo());
        }
        if (usuarioActualizado.getRol() != null) {
            usuario.setRol(usuarioActualizado.getRol());
        }
        if (usuarioActualizado.getActivo() != null) {
            usuario.setActivo(usuarioActualizado.getActivo());
        }
        
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Cambia la contraseña de un usuario
     */
    public void cambiarPassword(Long id, String nuevaPassword) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        
        Usuario usuario = usuarioOpt.get();
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);
    }
    
    /**
     * Autentica un usuario por empleadoId y contraseña
     */
    public Optional<Usuario> autenticarPorEmpleadoId(String empleadoId, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmpleadoId(empleadoId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(password, usuario.getPassword()) && usuario.getActivo()) {
                // Actualizar último acceso
                usuario.setUltimoAcceso(LocalDateTime.now());
                usuarioRepository.save(usuario);
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
    
    /**
     * Autentica un usuario por email y contraseña
     */
    public Optional<Usuario> autenticarPorEmail(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(password, usuario.getPassword()) && usuario.getActivo()) {
                // Actualizar último acceso
                usuario.setUltimoAcceso(LocalDateTime.now());
                usuarioRepository.save(usuario);
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
    
    /**
     * Obtiene un usuario por ID
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerUsuario(Long id) {
        return usuarioRepository.findById(id);
    }
    
    /**
     * Obtiene un usuario por empleadoId
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerUsuarioPorEmpleadoId(String empleadoId) {
        return usuarioRepository.findByEmpleadoId(empleadoId);
    }
    
    /**
     * Obtiene un usuario por email
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    /**
     * Obtiene todos los usuarios
     */
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }
    
    /**
     * Obtiene usuarios activos
     */
    @Transactional(readOnly = true)
    public List<Usuario> obtenerUsuariosActivos() {
        return usuarioRepository.findByActivoTrue();
    }
    
    /**
     * Obtiene usuarios por rol
     */
    @Transactional(readOnly = true)
    public List<Usuario> obtenerUsuariosPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol);
    }
    
    /**
     * Obtiene usuarios por departamento
     */
    @Transactional(readOnly = true)
    public List<Usuario> obtenerUsuariosPorDepartamento(String departamento) {
        return usuarioRepository.findByDepartamento(departamento);
    }
    
    /**
     * Obtiene técnicos
     */
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTecnicos() {
        return usuarioRepository.findTecnicos(List.of(Rol.TECNICO, Rol.SUPERVISOR, Rol.ADMIN));
    }
    
    /**
     * Obtiene supervisores
     */
    @Transactional(readOnly = true)
    public List<Usuario> obtenerSupervisores() {
        return usuarioRepository.findSupervisores(List.of(Rol.SUPERVISOR, Rol.ADMIN));
    }
    
    /**
     * Desactiva un usuario
     */
    public void desactivarUsuario(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
        }
    }
    
    /**
     * Activa un usuario
     */
    public void activarUsuario(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setActivo(true);
            usuarioRepository.save(usuario);
        }
    }
    
    /**
     * Elimina un usuario
     */
    public void eliminarUsuario(Long id) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
    
    /**
     * Verifica si un usuario existe
     */
    @Transactional(readOnly = true)
    public boolean existeUsuario(Long id) {
        return usuarioRepository.findById(id).isPresent();
    }
    
    /**
     * Verifica si un empleadoId existe
     */
    @Transactional(readOnly = true)
    public boolean existeEmpleadoId(String empleadoId) {
        return usuarioRepository.existsByEmpleadoId(empleadoId);
    }
    
    /**
     * Verifica si un email existe
     */
    @Transactional(readOnly = true)
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    /**
     * Obtiene estadísticas de usuarios
     */
    @Transactional(readOnly = true)
    public UsuarioStats obtenerEstadisticas() {
        long totalUsuarios = usuarioRepository.count();
        long usuariosActivos = usuarioRepository.countByActivoTrue();
        long empleados = usuarioRepository.countByRol(Rol.EMPLEADO);
        long supervisores = usuarioRepository.countByRol(Rol.SUPERVISOR);
        long tecnicos = usuarioRepository.countByRol(Rol.TECNICO);
        long admins = usuarioRepository.countByRol(Rol.ADMIN);
        
        return new UsuarioStats(totalUsuarios, usuariosActivos, empleados, supervisores, tecnicos, admins);
    }
    
    /**
     * Obtiene usuarios más activos (por número de incidencias creadas)
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerUsuariosMasActivos() {
        Map<String, Object> resultado = new HashMap<>();
        
        List<Object[]> usuariosActivos = usuarioRepository.findUsuariosMasActivos();
        Map<String, Long> porUsuario = new HashMap<>();
        
        for (Object[] usuario : usuariosActivos) {
            String nombreCompleto = (String) usuario[0];
            Long cantidadIncidencias = (Long) usuario[1];
            porUsuario.put(nombreCompleto, cantidadIncidencias);
        }
        
        resultado.put("usuariosActivos", porUsuario);
        resultado.put("totalUsuarios", porUsuario.size());
        
        return resultado;
    }
    
    /**
     * Clase para estadísticas de usuarios
     */
    public static class UsuarioStats {
        private final long totalUsuarios;
        private final long usuariosActivos;
        private final long empleados;
        private final long supervisores;
        private final long tecnicos;
        private final long admins;
        
        public UsuarioStats(long totalUsuarios, long usuariosActivos, long empleados, 
                           long supervisores, long tecnicos, long admins) {
            this.totalUsuarios = totalUsuarios;
            this.usuariosActivos = usuariosActivos;
            this.empleados = empleados;
            this.supervisores = supervisores;
            this.tecnicos = tecnicos;
            this.admins = admins;
        }
        
        // Getters
        public long getTotalUsuarios() { return totalUsuarios; }
        public long getUsuariosActivos() { return usuariosActivos; }
        public long getEmpleados() { return empleados; }
        public long getSupervisores() { return supervisores; }
        public long getTecnicos() { return tecnicos; }
        public long getAdmins() { return admins; }
    }
} 