package com.helpdesk.incidencias.infrastructure.controllers;

import com.helpdesk.incidencias.application.dtos.ApiResponse;
import com.helpdesk.incidencias.application.services.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador para gestión de favoritos
 */
@RestController
@RequestMapping("/api/favoritos")
@CrossOrigin(origins = "http://localhost:4200")
public class FavoritoController {
    
    @Autowired
    private FavoritoService favoritoService;
    
    /**
     * Obtener favoritos del usuario
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> obtenerFavoritos() {
        try {
            List<Map<String, Object>> favoritos = favoritoService.obtenerFavoritosUsuario();
            return ResponseEntity.ok(ApiResponse.success("Favoritos obtenidos", favoritos));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al obtener favoritos: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener contador de favoritos
     */
    @GetMapping("/contador")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerContador() {
        try {
            Map<String, Object> contador = favoritoService.obtenerContadorFavoritos();
            return ResponseEntity.ok(ApiResponse.success("Contador obtenido", contador));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al obtener contador: " + e.getMessage()));
        }
    }
    
    /**
     * Agregar a favoritos
     */
    @PostMapping("/{incidenciaId}")
    public ResponseEntity<ApiResponse<String>> agregarFavorito(@PathVariable Long incidenciaId) {
        try {
            favoritoService.agregarFavorito(incidenciaId);
            return ResponseEntity.ok(ApiResponse.success("Agregado a favoritos"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al agregar a favoritos: " + e.getMessage()));
        }
    }
    
    /**
     * Eliminar de favoritos
     */
    @DeleteMapping("/{incidenciaId}")
    public ResponseEntity<ApiResponse<String>> eliminarFavorito(@PathVariable Long incidenciaId) {
        try {
            favoritoService.eliminarFavorito(incidenciaId);
            return ResponseEntity.ok(ApiResponse.success("Eliminado de favoritos"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al eliminar de favoritos: " + e.getMessage()));
        }
    }
} 