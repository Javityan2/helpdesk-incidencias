package com.helpdesk.incidencias.infrastructure.controllers;

import com.helpdesk.incidencias.application.dtos.ApiResponse;
import com.helpdesk.incidencias.application.dtos.IncidenciaDTO;
import com.helpdesk.incidencias.application.dtos.IncidenciaCreateRequest;
import com.helpdesk.incidencias.application.dtos.IncidenciaUpdateRequest;
import com.helpdesk.incidencias.application.services.IncidenciaService;
import com.helpdesk.incidencias.domain.entities.Incidencia;
import com.helpdesk.incidencias.domain.entities.EstadoIncidencia;
import com.helpdesk.incidencias.domain.entities.Prioridad;
import com.helpdesk.incidencias.domain.entities.CategoriaIncidencia;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestión de incidencias
 */
@RestController
@RequestMapping("/api/incidencias")
@CrossOrigin(origins = "http://localhost:4200")
public class IncidenciaController {
    
    @Autowired
    private IncidenciaService incidenciaService;
    
    /**
     * Obtener todas las incidencias ordenadas por prioridad y popularidad
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<IncidenciaDTO>>> obtenerIncidencias(
            @RequestParam(required = false) EstadoIncidencia estado,
            @RequestParam(required = false) Prioridad prioridad,
            @RequestParam(required = false) CategoriaIncidencia categoria,
            @RequestParam(required = false) String usuarioId) {
        
        try {
            List<IncidenciaDTO> incidencias = incidenciaService.obtenerIncidenciasFiltradas(
                estado, prioridad, categoria, usuarioId);
            
            return ResponseEntity.ok(ApiResponse.success("Incidencias obtenidas", incidencias));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener incidencias: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener incidencias con paginación
     */
    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<IncidenciaDTO>>> obtenerIncidenciasPaginadas(
            @RequestParam(required = false) EstadoIncidencia estado,
            @RequestParam(required = false) Prioridad prioridad,
            @RequestParam(required = false) CategoriaIncidencia categoria,
            @RequestParam(required = false) String usuarioId,
            Pageable pageable) {
        
        try {
            Page<IncidenciaDTO> incidencias = incidenciaService.obtenerIncidenciasPaginadas(
                estado, prioridad, categoria, usuarioId, pageable);
            
            return ResponseEntity.ok(ApiResponse.success("Incidencias obtenidas", incidencias));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener incidencias: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener incidencia por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<IncidenciaDTO>> obtenerIncidencia(@PathVariable Long id) {
        try {
            Optional<IncidenciaDTO> incidencia = incidenciaService.obtenerIncidenciaPorId(id);
            
            if (incidencia.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Incidencia encontrada", incidencia.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Incidencia no encontrada"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener incidencia: " + e.getMessage()));
        }
    }
    
    /**
     * Crear nueva incidencia
     */
    @PostMapping
    public ResponseEntity<ApiResponse<IncidenciaDTO>> crearIncidencia(
            @Valid @RequestBody IncidenciaCreateRequest request) {
        try {
            IncidenciaDTO incidenciaCreada = incidenciaService.crearIncidencia(request);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Incidencia creada exitosamente", incidenciaCreada));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al crear incidencia: " + e.getMessage()));
        }
    }
    
    /**
     * Actualizar incidencia
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<IncidenciaDTO>> actualizarIncidencia(
            @PathVariable Long id,
            @Valid @RequestBody IncidenciaUpdateRequest request) {
        try {
            IncidenciaDTO incidenciaActualizada = incidenciaService.actualizarIncidencia(id, request);
            
            return ResponseEntity.ok(ApiResponse.success("Incidencia actualizada", incidenciaActualizada));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al actualizar incidencia: " + e.getMessage()));
        }
    }
    
    /**
     * Cambiar estado de incidencia
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<IncidenciaDTO>> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoIncidencia nuevoEstado) {
        try {
            IncidenciaDTO incidenciaActualizada = incidenciaService.cambiarEstadoIncidencia(id, nuevoEstado);
            
            return ResponseEntity.ok(ApiResponse.success("Estado actualizado", incidenciaActualizada));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al cambiar estado: " + e.getMessage()));
        }
    }
    
    /**
     * Asignar técnico a incidencia
     */
    @PatchMapping("/{id}/asignar")
    public ResponseEntity<ApiResponse<IncidenciaDTO>> asignarTecnico(
            @PathVariable Long id,
            @RequestParam Long tecnicoId) {
        try {
            IncidenciaDTO incidenciaActualizada = incidenciaService.asignarTecnico(id, tecnicoId);
            
            return ResponseEntity.ok(ApiResponse.success("Técnico asignado", incidenciaActualizada));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al asignar técnico: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener incidencias más populares (por número de comentarios)
     */
    @GetMapping("/populares")
    public ResponseEntity<ApiResponse<List<IncidenciaDTO>>> obtenerIncidenciasPopulares(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<IncidenciaDTO> incidencias = incidenciaService.obtenerIncidenciasPopulares(limit);
            
            return ResponseEntity.ok(ApiResponse.success("Incidencias populares obtenidas", incidencias));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener incidencias populares: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener estadísticas de incidencias
     */
    @GetMapping("/estadisticas")
    public ResponseEntity<ApiResponse<Object>> obtenerEstadisticas() {
        try {
            Object estadisticas = incidenciaService.obtenerEstadisticas();
            
            return ResponseEntity.ok(ApiResponse.success("Estadísticas obtenidas", estadisticas));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener estadísticas: " + e.getMessage()));
        }
    }
    
    /**
     * Eliminar incidencia (solo admin)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminarIncidencia(@PathVariable Long id) {
        try {
            incidenciaService.eliminarIncidencia(id);
            
            return ResponseEntity.ok(ApiResponse.success("Incidencia eliminada"));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al eliminar incidencia: " + e.getMessage()));
        }
    }
} 