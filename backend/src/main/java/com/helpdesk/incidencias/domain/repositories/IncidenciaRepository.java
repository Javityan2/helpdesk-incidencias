package com.helpdesk.incidencias.domain.repositories;

import com.helpdesk.incidencias.domain.entities.Incidencia;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.EstadoIncidencia;
import com.helpdesk.incidencias.domain.entities.Prioridad;
import com.helpdesk.incidencias.domain.entities.CategoriaIncidencia;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de incidencias
 */
public interface IncidenciaRepository {
    
    /**
     * Guarda una incidencia
     */
    Incidencia save(Incidencia incidencia);
    
    /**
     * Busca una incidencia por ID
     */
    Optional<Incidencia> findById(Long id);
    
    /**
     * Obtiene todas las incidencias
     */
    List<Incidencia> findAll();
    
    /**
     * Obtiene incidencias por usuario creador
     */
    List<Incidencia> findByUsuario(Usuario usuario);
    
    /**
     * Obtiene incidencias por usuario asignado
     */
    List<Incidencia> findByAsignado(Usuario asignado);
    
    /**
     * Obtiene incidencias por estado
     */
    List<Incidencia> findByEstado(EstadoIncidencia estado);
    
    /**
     * Obtiene incidencias por prioridad
     */
    List<Incidencia> findByPrioridad(Prioridad prioridad);
    
    /**
     * Obtiene incidencias por categoría
     */
    List<Incidencia> findByCategoria(CategoriaIncidencia categoria);
    
    /**
     * Obtiene incidencias activas (no resueltas)
     */
    List<Incidencia> findIncidenciasActivas();
    
    /**
     * Obtiene incidencias críticas
     */
    List<Incidencia> findIncidenciasCriticas();
    
    /**
     * Obtiene incidencias por fecha de creación
     */
    List<Incidencia> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);
    
    /**
     * Obtiene incidencias por departamento del usuario
     */
    List<Incidencia> findByUsuarioDepartamento(String departamento);
    
    /**
     * Busca incidencias por título o descripción (búsqueda de texto)
     */
    List<Incidencia> findByTituloContainingOrDescripcionContaining(String titulo, String descripcion);
    
    /**
     * Obtiene incidencias ordenadas por prioridad y fecha de creación
     */
    List<Incidencia> findIncidenciasOrdenadasPorPrioridad();
    
    /**
     * Obtiene incidencias sin asignar
     */
    List<Incidencia> findIncidenciasSinAsignar();
    
    /**
     * Obtiene incidencias asignadas a un técnico específico
     */
    List<Incidencia> findIncidenciasAsignadasATecnico(Usuario tecnico);
    
    /**
     * Cuenta incidencias por estado
     */
    long countByEstado(EstadoIncidencia estado);
    
    /**
     * Cuenta incidencias por prioridad
     */
    long countByPrioridad(Prioridad prioridad);
    
    /**
     * Cuenta incidencias por usuario
     */
    long countByUsuario(Usuario usuario);
    
    /**
     * Cuenta incidencias activas
     */
    long countIncidenciasActivas();
    
    /**
     * Cuenta incidencias críticas
     */
    long countIncidenciasCriticas();
    
    /**
     * Elimina una incidencia por ID
     */
    void deleteById(Long id);
    
    /**
     * Verifica si existe una incidencia con el ID dado
     */
    boolean existsById(Long id);
    
    /**
     * Obtiene estadísticas de incidencias
     */
    IncidenciaStats getEstadisticas();
    
    /**
     * Clase interna para estadísticas
     */
    class IncidenciaStats {
        private final long totalIncidencias;
        private final long incidenciasAbiertas;
        private final long incidenciasEnProceso;
        private final long incidenciasResueltas;
        private final long incidenciasCriticas;
        
        public IncidenciaStats(long totalIncidencias, long incidenciasAbiertas, 
                              long incidenciasEnProceso, long incidenciasResueltas, 
                              long incidenciasCriticas) {
            this.totalIncidencias = totalIncidencias;
            this.incidenciasAbiertas = incidenciasAbiertas;
            this.incidenciasEnProceso = incidenciasEnProceso;
            this.incidenciasResueltas = incidenciasResueltas;
            this.incidenciasCriticas = incidenciasCriticas;
        }
        
        // Getters
        public long getTotalIncidencias() { return totalIncidencias; }
        public long getIncidenciasAbiertas() { return incidenciasAbiertas; }
        public long getIncidenciasEnProceso() { return incidenciasEnProceso; }
        public long getIncidenciasResueltas() { return incidenciasResueltas; }
        public long getIncidenciasCriticas() { return incidenciasCriticas; }
    }
} 