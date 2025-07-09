package com.helpdesk.incidencias.infrastructure.controllers;

import com.helpdesk.incidencias.application.dtos.ApiResponse;
import com.helpdesk.incidencias.application.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador para gestión de notificaciones
 */
@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificacionController {
    
    @Autowired
    private NotificacionService notificacionService;
    
    /**
     * Obtener todas las notificaciones del usuario
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> obtenerNotificaciones() {
        try {
            List<Map<String, Object>> notificaciones = notificacionService.obtenerNotificacionesUsuario();
            return ResponseEntity.ok(ApiResponse.success("Notificaciones obtenidas", notificaciones));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al obtener notificaciones: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener contador de notificaciones no leídas
     */
    @GetMapping("/contador-no-leidas")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerContadorNoLeidas() {
        try {
            Map<String, Object> contador = notificacionService.obtenerContadorNoLeidas();
            return ResponseEntity.ok(ApiResponse.success("Contador obtenido", contador));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al obtener contador: " + e.getMessage()));
        }
    }
    
    /**
     * Marcar notificación como leída
     */
    @PutMapping("/{id}/leer")
    public ResponseEntity<ApiResponse<String>> marcarComoLeida(@PathVariable Long id) {
        try {
            notificacionService.marcarComoLeida(id);
            return ResponseEntity.ok(ApiResponse.success("Notificación marcada como leída"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al marcar como leída: " + e.getMessage()));
        }
    }
    
    /**
     * Eliminar notificación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminarNotificacion(@PathVariable Long id) {
        try {
            notificacionService.eliminarNotificacion(id);
            return ResponseEntity.ok(ApiResponse.success("Notificación eliminada"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error("Error al eliminar notificación: " + e.getMessage()));
        }
    }
} 