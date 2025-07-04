package com.helpdesk.incidencias.infrastructure.repositories;

import com.helpdesk.incidencias.domain.entities.Incidencia;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.EstadoIncidencia;
import com.helpdesk.incidencias.domain.entities.Prioridad;
import com.helpdesk.incidencias.domain.entities.CategoriaIncidencia;
import com.helpdesk.incidencias.domain.repositories.IncidenciaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de incidencias usando Spring Data JPA
 */
@Repository
public interface IncidenciaRepositoryImpl extends JpaRepository<Incidencia, Long>, IncidenciaRepository {
    
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
    @Query("SELECT i FROM Incidencia i WHERE i.estado NOT IN ('RESUELTA', 'CERRADA', 'CANCELADA')")
    List<Incidencia> findIncidenciasActivas();
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.prioridad = 'CRITICA'")
    List<Incidencia> findIncidenciasCriticas();
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.fechaCreacion BETWEEN :inicio AND :fin")
    List<Incidencia> findByFechaCreacionBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.usuario.departamento = :departamento")
    List<Incidencia> findByUsuarioDepartamento(@Param("departamento") String departamento);
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.titulo LIKE %:texto% OR i.descripcion LIKE %:texto%")
    List<Incidencia> findByTituloContainingOrDescripcionContaining(@Param("texto") String titulo, @Param("texto") String descripcion);
    
    @Override
    @Query("SELECT i FROM Incidencia i ORDER BY i.prioridad DESC, i.fechaCreacion ASC")
    List<Incidencia> findIncidenciasOrdenadasPorPrioridad();
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.asignado IS NULL")
    List<Incidencia> findIncidenciasSinAsignar();
    
    @Override
    @Query("SELECT i FROM Incidencia i WHERE i.asignado = :tecnico")
    List<Incidencia> findIncidenciasAsignadasATecnico(@Param("tecnico") Usuario tecnico);
    
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
    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.estado NOT IN ('RESUELTA', 'CERRADA', 'CANCELADA')")
    long countIncidenciasActivas();
    
    @Override
    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.prioridad = 'CRITICA'")
    long countIncidenciasCriticas();
    
    @Override
    @Query("SELECT COUNT(i) > 0 FROM Incidencia i WHERE i.id = :id")
    boolean existsById(@Param("id") Long id);
    
    @Override
    @Query("SELECT new com.helpdesk.incidencias.domain.repositories.IncidenciaRepository$IncidenciaStats(" +
           "COUNT(i), " +
           "SUM(CASE WHEN i.estado = 'ABIERTA' THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN i.estado = 'EN_PROCESO' THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN i.estado = 'RESUELTA' THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN i.prioridad = 'CRITICA' THEN 1 ELSE 0 END)) " +
           "FROM Incidencia i")
    IncidenciaRepository.IncidenciaStats getEstadisticas();
} 