package com.helpdesk.incidencias.infrastructure.repositories;

import com.helpdesk.incidencias.domain.entities.Incidencia;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.EstadoIncidencia;
import com.helpdesk.incidencias.domain.entities.Prioridad;
import com.helpdesk.incidencias.domain.entities.CategoriaIncidencia;
import com.helpdesk.incidencias.domain.repositories.IncidenciaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de incidencias
 */
@Repository
public interface IncidenciaRepositoryImpl extends JpaRepository<Incidencia, Long>, IncidenciaRepository {
    
    // ===================================
    // MÉTODOS DE BÚSQUEDA
    // ===================================
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.usuario = :usuario")
    List<Incidencia> findByUsuario(@Param("usuario") Usuario usuario);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.asignado = :asignado")
    List<Incidencia> findByAsignado(@Param("asignado") Usuario asignado);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.estado = :estado")
    List<Incidencia> findByEstado(@Param("estado") EstadoIncidencia estado);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.prioridad = :prioridad")
    List<Incidencia> findByPrioridad(@Param("prioridad") Prioridad prioridad);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.categoria = :categoria")
    List<Incidencia> findByCategoria(@Param("categoria") CategoriaIncidencia categoria);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.fechaCreacion BETWEEN :inicio AND :fin")
    List<Incidencia> findByFechaCreacionBetween(@Param("inicio") LocalDateTime inicio, 
                                               @Param("fin") LocalDateTime fin);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.usuario.departamento = :departamento")
    List<Incidencia> findByUsuarioDepartamento(@Param("departamento") String departamento);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.titulo LIKE %:texto% OR i.descripcion LIKE %:texto%")
    List<Incidencia> findByTituloContainingOrDescripcionContaining(@Param("texto") String texto);
    
