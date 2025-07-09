package com.helpdesk.incidencias.infrastructure.controllers;

import com.helpdesk.incidencias.application.dtos.ApiResponse;
import com.helpdesk.incidencias.application.services.BorradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador para gestión de borradores
 */
@RestController
@RequestMapping("/api/borradores")
@CrossOrigin(origins = "http://localhost:4200")
public class BorradorController {
    
    @Autowired
    private BorradorService borradorService;
    
    /**
     * Obtener borradores del usuario
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> obtenerBorradores() {
        try {
            List<Map<String, Object>> borradores = borradorService.obtenerBorradoresUsuario();
            return ResponseEntity.ok(ApiResponse.success("Borradores obtenidos", borradores));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al obtener borradores: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener contador de borradores
     */
    @GetMapping("/contador")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerContador() {
        try {
            Map<String, Object> contador = borradorService.obtenerContadorBorradores();
            return ResponseEntity.ok(ApiResponse.success("Contador obtenido", contador));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al obtener contador: " + e.getMessage()));
        }
    }
    
    /**
     * Guardar borrador
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> guardarBorrador(@RequestBody Map<String, Object> borrador) {
        try {
            Map<String, Object> borradorGuardado = borradorService.guardarBorrador(borrador);
            return ResponseEntity.ok(ApiResponse.success("Borrador guardado", borradorGuardado));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al guardar borrador: " + e.getMessage()));
        }
    }
    
    /**
     * Eliminar borrador
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminarBorrador(@PathVariable Long id) {
        try {
            borradorService.eliminarBorrador(id);
            return ResponseEntity.ok(ApiResponse.success("Borrador eliminado"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al eliminar borrador: " + e.getMessage()));
        }
    }
} 