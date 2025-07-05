package com.helpdesk.incidencias.infrastructure.controllers;

import com.helpdesk.incidencias.application.dtos.ApiResponse;
import com.helpdesk.incidencias.application.services.UsuarioService;
import com.helpdesk.incidencias.domain.entities.Rol;
import com.helpdesk.incidencias.domain.entities.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para gestión de usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    /**
     * Obtener todos los usuarios
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Usuario>>> obtenerTodosLosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            return ResponseEntity.ok(ApiResponse.success("Usuarios obtenidos exitosamente", usuarios));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener usuarios: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener usuario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> obtenerUsuario(@PathVariable Long id) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuario(id);
            if (usuarioOpt.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Usuario encontrado", usuarioOpt.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Usuario no encontrado"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener usuario: " + e.getMessage()));
        }
    }
    
    /**
     * Crear nuevo usuario
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> crearUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario creado exitosamente", usuarioCreado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al crear usuario: " + e.getMessage()));
        }
    }
    
    /**
     * Actualizar usuario
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> actualizarUsuario(@PathVariable Long id, 
                                                                 @Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
            return ResponseEntity.ok(ApiResponse.success("Usuario actualizado exitosamente", usuarioActualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al actualizar usuario: " + e.getMessage()));
        }
    }
    
    /**
     * Cambiar contraseña
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<ApiResponse<String>> cambiarPassword(@PathVariable Long id, 
                                                              @RequestBody String nuevaPassword) {
        try {
            usuarioService.cambiarPassword(id, nuevaPassword);
            return ResponseEntity.ok(ApiResponse.success("Contraseña cambiada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al cambiar contraseña: " + e.getMessage()));
        }
    }
    
    /**
     * Eliminar usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminarUsuario(@PathVariable Long id) {
        try {
            // Verificar si el usuario existe antes de eliminarlo
            Optional<Usuario> usuarioExistente = usuarioService.obtenerUsuario(id);
            if (usuarioExistente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Usuario no encontrado con ID: " + id));
            }
            
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok(ApiResponse.success("Usuario eliminado exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al eliminar usuario: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener usuarios por rol
     */
    @GetMapping("/rol/{rol}")
    public ResponseEntity<ApiResponse<List<Usuario>>> obtenerUsuariosPorRol(@PathVariable Rol rol) {
        try {
            List<Usuario> usuarios = usuarioService.obtenerUsuariosPorRol(rol);
            return ResponseEntity.ok(ApiResponse.success("Usuarios obtenidos por rol", usuarios));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener usuarios por rol: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener usuarios por departamento
     */
    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<ApiResponse<List<Usuario>>> obtenerUsuariosPorDepartamento(@PathVariable String departamento) {
        try {
            List<Usuario> usuarios = usuarioService.obtenerUsuariosPorDepartamento(departamento);
            return ResponseEntity.ok(ApiResponse.success("Usuarios obtenidos por departamento", usuarios));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener usuarios por departamento: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener técnicos
     */
    @GetMapping("/tecnicos")
    public ResponseEntity<ApiResponse<List<Usuario>>> obtenerTecnicos() {
        try {
            List<Usuario> tecnicos = usuarioService.obtenerTecnicos();
            return ResponseEntity.ok(ApiResponse.success("Técnicos obtenidos exitosamente", tecnicos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener técnicos: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener supervisores
     */
    @GetMapping("/supervisores")
    public ResponseEntity<ApiResponse<List<Usuario>>> obtenerSupervisores() {
        try {
            List<Usuario> supervisores = usuarioService.obtenerSupervisores();
            return ResponseEntity.ok(ApiResponse.success("Supervisores obtenidos exitosamente", supervisores));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener supervisores: " + e.getMessage()));
        }
    }
    
    /**
     * Activar usuario
     */
    @PutMapping("/{id}/activar")
    public ResponseEntity<ApiResponse<String>> activarUsuario(@PathVariable Long id) {
        try {
            usuarioService.activarUsuario(id);
            return ResponseEntity.ok(ApiResponse.success("Usuario activado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al activar usuario: " + e.getMessage()));
        }
    }
    
    /**
     * Desactivar usuario
     */
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<ApiResponse<String>> desactivarUsuario(@PathVariable Long id) {
        try {
            usuarioService.desactivarUsuario(id);
            return ResponseEntity.ok(ApiResponse.success("Usuario desactivado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al desactivar usuario: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener estadísticas de usuarios
     */
    @GetMapping("/estadisticas")
    public ResponseEntity<ApiResponse<UsuarioService.UsuarioStats>> obtenerEstadisticas() {
        try {
            UsuarioService.UsuarioStats stats = usuarioService.obtenerEstadisticas();
            return ResponseEntity.ok(ApiResponse.success("Estadísticas obtenidas exitosamente", stats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener estadísticas: " + e.getMessage()));
        }
    }

    /**
     * Obtiene todos los usuarios activos
     */
    @GetMapping("/activos")
    public ResponseEntity<ApiResponse<List<Usuario>>> obtenerUsuariosActivos() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerUsuariosActivos();
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios activos obtenidos exitosamente", usuarios));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener usuarios activos: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene un usuario por su ID de empleado
     */
    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<ApiResponse<Usuario>> obtenerUsuarioPorEmpleadoId(@PathVariable String empleadoId) {
        try {
            Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorEmpleadoId(empleadoId);
            if (usuario.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado exitosamente", usuario.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Usuario no encontrado con empleadoId: " + empleadoId, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener usuario: " + e.getMessage(), null));
        }
    }
} 