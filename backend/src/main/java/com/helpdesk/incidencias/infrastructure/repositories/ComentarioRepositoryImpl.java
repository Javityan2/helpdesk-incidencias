package com.helpdesk.incidencias.infrastructure.repositories;

import com.helpdesk.incidencias.domain.entities.Comentario;
import com.helpdesk.incidencias.domain.entities.Incidencia;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.TipoComentario;
import com.helpdesk.incidencias.domain.repositories.ComentarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de comentarios
 */
@Repository
public interface ComentarioRepositoryImpl extends JpaRepository<Comentario, Long>, ComentarioRepository {
    
    // ===================================
    // MÉTODOS DE BÚSQUEDA
    // ===================================
    
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
    List<Comentario> findByIncidenciaAndTipo(@Param("incidencia") Incidencia incidencia, 
                                            @Param("tipo") TipoComentario tipo);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia AND c.esInterno = false")
    List<Comentario> findByIncidenciaAndEsInternoFalse(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia AND c.esInterno = true")
    List<Comentario> findByIncidenciaAndEsInternoTrue(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia AND c.tipo IN :tipos")
    List<Comentario> findComentariosTecnicos(@Param("incidencia") Incidencia incidencia, 
                                            @Param("tipos") List<TipoComentario> tipos);
    
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
    @Query(value = "SELECT * FROM comentarios WHERE usuario_id = :usuarioId ORDER BY fecha_creacion DESC LIMIT :limite", nativeQuery = true)
    List<Comentario> findComentariosRecientesByUsuario(@Param("usuarioId") Long usuarioId, 
                                                       @Param("limite") int limite);
    
    // ===================================
    // MÉTODOS DE BÚSQUEDA POR ID
    // ===================================
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia.id = :incidenciaId")
    List<Comentario> findByIncidenciaId(@Param("incidenciaId") Long incidenciaId);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia.id = :incidenciaId AND c.tipo = :tipo")
    List<Comentario> findByIncidenciaIdAndTipo(@Param("incidenciaId") Long incidenciaId, 
                                               @Param("tipo") TipoComentario tipo);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia.id = :incidenciaId")
    Page<Comentario> findByIncidenciaId(@Param("incidenciaId") Long incidenciaId, Pageable pageable);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia.id = :incidenciaId AND c.tipo = :tipo")
    Page<Comentario> findByIncidenciaIdAndTipo(@Param("incidenciaId") Long incidenciaId, 
                                               @Param("tipo") TipoComentario tipo, 
                                               Pageable pageable);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.usuario.id = :usuarioId")
    List<Comentario> findByUsuarioId(@Param("usuarioId") Long usuarioId);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia.id = :incidenciaId AND c.tipo IN :tipos")
    List<Comentario> findByIncidenciaIdAndTipoIn(@Param("incidenciaId") Long incidenciaId, 
                                                 @Param("tipos") List<TipoComentario> tipos);
    
    // ===================================
    // MÉTODOS DE CONTEO
    // ===================================
    
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
    @Query("SELECT COUNT(c) FROM Comentario c WHERE c.incidencia.id = :incidenciaId")
    long countByIncidenciaId(@Param("incidenciaId") Long incidenciaId);
    
    @Override
    @Query("SELECT COUNT(c) FROM Comentario c WHERE c.incidencia.id = :incidenciaId AND c.tipo = :tipo")
    long countByIncidenciaIdAndTipo(@Param("incidenciaId") Long incidenciaId, 
                                   @Param("tipo") TipoComentario tipo);
    
    // ===================================
    // MÉTODOS DE ELIMINACIÓN
    // ===================================
    
    @Override
    @Query("DELETE FROM Comentario c WHERE c.incidencia = :incidencia")
    void deleteByIncidencia(@Param("incidencia") Incidencia incidencia);
    
    // ===================================
    // MÉTODOS DE BÚSQUEDA ESPECIALIZADA
    // ===================================
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia ORDER BY c.fechaCreacion DESC")
    Optional<Comentario> findTopByIncidenciaOrderByFechaCreacionDesc(@Param("incidencia") Incidencia incidencia);
    
    @Override
    @Query("SELECT c FROM Comentario c WHERE c.incidencia = :incidencia ORDER BY c.likes DESC")
    Optional<Comentario> findTopByIncidenciaOrderByLikesDesc(@Param("incidencia") Incidencia incidencia);
} 