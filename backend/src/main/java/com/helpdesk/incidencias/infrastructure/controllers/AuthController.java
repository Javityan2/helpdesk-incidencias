package com.helpdesk.incidencias.infrastructure.controllers;

import com.helpdesk.incidencias.application.dtos.ApiResponse;
import com.helpdesk.incidencias.application.dtos.AuthRequest;
import com.helpdesk.incidencias.application.dtos.AuthResponse;
import com.helpdesk.incidencias.application.services.UsuarioService;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.infrastructure.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador para autenticación
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtService jwtService;
    
    /**
     * Endpoint para login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        try {
            // Intentar autenticar por email
            Optional<Usuario> usuarioOpt = usuarioService.autenticarPorEmail(request.getIdentificador(), request.getPassword());
            
            // Si no funciona, intentar por empleadoId
            if (usuarioOpt.isEmpty()) {
                usuarioOpt = usuarioService.autenticarPorEmpleadoId(request.getIdentificador(), request.getPassword());
            }
            
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                
                // Generar token JWT
                String token = jwtService.generateToken(usuario);
                
                // Crear respuesta de autenticación
                AuthResponse authResponse = new AuthResponse(
                    token,
                    usuario.getId(),
                    usuario.getEmpleadoId(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getEmail(),
                    usuario.getRol(),
                    usuario.getDepartamento(),
                    usuario.getCargo()
                );
                
                return ResponseEntity.ok(ApiResponse.success("Login exitoso", authResponse));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Credenciales inválidas"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error en el servidor: " + e.getMessage()));
        }
    }
    
    /**
     * Endpoint para registro (solo admin)
     */
    @PostMapping("/registro")
    public ResponseEntity<ApiResponse<AuthResponse>> registro(@Valid @RequestBody Usuario usuario) {
        try {
            // Crear usuario
            Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
            
            // Generar token JWT
            String token = jwtService.generateToken(usuarioCreado);
            
            // Crear respuesta de autenticación
            AuthResponse authResponse = new AuthResponse(
                token,
                usuarioCreado.getId(),
                usuarioCreado.getEmpleadoId(),
                usuarioCreado.getNombre(),
                usuarioCreado.getApellido(),
                usuarioCreado.getEmail(),
                usuarioCreado.getRol(),
                usuarioCreado.getDepartamento(),
                usuarioCreado.getCargo()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario registrado exitosamente", authResponse));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error en el servidor: " + e.getMessage()));
        }
    }
    
    /**
     * Endpoint para validar token
     */
    @GetMapping("/validar")
    public ResponseEntity<ApiResponse<String>> validarToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                // La validación se hace automáticamente en el filtro JWT
                return ResponseEntity.ok(ApiResponse.success("Token válido"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Token no proporcionado"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Token inválido"));
        }
    }
    
    /**
     * Endpoint para obtener información del usuario actual
     */
    @GetMapping("/perfil")
    public ResponseEntity<ApiResponse<AuthResponse>> obtenerPerfil(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String empleadoId = jwtService.extractEmpleadoId(token);
                
                Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuarioPorEmpleadoId(empleadoId);
                
                if (usuarioOpt.isPresent()) {
                    Usuario usuario = usuarioOpt.get();
                    
                    AuthResponse authResponse = new AuthResponse(
                        token,
                        usuario.getId(),
                        usuario.getEmpleadoId(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getEmail(),
                        usuario.getRol(),
                        usuario.getDepartamento(),
                        usuario.getCargo()
                    );
                    
                    return ResponseEntity.ok(ApiResponse.success("Perfil obtenido", authResponse));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Usuario no encontrado"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Token no proporcionado"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error en el servidor: " + e.getMessage()));
        }
    }
    
    /**
     * Endpoint de prueba para verificar el token
     */
    @GetMapping("/test-token")
    public ResponseEntity<ApiResponse<String>> testToken(@RequestHeader("Authorization") String authHeader) {
        try {
            System.out.println("AuthController - Header recibido: " + authHeader);
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                System.out.println("AuthController - Token extraído: " + token.substring(0, Math.min(50, token.length())) + "...");
                
                String empleadoId = jwtService.extractEmpleadoId(token);
                System.out.println("AuthController - EmpleadoId extraído: " + empleadoId);
                
                return ResponseEntity.ok(ApiResponse.success("Token válido para: " + empleadoId));
            } else {
                System.out.println("AuthController - Header inválido o faltante");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Token no proporcionado o formato inválido"));
            }
        } catch (Exception e) {
            System.out.println("AuthController - Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Token inválido: " + e.getMessage()));
        }
    }

    /**
     * Endpoint de prueba detallado para verificar el token
     */
    @GetMapping("/debug-token")
    public ResponseEntity<ApiResponse<String>> debugToken(@RequestHeader("Authorization") String authHeader) {
        try {
            System.out.println("AuthController - debugToken - Header recibido: " + authHeader);
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                System.out.println("AuthController - debugToken - Token extraído: " + token.substring(0, Math.min(50, token.length())) + "...");
                System.out.println("AuthController - debugToken - Token length: " + token.length());
                
                // Verificar si el token tiene el formato correcto (3 partes separadas por puntos)
                String[] parts = token.split("\\.");
                System.out.println("AuthController - debugToken - Partes del token: " + parts.length);
                
                if (parts.length != 3) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Token malformado: debe tener 3 partes separadas por puntos"));
                }
                
                try {
                    String empleadoId = jwtService.extractEmpleadoId(token);
                    System.out.println("AuthController - debugToken - EmpleadoId extraído: " + empleadoId);
                    
                    return ResponseEntity.ok(ApiResponse.success("Token válido para: " + empleadoId + " (partes: " + parts.length + ")"));
                } catch (Exception e) {
                    System.out.println("AuthController - debugToken - Error al extraer empleadoId: " + e.getMessage());
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Error al procesar token: " + e.getMessage()));
                }
            } else {
                System.out.println("AuthController - debugToken - Header inválido o faltante");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Token no proporcionado o formato inválido"));
            }
        } catch (Exception e) {
            System.out.println("AuthController - debugToken - Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Token inválido: " + e.getMessage()));
        }
    }

    /**
     * Endpoint de prueba para verificar autenticación
     */
    @GetMapping("/test-auth")
    public ResponseEntity<ApiResponse<String>> testAuth() {
        try {
            return ResponseEntity.ok(ApiResponse.success("Autenticación exitosa"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Error de autenticación: " + e.getMessage()));
        }
    }
} 