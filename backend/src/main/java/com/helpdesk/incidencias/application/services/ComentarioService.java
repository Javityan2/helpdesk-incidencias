package com.helpdesk.incidencias.application.services;

import com.helpdesk.incidencias.domain.entities.Comentario;
import com.helpdesk.incidencias.domain.entities.Incidencia;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.TipoComentario;
import com.helpdesk.incidencias.domain.repositories.ComentarioRepository;
import com.helpdesk.incidencias.domain.repositories.IncidenciaRepository;
import com.helpdesk.incidencias.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación para la gestión de comentarios
 */
@Service
@Transactional
public class ComentarioService {
    
    @Autowired
    private ComentarioRepository comentarioRepository;
    
    @Autowired
    private IncidenciaRepository incidenciaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /**
     * Crea un nuevo comentario
     */
    public Comentario crearComentario(Comentario comentario) {
        // Validar que la incidencia existe
        if (comentario.getIncidencia() == null || comentario.getIncidencia().getId() == null) {
            throw new IllegalArgumentException("La incidencia es obligatoria");
        }
        
        Optional<Incidencia> incidenciaOpt = incidenciaRepository.findById(comentario.getIncidencia().getId());
        if (incidenciaOpt.isEmpty()) {
            throw new IllegalArgumentException("Incidencia no encontrada");
        }
        
        // Validar que el usuario existe y está activo
        if (comentario.getUsuario() == null || comentario.getUsuario().getId() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(comentario.getUsuario().getId());
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        if (!usuario.getActivo()) {
            throw new IllegalArgumentException("El usuario debe estar activo");
        }
        
        // Establecer valores por defecto
        comentario.setIncidencia(incidenciaOpt.get());
        comentario.setUsuario(usuario);
        comentario.setFechaCreacion(LocalDateTime.now());
        
        // Si es comentario interno, verificar permisos
        if (comentario.getEsInterno() && !usuario.getRol().esTecnico()) {
            throw new IllegalArgumentException("Solo los técnicos pueden crear comentarios internos");
        }
        
        return comentarioRepository.save(comentario);
    }
    
    /**
     * Actualiza un comentario existente
     */
    public Comentario actualizarComentario(Long id, Comentario comentarioActualizado) {
        Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
        if (comentarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Comentario no encontrado con ID: " + id);
        }
        
        Comentario comentario = comentarioOpt.get();
        
        // Solo el autor puede editar el comentario
        if (!comentario.getUsuario().getId().equals(comentarioActualizado.getUsuario().getId())) {
            throw new IllegalArgumentException("Solo el autor puede editar el comentario");
        }
        
        // Actualizar contenido
        if (comentarioActualizado.getContenido() != null) {
            comentario.setContenido(comentarioActualizado.getContenido());
        }
        
        // Actualizar tipo si es necesario
        if (comentarioActualizado.getTipo() != null) {
            comentario.setTipo(comentarioActualizado.getTipo());
        }
        
        comentario.setFechaActualizacion(LocalDateTime.now());
        
        return comentarioRepository.save(comentario);
    }
    
    /**
     * Agrega un like a un comentario
     */
    public void agregarLike(Long comentarioId, Usuario usuario) {
        Optional<Comentario> comentarioOpt = comentarioRepository.findById(comentarioId);
        if (comentarioOpt.isPresent()) {
            Comentario comentario = comentarioOpt.get();
            comentario.incrementarLikes();
            comentarioRepository.save(comentario);
        }
    }
    
    /**
     * Agrega un dislike a un comentario
     */
    public void agregarDislike(Long comentarioId, Usuario usuario) {
        Optional<Comentario> comentarioOpt = comentarioRepository.findById(comentarioId);
        if (comentarioOpt.isPresent()) {
            Comentario comentario = comentarioOpt.get();
            comentario.incrementarDislikes();
            comentarioRepository.save(comentario);
        }
    }
    
    /**
     * Obtiene un comentario por ID
     */
    @Transactional(readOnly = true)
    public Optional<Comentario> obtenerComentario(Long id) {
        return comentarioRepository.findById(id);
    }
    
    /**
     * Obtiene todos los comentarios de una incidencia
     */
    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosPorIncidencia(Incidencia incidencia) {
        return comentarioRepository.findByIncidencia(incidencia);
    }
    
    /**
     * Obtiene comentarios públicos de una incidencia
     */
    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosPublicosPorIncidencia(Incidencia incidencia) {
        return comentarioRepository.findByIncidenciaAndEsInternoFalse(incidencia);
    }
    
    /**
     * Obtiene comentarios internos de una incidencia (solo para técnicos)
     */
    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosInternosPorIncidencia(Incidencia incidencia) {
        return comentarioRepository.findByIncidenciaAndEsInternoTrue(incidencia);
    }
    
    /**
     * Obtiene comentarios técnicos de una incidencia
     */
    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosTecnicosPorIncidencia(Incidencia incidencia) {
        return comentarioRepository.findComentariosTecnicos(incidencia);
    }
    
    /**
     * Obtiene comentarios por usuario
     */
    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosPorUsuario(Usuario usuario) {
        return comentarioRepository.findByUsuario(usuario);
    }
    
    /**
     * Obtiene comentarios por tipo
     */
    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosPorTipo(TipoComentario tipo) {
        return comentarioRepository.findByTipo(tipo);
    }
    
    /**
     * Obtiene comentarios ordenados por fecha (más recientes primero)
     */
    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosOrdenadosPorFecha(Incidencia incidencia) {
        return comentarioRepository.findByIncidenciaOrderByFechaCreacionDesc(incidencia);
    }
    
    /**
     * Obtiene comentarios ordenados por puntuación
     */
    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosOrdenadosPorPuntuacion(Incidencia incidencia) {
        return comentarioRepository.findByIncidenciaOrderByLikesDesc(incidencia);
    }
    
    /**
     * Busca comentarios por contenido
     */
    @Transactional(readOnly = true)
    public List<Comentario> buscarComentarios(String contenido) {
        return comentarioRepository.findByContenidoContaining(contenido);
    }
    
    /**
     * Obtiene comentarios recientes de un usuario
     */
    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosRecientesPorUsuario(Usuario usuario, int limite) {
        return comentarioRepository.findComentariosRecientesByUsuario(usuario, limite);
    }
    
    /**
     * Obtiene el comentario más reciente de una incidencia
     */
    @Transactional(readOnly = true)
    public Optional<Comentario> obtenerComentarioMasReciente(Incidencia incidencia) {
        return comentarioRepository.findTopByIncidenciaOrderByFechaCreacionDesc(incidencia);
    }
    
    /**
     * Obtiene el comentario con mejor puntuación de una incidencia
     */
    @Transactional(readOnly = true)
    public Optional<Comentario> obtenerComentarioMejorPuntuado(Incidencia incidencia) {
        return comentarioRepository.findTopByIncidenciaOrderByLikesDesc(incidencia);
    }
    
    /**
     * Elimina un comentario
     */
    public void eliminarComentario(Long id, Usuario usuario) {
        Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
        if (comentarioOpt.isPresent()) {
            Comentario comentario = comentarioOpt.get();
            
            // Solo el autor o un administrador puede eliminar el comentario
            if (!comentario.getUsuario().getId().equals(usuario.getId()) && 
                !usuario.getRol().esAdmin()) {
                throw new IllegalArgumentException("No tienes permisos para eliminar este comentario");
            }
            
            comentarioRepository.deleteById(id);
        }
    }
    
    /**
     * Cuenta comentarios por incidencia
     */
    @Transactional(readOnly = true)
    public long contarComentariosPorIncidencia(Incidencia incidencia) {
        return comentarioRepository.countByIncidencia(incidencia);
    }
    
    /**
     * Cuenta comentarios por usuario
     */
    @Transactional(readOnly = true)
    public long contarComentariosPorUsuario(Usuario usuario) {
        return comentarioRepository.countByUsuario(usuario);
    }
    
    /**
     * Cuenta comentarios por tipo
     */
    @Transactional(readOnly = true)
    public long contarComentariosPorTipo(TipoComentario tipo) {
        return comentarioRepository.countByTipo(tipo);
    }
    
    /**
     * Cuenta comentarios públicos por incidencia
     */
    @Transactional(readOnly = true)
    public long contarComentariosPublicosPorIncidencia(Incidencia incidencia) {
        return comentarioRepository.countByIncidenciaAndEsInternoFalse(incidencia);
    }
    
    /**
     * Verifica si un comentario existe
     */
    @Transactional(readOnly = true)
    public boolean existeComentario(Long id) {
        return comentarioRepository.existsById(id);
    }
    
    /**
     * Obtiene estadísticas de comentarios
     */
    @Transactional(readOnly = true)
    public ComentarioStats obtenerEstadisticas() {
        long totalComentarios = comentarioRepository.count();
        long comentariosGenerales = comentarioRepository.countByTipo(TipoComentario.GENERAL);
        long comentariosTecnicos = comentarioRepository.countByTipo(TipoComentario.TECNICO);
        long comentariosInternos = comentarioRepository.countByTipo(TipoComentario.INTERNO);
        long comentariosSolucion = comentarioRepository.countByTipo(TipoComentario.SOLUCION);
        
        return new ComentarioStats(totalComentarios, comentariosGenerales, 
                                 comentariosTecnicos, comentariosInternos, comentariosSolucion);
    }
    
    /**
     * Clase para estadísticas de comentarios
     */
    public static class ComentarioStats {
        private final long totalComentarios;
        private final long comentariosGenerales;
        private final long comentariosTecnicos;
        private final long comentariosInternos;
        private final long comentariosSolucion;
        
        public ComentarioStats(long totalComentarios, long comentariosGenerales, 
                              long comentariosTecnicos, long comentariosInternos, 
                              long comentariosSolucion) {
            this.totalComentarios = totalComentarios;
            this.comentariosGenerales = comentariosGenerales;
            this.comentariosTecnicos = comentariosTecnicos;
            this.comentariosInternos = comentariosInternos;
            this.comentariosSolucion = comentariosSolucion;
        }
        
        // Getters
        public long getTotalComentarios() { return totalComentarios; }
        public long getComentariosGenerales() { return comentariosGenerales; }
        public long getComentariosTecnicos() { return comentariosTecnicos; }
        public long getComentariosInternos() { return comentariosInternos; }
        public long getComentariosSolucion() { return comentariosSolucion; }
    }
} 