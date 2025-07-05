package com.helpdesk.incidencias.infrastructure.controllers;

import com.helpdesk.incidencias.application.dtos.ApiResponse;
import com.helpdesk.incidencias.application.dtos.ComentarioDTO;
import com.helpdesk.incidencias.application.dtos.ComentarioCreateRequest;
import com.helpdesk.incidencias.application.dtos.ComentarioUpdateRequest;
import com.helpdesk.incidencias.application.services.ComentarioService;
import com.helpdesk.incidencias.domain.entities.TipoComentario;
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
 * Controlador REST para gestión de comentarios colaborativos
 */
@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "http://localhost:4200")
public class ComentarioController {
    
    @Autowired
    private ComentarioService comentarioService;
    
    /**
     * Obtener comentarios de una incidencia
     */
    @GetMapping("/incidencia/{incidenciaId}")
    public ResponseEntity<ApiResponse<List<ComentarioDTO>>> obtenerComentariosPorIncidencia(
            @PathVariable Long incidenciaId,
            @RequestParam(required = false) TipoComentario tipo) {
        
        try {
            List<ComentarioDTO> comentarios = comentarioService.obtenerComentariosPorIncidencia(incidenciaId, tipo);
            
            return ResponseEntity.ok(ApiResponse.success("Comentarios obtenidos", comentarios));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener comentarios: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener comentarios con paginación
     */
    @GetMapping("/incidencia/{incidenciaId}/paginated")
    public ResponseEntity<ApiResponse<Page<ComentarioDTO>>> obtenerComentariosPaginados(
            @PathVariable Long incidenciaId,
            @RequestParam(required = false) TipoComentario tipo,
            Pageable pageable) {
        
        try {
            Page<ComentarioDTO> comentarios = comentarioService.obtenerComentariosPaginados(incidenciaId, tipo, pageable);
            
            return ResponseEntity.ok(ApiResponse.success("Comentarios obtenidos", comentarios));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener comentarios: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener comentario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ComentarioDTO>> obtenerComentario(@PathVariable Long id) {
        try {
            Optional<ComentarioDTO> comentario = comentarioService.obtenerComentarioPorId(id);
            
            if (comentario.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Comentario encontrado", comentario.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Comentario no encontrado"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener comentario: " + e.getMessage()));
        }
    }
    
    /**
     * Crear nuevo comentario
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ComentarioDTO>> crearComentario(
            @Valid @RequestBody ComentarioCreateRequest request) {
        try {
            ComentarioDTO comentarioCreado = comentarioService.crearComentario(request);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Comentario creado exitosamente", comentarioCreado));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al crear comentario: " + e.getMessage()));
        }
    }
    
    /**
     * Actualizar comentario
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ComentarioDTO>> actualizarComentario(
            @PathVariable Long id,
            @Valid @RequestBody ComentarioUpdateRequest request) {
        try {
            ComentarioDTO comentarioActualizado = comentarioService.actualizarComentario(id, request);
            
            return ResponseEntity.ok(ApiResponse.success("Comentario actualizado", comentarioActualizado));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al actualizar comentario: " + e.getMessage()));
        }
    }
    
    /**
     * Eliminar comentario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminarComentario(@PathVariable Long id) {
        try {
            comentarioService.eliminarComentario(id);
            
            return ResponseEntity.ok(ApiResponse.success("Comentario eliminado"));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al eliminar comentario: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener comentarios por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<ApiResponse<List<ComentarioDTO>>> obtenerComentariosPorTipo(
            @PathVariable TipoComentario tipo) {
        try {
            List<ComentarioDTO> comentarios = comentarioService.obtenerComentariosPorTipoDTO(tipo);
            
            return ResponseEntity.ok(ApiResponse.success("Comentarios obtenidos", comentarios));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener comentarios: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener comentarios de un usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ApiResponse<List<ComentarioDTO>>> obtenerComentariosPorUsuario(
            @PathVariable Long usuarioId) {
        try {
            List<ComentarioDTO> comentarios = comentarioService.obtenerComentariosPorUsuario(usuarioId);
            
            return ResponseEntity.ok(ApiResponse.success("Comentarios obtenidos", comentarios));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener comentarios: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener comentarios internos (solo para técnicos)
     */
    @GetMapping("/internos/incidencia/{incidenciaId}")
    public ResponseEntity<ApiResponse<List<ComentarioDTO>>> obtenerComentariosInternos(
            @PathVariable Long incidenciaId) {
        try {
            List<ComentarioDTO> comentarios = comentarioService.obtenerComentariosInternos(incidenciaId);
            
            return ResponseEntity.ok(ApiResponse.success("Comentarios internos obtenidos", comentarios));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener comentarios internos: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener comentarios técnicos
     */
    @GetMapping("/tecnicos/incidencia/{incidenciaId}")
    public ResponseEntity<ApiResponse<List<ComentarioDTO>>> obtenerComentariosTecnicos(
            @PathVariable Long incidenciaId) {
        try {
            List<ComentarioDTO> comentarios = comentarioService.obtenerComentariosTecnicos(incidenciaId);
            
            return ResponseEntity.ok(ApiResponse.success("Comentarios técnicos obtenidos", comentarios));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener comentarios técnicos: " + e.getMessage()));
        }
    }
    
    /**
     * Obtener estadísticas de comentarios
     */
    @GetMapping("/estadisticas/incidencia/{incidenciaId}")
    public ResponseEntity<ApiResponse<Object>> obtenerEstadisticasComentarios(
            @PathVariable Long incidenciaId) {
        try {
            Object estadisticas = comentarioService.obtenerEstadisticasComentarios(incidenciaId);
            
            return ResponseEntity.ok(ApiResponse.success("Estadísticas obtenidas", estadisticas));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error al obtener estadísticas: " + e.getMessage()));
        }
    }
} 