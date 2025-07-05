package com.helpdesk.incidencias.infrastructure.controllers;

import com.helpdesk.incidencias.application.dtos.ApiResponse;
import com.helpdesk.incidencias.application.services.IncidenciaService;
import com.helpdesk.incidencias.application.services.UsuarioService;
import com.helpdesk.incidencias.application.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * Controlador REST para dashboard y estadísticas
 */
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {
    
    @Autowired
    private IncidenciaService incidenciaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ComentarioService comentarioService;
    
    /**
     * Obtener estadísticas generales del sistema
     */
    @GetMapping("/estadisticas-generales")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerEstadisticasGenerales() {
        try {
            Map<String, Object> estadisticas = incidenciaService.obtenerEstadisticasGenerales();
            
            return ResponseEntity.ok(ApiResponse.success("Estadísticas obtenidas", estadisticas));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener estadísticas: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener estadísticas por departamento
     */
    @GetMapping("/estadisticas-departamento")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerEstadisticasPorDepartamento() {
        try {
            Map<String, Object> estadisticas = incidenciaService.obtenerEstadisticasPorDepartamento();
            
            return ResponseEntity.ok(ApiResponse.success("Estadísticas por departamento obtenidas", estadisticas));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener estadísticas por departamento: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener estadísticas por técnico
     */
    @GetMapping("/estadisticas-tecnico")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerEstadisticasPorTecnico() {
        try {
            Map<String, Object> estadisticas = incidenciaService.obtenerEstadisticasPorTecnico();
            
            return ResponseEntity.ok(ApiResponse.success("Estadísticas por técnico obtenidas", estadisticas));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener estadísticas por técnico: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener incidencias por estado
     */
    @GetMapping("/incidencias-por-estado")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerIncidenciasPorEstado() {
        try {
            Map<String, Object> estadisticas = incidenciaService.obtenerIncidenciasPorEstado();
            
            return ResponseEntity.ok(ApiResponse.success("Incidencias por estado obtenidas", estadisticas));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener incidencias por estado: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener incidencias por prioridad
     */
    @GetMapping("/incidencias-por-prioridad")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerIncidenciasPorPrioridad() {
        try {
            Map<String, Object> estadisticas = incidenciaService.obtenerIncidenciasPorPrioridad();
            
            return ResponseEntity.ok(ApiResponse.success("Incidencias por prioridad obtenidas", estadisticas));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener incidencias por prioridad: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener incidencias por categoría
     */
    @GetMapping("/incidencias-por-categoria")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerIncidenciasPorCategoria() {
        try {
            Map<String, Object> estadisticas = incidenciaService.obtenerIncidenciasPorCategoria();
            
            return ResponseEntity.ok(ApiResponse.success("Incidencias por categoría obtenidas", estadisticas));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener incidencias por categoría: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener tendencias de incidencias por fecha
     */
    @GetMapping("/tendencias")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerTendencias(
            @RequestParam(required = false) LocalDate fechaInicio,
            @RequestParam(required = false) LocalDate fechaFin) {
        try {
            Map<String, Object> tendencias = incidenciaService.obtenerTendencias(fechaInicio, fechaFin);
            
            return ResponseEntity.ok(ApiResponse.success("Tendencias obtenidas", tendencias));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener tendencias: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener tiempo promedio de resolución
     */
    @GetMapping("/tiempo-promedio-resolucion")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerTiempoPromedioResolucion() {
        try {
            Map<String, Object> estadisticas = incidenciaService.obtenerTiempoPromedioResolucion();
            
            return ResponseEntity.ok(ApiResponse.success("Tiempo promedio de resolución obtenido", estadisticas));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener tiempo promedio: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener incidencias críticas
     */
    @GetMapping("/incidencias-criticas")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerIncidenciasCriticas() {
        try {
            Map<String, Object> incidencias = incidenciaService.obtenerIncidenciasCriticasMap();
            
            return ResponseEntity.ok(ApiResponse.success("Incidencias críticas obtenidas", incidencias));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener incidencias críticas: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener incidencias sin asignar
     */
    @GetMapping("/incidencias-sin-asignar")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerIncidenciasSinAsignar() {
        try {
            Map<String, Object> incidencias = incidenciaService.obtenerIncidenciasSinAsignarMap();
            
            return ResponseEntity.ok(ApiResponse.success("Incidencias sin asignar obtenidas", incidencias));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener incidencias sin asignar: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener estadísticas de comentarios
     */
    @GetMapping("/estadisticas-comentarios")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerEstadisticasComentarios() {
        try {
            Map<String, Object> estadisticas = comentarioService.obtenerEstadisticasGenerales();
            
            return ResponseEntity.ok(ApiResponse.success("Estadísticas de comentarios obtenidas", estadisticas));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener estadísticas de comentarios: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener usuarios más activos
     */
    @GetMapping("/usuarios-mas-activos")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerUsuariosMasActivos() {
        try {
            Map<String, Object> usuarios = usuarioService.obtenerUsuariosMasActivos();
            
            return ResponseEntity.ok(ApiResponse.success("Usuarios más activos obtenidos", usuarios));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener usuarios más activos: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener reporte de productividad
     */
    @GetMapping("/reporte-productividad")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerReporteProductividad(
            @RequestParam(required = false) LocalDate fechaInicio,
            @RequestParam(required = false) LocalDate fechaFin) {
        try {
            Map<String, Object> reporte = incidenciaService.obtenerReporteProductividad(fechaInicio, fechaFin);
            
            return ResponseEntity.ok(ApiResponse.success("Reporte de productividad obtenido", reporte));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener reporte de productividad: " + e.getMessage()));
        }
    }
} 