    // ===================================
    // MÉTODOS DE FILTRADO Y PAGINACIÓN
    // ===================================
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE " +
           "(:estado IS NULL OR i.estado = :estado) AND " +
           "(:prioridad IS NULL OR i.prioridad = :prioridad) AND " +
           "(:categoria IS NULL OR i.categoria = :categoria) AND " +
           "(:usuarioId IS NULL OR i.usuario.empleadoId = :usuarioId)")
    List<Incidencia> findByFiltros(@Param("estado") EstadoIncidencia estado,
                                   @Param("prioridad") Prioridad prioridad,
                                   @Param("categoria") CategoriaIncidencia categoria,
                                   @Param("usuarioId") String usuarioId);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE " +
           "(:estado IS NULL OR i.estado = :estado) AND " +
           "(:prioridad IS NULL OR i.prioridad = :prioridad) AND " +
           "(:categoria IS NULL OR i.categoria = :categoria) AND " +
           "(:usuarioId IS NULL OR i.usuario.empleadoId = :usuarioId)")
    Page<Incidencia> findByFiltrosPaginados(@Param("estado") EstadoIncidencia estado,
                                            @Param("prioridad") Prioridad prioridad,
                                            @Param("categoria") CategoriaIncidencia categoria,
                                            @Param("usuarioId") String usuarioId,
                                            Pageable pageable);
    
    @Override
    @Query("SELECT i FROM Incidencia i ORDER BY i.prioridad DESC, i.fechaCreacion DESC")
    List<Incidencia> findIncidenciasOrdenadasPorPrioridad();
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.estado IN :estados")
    List<Incidencia> findIncidenciasActivas(@Param("estados") List<EstadoIncidencia> estados);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.prioridad = :prioridad AND i.estado IN :estados")
    List<Incidencia> findIncidenciasCriticas(@Param("estados") List<EstadoIncidencia> estados, 
                                            @Param("prioridad") Prioridad prioridad);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.asignado IS NULL")
    List<Incidencia> findIncidenciasSinAsignar();
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.asignado = :tecnico")
    List<Incidencia> findIncidenciasAsignadasATecnico(@Param("tecnico") Usuario tecnico);
    
    @Override
    @Query("SELECT i FROM Incidencia i ORDER BY (SELECT COUNT(c) FROM Comentario c WHERE c.incidencia = i) DESC, i.fechaCreacion DESC")
    List<Incidencia> findIncidenciasPopulares(@Param("limit") int limit);
    
    // ===================================
    // MÉTODOS DE CONTEO
    // ===================================
    
    @Override
    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.estado = :estado")
    long countByEstado(@Param("estado") EstadoIncidencia estado);
    
    @Override
    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.prioridad = :prioridad")
    long countByPrioridad(@Param("prioridad") Prioridad prioridad);
    
    @Override
    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.usuario = :usuario")
    long countByUsuario(@Param("usuario") Usuario usuario);
    
    @Override
    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.estado IN :estados")
    long countIncidenciasActivas(@Param("estados") List<EstadoIncidencia> estados);
    
    @Override
    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.prioridad = :prioridad AND i.estado IN :estados")
    long countIncidenciasCriticas(@Param("estados") List<EstadoIncidencia> estados, 
                                 @Param("prioridad") Prioridad prioridad);
    
    @Override
    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.fechaCreacion BETWEEN :inicio AND :fin")
    long countByFechaCreacionBetween(@Param("inicio") LocalDateTime inicio, 
                                    @Param("fin") LocalDateTime fin);
    
    @Override
    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.fechaResolucion BETWEEN :inicio AND :fin")
    long countByFechaResolucionBetween(@Param("inicio") LocalDateTime inicio, 
                                      @Param("fin") LocalDateTime fin);
    
    // ===================================
    // MÉTODOS DE ESTADÍSTICAS
    // ===================================
    
    @Override
    @Query("SELECT i.usuario.departamento, COUNT(i) FROM Incidencia i GROUP BY i.usuario.departamento")
    List<Object[]> findEstadisticasPorDepartamento();
    
    @Override
    @Query("SELECT i.asignado.nombre || ' ' || i.asignado.apellido, COUNT(i) " +
           "FROM Incidencia i WHERE i.asignado IS NOT NULL GROUP BY i.asignado")
    List<Object[]> findEstadisticasPorTecnico();
    
    @Override
    @Query("SELECT i.categoria, COUNT(i) FROM Incidencia i GROUP BY i.categoria")
    List<Object[]> findEstadisticasPorCategoria();
    
    @Override
    @Query("SELECT CAST(i.fechaCreacion AS date), COUNT(i) FROM Incidencia i " +
           "WHERE i.fechaCreacion BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY CAST(i.fechaCreacion AS date) ORDER BY CAST(i.fechaCreacion AS date)")
    List<Object[]> findTendenciasPorFecha(@Param("fechaInicio") LocalDate fechaInicio,
                                         @Param("fechaFin") LocalDate fechaFin);
    
    @Override
    @Query("SELECT AVG(i.tiempoRealHoras) FROM Incidencia i WHERE i.estado = 'RESUELTA' AND i.tiempoRealHoras IS NOT NULL")
    Double findTiempoPromedioResolucion();
    
    // ===================================
    // MÉTODO DE ESTADÍSTICAS GENERALES
    // ===================================
    
    @Override
    default IncidenciaStats getEstadisticas() {
        long totalIncidencias = count();
        long incidenciasAbiertas = countByEstado(EstadoIncidencia.ABIERTA);
        long incidenciasEnProceso = countByEstado(EstadoIncidencia.EN_PROCESO);
        long incidenciasResueltas = countByEstado(EstadoIncidencia.RESUELTA);
        long incidenciasCriticas = countIncidenciasCriticas(List.of(EstadoIncidencia.ABIERTA, EstadoIncidencia.EN_PROCESO), Prioridad.CRITICA);
        
        return new IncidenciaStats(totalIncidencias, incidenciasAbiertas, 
                                 incidenciasEnProceso, incidenciasResueltas, incidenciasCriticas);
    }
} 