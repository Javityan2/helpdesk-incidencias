package com.helpdesk.incidencias.infrastructure.security;

import com.helpdesk.incidencias.domain.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para manejo de JWT
 */
@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    /**
     * Genera un token JWT para un usuario
     */
    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("empleadoId", usuario.getEmpleadoId());
        claims.put("email", usuario.getEmail());
        claims.put("rol", usuario.getRol().name());
        claims.put("departamento", usuario.getDepartamento());
        
        return createToken(claims, usuario.getEmpleadoId());
    }
    
    /**
     * Crea un token JWT con claims específicos
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }
    
    /**
     * Obtiene la clave de firma
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    /**
     * Extrae el empleadoId del token
     */
    public String extractEmpleadoId(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    /**
     * Extrae la fecha de expiración del token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /**
     * Extrae un claim específico del token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Extrae todos los claims del token
     */
    private Claims extractAllClaims(String token) {
        System.out.println("JwtService - extractAllClaims - Token recibido: " + token.substring(0, Math.min(50, token.length())) + "...");
        System.out.println("JwtService - extractAllClaims - Token length: " + token.length());
        
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            
            System.out.println("JwtService - extractAllClaims - Claims extraídos exitosamente");
            return claims;
        } catch (Exception e) {
            System.out.println("JwtService - extractAllClaims - Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Verifica si el token ha expirado
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    /**
     * Valida un token JWT
     */
    public Boolean validateToken(String token, Usuario usuario) {
        try {
            System.out.println("JwtService - validateToken - Iniciando validación");
            System.out.println("JwtService - validateToken - Token recibido: " + token.substring(0, Math.min(50, token.length())) + "...");
            System.out.println("JwtService - validateToken - Usuario: " + usuario.getEmpleadoId());
            
            final String empleadoId = extractEmpleadoId(token);
            System.out.println("JwtService - validateToken - EmpleadoId extraído: " + empleadoId);
            System.out.println("JwtService - validateToken - EmpleadoId del usuario: " + usuario.getEmpleadoId());
            System.out.println("JwtService - validateToken - EmpleadoIds coinciden: " + empleadoId.equals(usuario.getEmpleadoId()));
            
            boolean isExpired = isTokenExpired(token);
            System.out.println("JwtService - validateToken - Token expirado: " + isExpired);
            
            boolean isValid = (empleadoId.equals(usuario.getEmpleadoId()) && !isExpired);
            System.out.println("JwtService - validateToken - Token válido: " + isValid);
            
            return isValid;
        } catch (Exception e) {
            System.out.println("JwtService - validateToken - Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Extrae el rol del token
     */
    public String extractRol(String token) {
        return extractClaim(token, claims -> claims.get("rol", String.class));
    }
    
    /**
     * Extrae el email del token
     */
    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }
    
    /**
     * Extrae el departamento del token
     */
    public String extractDepartamento(String token) {
        return extractClaim(token, claims -> claims.get("departamento", String.class));
    }
} 