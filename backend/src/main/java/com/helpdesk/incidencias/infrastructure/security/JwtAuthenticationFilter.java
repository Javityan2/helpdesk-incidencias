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
        
        final String authHeader = request.getHeader(jwtHeader);
        final String jwt;
        final String empleadoId;
        
        // Si no hay header de autorización o no empieza con Bearer, continuar
        if (authHeader == null || !authHeader.startsWith(jwtPrefix + " ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Extraer el token
        jwt = authHeader.substring(jwtPrefix.length() + 1);
        
        try {
            // Extraer empleadoId del token
            empleadoId = jwtService.extractEmpleadoId(jwt);
            
            // Si hay empleadoId y no hay autenticación actual
            if (empleadoId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Buscar usuario por empleadoId
                var usuarioOpt = usuarioService.obtenerUsuarioPorEmpleadoId(empleadoId);
                
                if (usuarioOpt.isPresent()) {
                    Usuario usuario = usuarioOpt.get();
                    
                    // Validar token
                    if (jwtService.validateToken(jwt, usuario)) {
                        // Crear autenticación
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            new ArrayList<>()
                        );
                        
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception e) {
            // Log del error pero no interrumpir el flujo
            logger.error("Error procesando JWT: " + e.getMessage());
        }
        
        filterChain.doFilter(request, response);
    }
} 