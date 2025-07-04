package com.helpdesk.incidencias.domain.repositories;

import com.helpdesk.incidencias.domain.entities.Comentario;
import com.helpdesk.incidencias.domain.entities.Incidencia;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.TipoComentario;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de comentarios
 */
public interface ComentarioRepository {
    
    /**
     * Guarda un comentario
     */
    Comentario save(Comentario comentario);
    
    /**
     * Busca un comentario por ID
     */
    Optional<Comentario> findById(Long id);
    
    /**
     * Obtiene todos los comentarios de una incidencia
     */
    List<Comentario> findByIncidencia(Incidencia incidencia);
    
    /**
     * Obtiene comentarios por usuario
     */
    List<Comentario> findByUsuario(Usuario usuario);
    
    /**
     * Obtiene comentarios por tipo
     */
    List<Comentario> findByTipo(TipoComentario tipo);
    
    /**
     * Obtiene comentarios por incidencia y tipo
     */
    List<Comentario> findByIncidenciaAndTipo(Incidencia incidencia, TipoComentario tipo);
    
    /**
     * Obtiene comentarios públicos (no internos)
     */
    List<Comentario> findByIncidenciaAndEsInternoFalse(Incidencia incidencia);
    
    /**
     * Obtiene comentarios internos
     */
    List<Comentario> findByIncidenciaAndEsInternoTrue(Incidencia incidencia);
    
    /**
     * Obtiene comentarios técnicos
     */
    List<Comentario> findComentariosTecnicos(Incidencia incidencia);
    
    /**
     * Obtiene comentarios ordenados por fecha de creación (más recientes primero)
     */
    List<Comentario> findByIncidenciaOrderByFechaCreacionDesc(Incidencia incidencia);
    
    /**
     * Obtiene comentarios ordenados por puntuación (mejor puntuación primero)
     */
    List<Comentario> findByIncidenciaOrderByLikesDesc(Incidencia incidencia);
    
    /**
     * Busca comentarios por contenido (búsqueda de texto)
     */
    List<Comentario> findByContenidoContaining(String contenido);
    
    /**
     * Obtiene comentarios recientes de un usuario
     */
    List<Comentario> findComentariosRecientesByUsuario(Usuario usuario, int limite);
    
    /**
     * Cuenta comentarios por incidencia
     */
    long countByIncidencia(Incidencia incidencia);
    
    /**
     * Cuenta comentarios por usuario
     */
    long countByUsuario(Usuario usuario);
    
    /**
     * Cuenta comentarios por tipo
     */
    long countByTipo(TipoComentario tipo);
    
    /**
     * Cuenta comentarios públicos por incidencia
     */
    long countByIncidenciaAndEsInternoFalse(Incidencia incidencia);
    
    /**
     * Elimina un comentario por ID
     */
    void deleteById(Long id);
    
    /**
     * Elimina todos los comentarios de una incidencia
     */
    void deleteByIncidencia(Incidencia incidencia);
    
    /**
     * Verifica si existe un comentario con el ID dado
     */
    boolean existsById(Long id);
    
    /**
     * Obtiene el comentario más reciente de una incidencia
     */
    Optional<Comentario> findTopByIncidenciaOrderByFechaCreacionDesc(Incidencia incidencia);
    
    /**
     * Obtiene el comentario con mejor puntuación de una incidencia
     */
    Optional<Comentario> findTopByIncidenciaOrderByLikesDesc(Incidencia incidencia);
} 