package com.helpdesk.incidencias.application.services;

import com.helpdesk.incidencias.application.dtos.ComentarioDTO;
import com.helpdesk.incidencias.application.dtos.ComentarioCreateRequest;
import com.helpdesk.incidencias.application.dtos.ComentarioUpdateRequest;
import com.helpdesk.incidencias.domain.entities.Comentario;
import com.helpdesk.incidencias.domain.entities.Incidencia;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.TipoComentario;
import com.helpdesk.incidencias.domain.repositories.ComentarioRepository;
import com.helpdesk.incidencias.domain.repositories.IncidenciaRepository;
import com.helpdesk.incidencias.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    
    // ===================================
    // MÉTODOS PARA DTOs
    // ===================================
    
    /**
     * Crea un nuevo comentario desde DTO
     */
    public ComentarioDTO crearComentario(ComentarioCreateRequest request) {
        // Validar que la incidencia existe
        Optional<Incidencia> incidenciaOpt = incidenciaRepository.findById(request.getIncidenciaId());
        if (incidenciaOpt.isEmpty()) {
            throw new IllegalArgumentException("Incidencia no encontrada con ID: " + request.getIncidenciaId());
        }
        
        Comentario comentario = new Comentario();
        comentario.setContenido(request.getContenido());
        comentario.setTipo(request.getTipo());
        comentario.setIncidencia(incidenciaOpt.get());
        comentario.setFechaCreacion(LocalDateTime.now());
        
        // TODO: Obtener usuario del contexto de seguridad
        // comentario.setUsuario(usuarioActual);
        
        Comentario comentarioCreado = comentarioRepository.save(comentario);
        return convertirADTO(comentarioCreado);
    }
    
    /**
     * Actualiza un comentario desde DTO
     */
    public ComentarioDTO actualizarComentario(Long id, ComentarioUpdateRequest request) {
        Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
        if (comentarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Comentario no encontrado con ID: " + id);
        }
        
        Comentario comentario = comentarioOpt.get();
        
        if (request.tieneContenido()) {
            comentario.setContenido(request.getContenido());
        }
        if (request.tieneTipo()) {
            comentario.setTipo(request.getTipo());
        }
        
        Comentario comentarioActualizado = comentarioRepository.save(comentario);
        return convertirADTO(comentarioActualizado);
    }
    
    /**
     * Obtiene comentario por ID como DTO
     */
    @Transactional(readOnly = true)
    public Optional<ComentarioDTO> obtenerComentarioPorId(Long id) {
        Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
        return comentarioOpt.map(this::convertirADTO);
    }
    
    /**
     * Obtiene comentarios de una incidencia como DTOs
     */
    @Transactional(readOnly = true)
    public List<ComentarioDTO> obtenerComentariosPorIncidencia(Long incidenciaId, TipoComentario tipo) {
        List<Comentario> comentarios;
        if (tipo != null) {
            comentarios = comentarioRepository.findByIncidenciaIdAndTipo(incidenciaId, tipo);
        } else {
            comentarios = comentarioRepository.findByIncidenciaId(incidenciaId);
        }
        
        return comentarios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene comentarios paginados como DTOs
     */
    @Transactional(readOnly = true)
    public Page<ComentarioDTO> obtenerComentariosPaginados(Long incidenciaId, TipoComentario tipo, Pageable pageable) {
        Page<Comentario> comentarios;
        if (tipo != null) {
            comentarios = comentarioRepository.findByIncidenciaIdAndTipo(incidenciaId, tipo, pageable);
        } else {
            comentarios = comentarioRepository.findByIncidenciaId(incidenciaId, pageable);
        }
        
        return comentarios.map(this::convertirADTO);
    }
    
    /**
     * Elimina un comentario
     */
    public void eliminarComentario(Long id) {
        if (!comentarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Comentario no encontrado con ID: " + id);
        }
        comentarioRepository.deleteById(id);
    }
    
    /**
     * Obtiene comentarios por tipo como DTOs
     */
    @Transactional(readOnly = true)
    public List<ComentarioDTO> obtenerComentariosPorTipoDTO(TipoComentario tipo) {
        List<Comentario> comentarios = comentarioRepository.findByTipo(tipo);
        return comentarios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene comentarios de un usuario como DTOs
     */
    @Transactional(readOnly = true)
    public List<ComentarioDTO> obtenerComentariosPorUsuario(Long usuarioId) {
        List<Comentario> comentarios = comentarioRepository.findByUsuarioId(usuarioId);
        return comentarios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene comentarios internos de una incidencia
     */
    @Transactional(readOnly = true)
    public List<ComentarioDTO> obtenerComentariosInternos(Long incidenciaId) {
        List<Comentario> comentarios = comentarioRepository.findByIncidenciaIdAndTipo(incidenciaId, TipoComentario.INTERNO);
        return comentarios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene comentarios técnicos de una incidencia
     */
    @Transactional(readOnly = true)
    public List<ComentarioDTO> obtenerComentariosTecnicos(Long incidenciaId) {
        List<Comentario> comentarios = comentarioRepository.findByIncidenciaIdAndTipoIn(
            incidenciaId, List.of(TipoComentario.TECNICO, TipoComentario.INTERNO));
        return comentarios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene estadísticas de comentarios de una incidencia
     */
    @Transactional(readOnly = true)
    public Object obtenerEstadisticasComentarios(Long incidenciaId) {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalComentarios = comentarioRepository.countByIncidenciaId(incidenciaId);
        long comentariosGenerales = comentarioRepository.countByIncidenciaIdAndTipo(incidenciaId, TipoComentario.GENERAL);
        long comentariosTecnicos = comentarioRepository.countByIncidenciaIdAndTipo(incidenciaId, TipoComentario.TECNICO);
        long comentariosInternos = comentarioRepository.countByIncidenciaIdAndTipo(incidenciaId, TipoComentario.INTERNO);
        
        estadisticas.put("totalComentarios", totalComentarios);
        estadisticas.put("comentariosGenerales", comentariosGenerales);
        estadisticas.put("comentariosTecnicos", comentariosTecnicos);
        estadisticas.put("comentariosInternos", comentariosInternos);
        
        return estadisticas;
    }
    
    /**
     * Obtiene estadísticas generales de comentarios
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticasGenerales() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalComentarios = comentarioRepository.count();
        long comentariosGenerales = comentarioRepository.countByTipo(TipoComentario.GENERAL);
        long comentariosTecnicos = comentarioRepository.countByTipo(TipoComentario.TECNICO);
        long comentariosInternos = comentarioRepository.countByTipo(TipoComentario.INTERNO);
        
        estadisticas.put("totalComentarios", totalComentarios);
        estadisticas.put("comentariosGenerales", comentariosGenerales);
        estadisticas.put("comentariosTecnicos", comentariosTecnicos);
        estadisticas.put("comentariosInternos", comentariosInternos);
        
        return estadisticas;
    }
    
    // ===================================
    // MÉTODOS DE CONVERSIÓN
    // ===================================
    
    /**
     * Convierte una entidad Comentario a DTO
     */
    private ComentarioDTO convertirADTO(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        
        // Datos básicos
        dto.setId(comentario.getId());
        dto.setContenido(comentario.getContenido());
        dto.setTipo(comentario.getTipo());
        dto.setFechaCreacion(comentario.getFechaCreacion());
        
        // Usuario que creó el comentario
        if (comentario.getUsuario() != null) {
            dto.setUsuarioId(comentario.getUsuario().getId());
            dto.setUsuarioEmpleadoId(comentario.getUsuario().getEmpleadoId());
            dto.setUsuarioNombre(comentario.getUsuario().getNombre());
            dto.setUsuarioApellido(comentario.getUsuario().getApellido());
            dto.setUsuarioEmail(comentario.getUsuario().getEmail());
            dto.setUsuarioDepartamento(comentario.getUsuario().getDepartamento());
        }
        
        // Incidencia relacionada
        if (comentario.getIncidencia() != null) {
            dto.setIncidenciaId(comentario.getIncidencia().getId());
            dto.setIncidenciaTitulo(comentario.getIncidencia().getTitulo());
        }
        
        return dto;
    }
    
    // ===================================
    // MÉTODOS EXISTENTES (mantener)
    // ===================================
    
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
        return comentarioRepository.findComentariosTecnicos(incidencia, 
            List.of(TipoComentario.TECNICO, TipoComentario.INTERNO));
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
        return comentarioRepository.findComentariosRecientesByUsuario(usuario.getId(), limite);
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