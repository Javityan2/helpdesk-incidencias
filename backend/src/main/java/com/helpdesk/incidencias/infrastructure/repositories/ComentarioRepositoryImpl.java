package com.helpdesk.incidencias.infrastructure.repositories;

import com.helpdesk.incidencias.domain.entities.Comentario;
import com.helpdesk.incidencias.domain.entities.Incidencia;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.TipoComentario;
import com.helpdesk.incidencias.domain.repositories.ComentarioRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de comentarios usando Spring Data JPA
 */
@Repository
public interface ComentarioRepositoryImpl extends JpaRepository<Comentario, Long>, ComentarioRepository {
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia")
    List<Comentario> findByIncidencia(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.usuario = :usuario")
    List<Comentario> findByUsuario(@Param("usuario") Usuario usuario);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.tipo = :tipo")
    List<Comentario> findByTipo(@Param("tipo") TipoComentario tipo);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia AND c.tipo = :tipo")
    List<Comentario> findByIncidenciaAndTipo(@Param("incidencia") Incidencia incidencia, @Param("tipo") TipoComentario tipo);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia AND c.esInterno = false")
    List<Comentario> findByIncidenciaAndEsInternoFalse(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia AND c.esInterno = true")
    List<Comentario> findByIncidenciaAndEsInternoTrue(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia AND c.tipo IN ('TECNICO', 'INTERNO')")
    List<Comentario> findComentariosTecnicos(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia ORDER BY c.fechaCreacion DESC")
    List<Comentario> findByIncidenciaOrderByFechaCreacionDesc(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia ORDER BY c.likes DESC")
    List<Comentario> findByIncidenciaOrderByLikesDesc(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.contenido LIKE %:contenido%")
    List<Comentario> findByContenidoContaining(@Param("contenido") String contenido);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.usuario = :usuario ORDER BY c.fechaCreacion DESC")
    List<Comentario> findComentariosRecientesByUsuario(@Param("usuario") Usuario usuario, Pageable pageable);
    
    @Override
    @Query("SELECT COUNT(c) FROM Comentario c WHERE c.incidencia = :incidencia")
    long countByIncidencia(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT COUNT(c) FROM Comentario c WHERE c.usuario = :usuario")
    long countByUsuario(@Param("usuario") Usuario usuario);
    
    @Override
    @Query("SELECT COUNT(c) FROM Comentario c WHERE c.tipo = :tipo")
    long countByTipo(@Param("tipo") TipoComentario tipo);
    
    @Override
    @Query("SELECT COUNT(c) FROM Comentario c WHERE c.incidencia = :incidencia AND c.esInterno = false")
    long countByIncidenciaAndEsInternoFalse(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT COUNT(c) > 0 FROM Comentario c WHERE c.id = :id")
    boolean existsById(@Param("id") Long id);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia ORDER BY c.fechaCreacion DESC")
    Optional<Comentario> findTopByIncidenciaOrderByFechaCreacionDesc(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia ORDER BY c.likes DESC")
    Optional<Comentario> findTopByIncidenciaOrderByLikesDesc(@Param("incidencia") Incidencia incidencia);
    
    // Método auxiliar para obtener comentarios recientes con límite
    default List<Comentario> findComentariosRecientesByUsuario(Usuario usuario, int limite) {
        return findComentariosRecientesByUsuario(usuario, Pageable.ofSize(limite));
    }
} 