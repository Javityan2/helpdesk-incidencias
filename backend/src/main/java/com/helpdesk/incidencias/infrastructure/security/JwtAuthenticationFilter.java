package com.helpdesk.incidencias.infrastructure.security;

import com.helpdesk.incidencias.application.services.UsuarioService;
import com.helpdesk.incidencias.domain.entities.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Filtro JWT para autenticación
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Value("${jwt.header}")
    private String jwtHeader;
    
    @Value("${jwt.prefix}")
    private String jwtPrefix;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        System.out.println("JwtAuthenticationFilter - ===== NUEVA PETICIÓN =====");
        System.out.println("JwtAuthenticationFilter - Método: " + request.getMethod());
        System.out.println("JwtAuthenticationFilter - URL: " + request.getRequestURI());
        
        final String authHeader = request.getHeader(jwtHeader);
        final String jwt;
        final String empleadoId;
        
        System.out.println("JwtAuthenticationFilter - AuthHeader: " + (authHeader != null ? authHeader.substring(0, Math.min(50, authHeader.length())) + "..." : "null"));
        System.out.println("JwtAuthenticationFilter - AuthHeader completo: " + authHeader);
        System.out.println("JwtAuthenticationFilter - AuthHeader length: " + (authHeader != null ? authHeader.length() : 0));
        
        // Si no hay header de autorización o no empieza con Bearer, continuar
        if (authHeader == null || !authHeader.startsWith(jwtPrefix + " ")) {
            System.out.println("JwtAuthenticationFilter - No hay header de autorización válido");
            System.out.println("JwtAuthenticationFilter - jwtPrefix: " + jwtPrefix);
            System.out.println("JwtAuthenticationFilter - authHeader starts with Bearer: " + (authHeader != null ? authHeader.startsWith("Bearer ") : false));
            System.out.println("JwtAuthenticationFilter - authHeader starts with jwtPrefix + space: " + (authHeader != null ? authHeader.startsWith(jwtPrefix + " ") : false));
            System.out.println("JwtAuthenticationFilter - authHeader es null: " + (authHeader == null));
            System.out.println("JwtAuthenticationFilter - authHeader vacío: " + (authHeader != null && authHeader.trim().isEmpty()));
            System.out.println("JwtAuthenticationFilter - authHeader length: " + (authHeader != null ? authHeader.length() : 0));
            System.out.println("JwtAuthenticationFilter - Continuando sin autenticación para URL: " + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        
        // Extraer el token
        jwt = authHeader.substring(jwtPrefix.length() + 1);
        System.out.println("JwtAuthenticationFilter - Token extraído: " + jwt.substring(0, Math.min(50, jwt.length())) + "...");
        System.out.println("JwtAuthenticationFilter - Token completo: " + jwt);
        System.out.println("JwtAuthenticationFilter - Token length: " + jwt.length());
        System.out.println("JwtAuthenticationFilter - jwtPrefix.length(): " + jwtPrefix.length());
        System.out.println("JwtAuthenticationFilter - Substring desde: " + (jwtPrefix.length() + 1));
        
        // Verificar que el token no esté vacío
        if (jwt == null || jwt.trim().isEmpty()) {
            System.out.println("JwtAuthenticationFilter - ERROR: Token extraído está vacío");
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            // Extraer empleadoId del token
            empleadoId = jwtService.extractEmpleadoId(jwt);
            System.out.println("JwtAuthenticationFilter - EmpleadoId extraído: " + empleadoId);
            
            // Si hay empleadoId y no hay autenticación actual
            if (empleadoId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Buscar usuario por empleadoId
                var usuarioOpt = usuarioService.obtenerUsuarioPorEmpleadoId(empleadoId);
                
                if (usuarioOpt.isPresent()) {
                    Usuario usuario = usuarioOpt.get();
                    System.out.println("JwtAuthenticationFilter - Usuario encontrado: " + usuario.getEmpleadoId());
                    
                    // Validar token
                    if (jwtService.validateToken(jwt, usuario)) {
                        System.out.println("JwtAuthenticationFilter - Token válido, creando autenticación");
                        
                        // Crear lista de autoridades con el prefijo ROLE_
                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
                        
                        // Crear autenticación
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            authorities
                        );
                        
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        System.out.println("JwtAuthenticationFilter - Autenticación establecida con rol: " + usuario.getRol().name());
                    } else {
                        System.out.println("JwtAuthenticationFilter - Token inválido");
                        System.out.println("JwtAuthenticationFilter - Token que falló validación: " + jwt.substring(0, Math.min(50, jwt.length())) + "...");
                    }
                } else {
                    System.out.println("JwtAuthenticationFilter - Usuario no encontrado para empleadoId: " + empleadoId);
                }
            } else {
                System.out.println("JwtAuthenticationFilter - EmpleadoId es null o ya hay autenticación");
            }
        } catch (Exception e) {
            // Log del error pero no interrumpir el flujo
            System.out.println("JwtAuthenticationFilter - Error procesando JWT: " + e.getMessage());
            System.out.println("JwtAuthenticationFilter - Token que causó el error: " + jwt);
            e.printStackTrace();
        }
        
        filterChain.doFilter(request, response);
    }
} 