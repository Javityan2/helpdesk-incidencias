package com.helpdesk.incidencias.application.services;

import com.helpdesk.incidencias.domain.entities.Incidencia;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.EstadoIncidencia;
import com.helpdesk.incidencias.domain.entities.Prioridad;
import com.helpdesk.incidencias.domain.entities.CategoriaIncidencia;
import com.helpdesk.incidencias.domain.entities.HistorialIncidencia;
import com.helpdesk.incidencias.domain.entities.TipoEvento;
import com.helpdesk.incidencias.domain.repositories.IncidenciaRepository;
import com.helpdesk.incidencias.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación para la gestión de incidencias
 */
@Service
@Transactional
public class IncidenciaService {
    
    @Autowired
    private IncidenciaRepository incidenciaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /**
     * Crea una nueva incidencia
     */
    public Incidencia crearIncidencia(Incidencia incidencia) {
        // Validar que el usuario existe y está activo
        if (incidencia.getUsuario() == null || incidencia.getUsuario().getId() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(incidencia.getUsuario().getId());
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        if (!usuario.getActivo()) {
            throw new IllegalArgumentException("El usuario debe estar activo");
        }
        
        // Establecer valores por defecto
        incidencia.setUsuario(usuario);
        incidencia.setEstado(EstadoIncidencia.ABIERTA);
        incidencia.setFechaCreacion(LocalDateTime.now());
        
        // Guardar la incidencia
        Incidencia incidenciaGuardada = incidenciaRepository.save(incidencia);
        
        // Crear registro en el historial
        crearRegistroHistorial(incidenciaGuardada, usuario, TipoEvento.CREACION, 
                              "Incidencia creada: " + incidencia.getTitulo());
        
        return incidenciaGuardada;
    }
    
    /**
     * Actualiza una incidencia existente
     */
    public Incidencia actualizarIncidencia(Long id, Incidencia incidenciaActualizada) {
        Optional<Incidencia> incidenciaOpt = incidenciaRepository.findById(id);
        if (incidenciaOpt.isEmpty()) {
            throw new IllegalArgumentException("Incidencia no encontrada con ID: " + id);
        }
        
        Incidencia incidencia = incidenciaOpt.get();
        
        // Actualizar campos permitidos
        if (incidenciaActualizada.getTitulo() != null) {
            incidencia.setTitulo(incidenciaActualizada.getTitulo());
        }
        if (incidenciaActualizada.getDescripcion() != null) {
            incidencia.setDescripcion(incidenciaActualizada.getDescripcion());
        }
        if (incidenciaActualizada.getCategoria() != null) {
            incidencia.setCategoria(incidenciaActualizada.getCategoria());
        }
        if (incidenciaActualizada.getTiempoEstimadoHoras() != null) {
            incidencia.setTiempoEstimadoHoras(incidenciaActualizada.getTiempoEstimadoHoras());
        }
        if (incidenciaActualizada.getSolucion() != null) {
            incidencia.setSolucion(incidenciaActualizada.getSolucion());
        }
        if (incidenciaActualizada.getComentariosInternos() != null) {
            incidencia.setComentariosInternos(incidenciaActualizada.getComentariosInternos());
        }
        
        incidencia.actualizarFechaModificacion();
        
        return incidenciaRepository.save(incidencia);
    }
    
    /**
     * Cambia el estado de una incidencia
     */
    public Incidencia cambiarEstado(Long id, EstadoIncidencia nuevoEstado, Usuario usuario) {
        Optional<Incidencia> incidenciaOpt = incidenciaRepository.findById(id);
        if (incidenciaOpt.isEmpty()) {
            throw new IllegalArgumentException("Incidencia no encontrada con ID: " + id);
        }
        
        Incidencia incidencia = incidenciaOpt.get();
        EstadoIncidencia estadoAnterior = incidencia.getEstado();
        
        // Validar transición de estado
        if (!estadoAnterior.puedeCambiarA(nuevoEstado)) {
            throw new IllegalArgumentException("No se puede cambiar de " + estadoAnterior.getDescripcion() + 
                                           " a " + nuevoEstado.getDescripcion());
        }
        
        incidencia.setEstado(nuevoEstado);
        incidencia.actualizarFechaModificacion();
        
        Incidencia incidenciaActualizada = incidenciaRepository.save(incidencia);
        
        // Crear registro en el historial
        crearRegistroHistorial(incidenciaActualizada, usuario, TipoEvento.CAMBIO_ESTADO,
                              "Estado cambiado de " + estadoAnterior.getDescripcion() + 
                              " a " + nuevoEstado.getDescripcion(),
                              estadoAnterior.getDescripcion(), nuevoEstado.getDescripcion());
        
        return incidenciaActualizada;
    }
    
    /**
     * Cambia la prioridad de una incidencia
     */
    public Incidencia cambiarPrioridad(Long id, Prioridad nuevaPrioridad, Usuario usuario) {
        Optional<Incidencia> incidenciaOpt = incidenciaRepository.findById(id);
        if (incidenciaOpt.isEmpty()) {
            throw new IllegalArgumentException("Incidencia no encontrada con ID: " + id);
        }
        
        Incidencia incidencia = incidenciaOpt.get();
        Prioridad prioridadAnterior = incidencia.getPrioridad();
        
        incidencia.setPrioridad(nuevaPrioridad);
        incidencia.actualizarFechaModificacion();
        
        Incidencia incidenciaActualizada = incidenciaRepository.save(incidencia);
        
        // Crear registro en el historial
        crearRegistroHistorial(incidenciaActualizada, usuario, TipoEvento.CAMBIO_PRIORIDAD,
                              "Prioridad cambiada de " + prioridadAnterior.getDescripcion() + 
                              " a " + nuevaPrioridad.getDescripcion(),
                              prioridadAnterior.getDescripcion(), nuevaPrioridad.getDescripcion());
        
        return incidenciaActualizada;
    }
    
    /**
     * Asigna una incidencia a un técnico
     */
    public Incidencia asignarIncidencia(Long id, Long tecnicoId, Usuario usuario) {
        Optional<Incidencia> incidenciaOpt = incidenciaRepository.findById(id);
        if (incidenciaOpt.isEmpty()) {
            throw new IllegalArgumentException("Incidencia no encontrada con ID: " + id);
        }
        
        Optional<Usuario> tecnicoOpt = usuarioRepository.findById(tecnicoId);
        if (tecnicoOpt.isEmpty()) {
            throw new IllegalArgumentException("Técnico no encontrado con ID: " + tecnicoId);
        }
        
        Usuario tecnico = tecnicoOpt.get();
        if (!tecnico.getRol().esTecnico()) {
            throw new IllegalArgumentException("El usuario debe ser técnico");
        }
        
        Incidencia incidencia = incidenciaOpt.get();
        Usuario asignadoAnterior = incidencia.getAsignado();
        
        incidencia.setAsignado(tecnico);
        incidencia.actualizarFechaModificacion();
        
        Incidencia incidenciaActualizada = incidenciaRepository.save(incidencia);
        
        // Crear registro en el historial
        String descripcion = "Incidencia asignada a " + tecnico.getNombreCompleto();
        if (asignadoAnterior != null) {
            descripcion = "Incidencia reasignada de " + asignadoAnterior.getNombreCompleto() + 
                         " a " + tecnico.getNombreCompleto();
        }
        
        crearRegistroHistorial(incidenciaActualizada, usuario, TipoEvento.ASIGNACION, descripcion);
        
        return incidenciaActualizada;
    }
    
    /**
     * Resuelve una incidencia
     */
    public Incidencia resolverIncidencia(Long id, String solucion, Usuario usuario) {
        Optional<Incidencia> incidenciaOpt = incidenciaRepository.findById(id);
        if (incidenciaOpt.isEmpty()) {
            throw new IllegalArgumentException("Incidencia no encontrada con ID: " + id);
        }
        
        Incidencia incidencia = incidenciaOpt.get();
        
        incidencia.setEstado(EstadoIncidencia.RESUELTA);
        incidencia.setSolucion(solucion);
        incidencia.setFechaResolucion(LocalDateTime.now());
        incidencia.actualizarFechaModificacion();
        
        Incidencia incidenciaActualizada = incidenciaRepository.save(incidencia);
        
        // Crear registro en el historial
        crearRegistroHistorial(incidenciaActualizada, usuario, TipoEvento.RESOLUCION,
                              "Incidencia resuelta: " + solucion);
        
        return incidenciaActualizada;
    }
    
    /**
     * Obtiene una incidencia por ID
     */
    @Transactional(readOnly = true)
    public Optional<Incidencia> obtenerIncidencia(Long id) {
        return incidenciaRepository.findById(id);
    }
    
    /**
     * Obtiene todas las incidencias
     */
    @Transactional(readOnly = true)
    public List<Incidencia> obtenerTodasLasIncidencias() {
        return incidenciaRepository.findAll();
    }
    
    /**
     * Obtiene incidencias por usuario
     */
    @Transactional(readOnly = true)
    public List<Incidencia> obtenerIncidenciasPorUsuario(Usuario usuario) {
        return incidenciaRepository.findByUsuario(usuario);
    }
    
    /**
     * Obtiene incidencias por estado
     */
    @Transactional(readOnly = true)
    public List<Incidencia> obtenerIncidenciasPorEstado(EstadoIncidencia estado) {
        return incidenciaRepository.findByEstado(estado);
    }
    
    /**
     * Obtiene incidencias por prioridad
     */
    @Transactional(readOnly = true)
    public List<Incidencia> obtenerIncidenciasPorPrioridad(Prioridad prioridad) {
        return incidenciaRepository.findByPrioridad(prioridad);
    }
    
    /**
     * Obtiene incidencias activas
     */
    @Transactional(readOnly = true)
    public List<Incidencia> obtenerIncidenciasActivas() {
        return incidenciaRepository.findIncidenciasActivas();
    }
    
    /**
     * Obtiene incidencias críticas
     */
    @Transactional(readOnly = true)
    public List<Incidencia> obtenerIncidenciasCriticas() {
        return incidenciaRepository.findIncidenciasCriticas();
    }
    
    /**
     * Obtiene incidencias sin asignar
     */
    @Transactional(readOnly = true)
    public List<Incidencia> obtenerIncidenciasSinAsignar() {
        return incidenciaRepository.findIncidenciasSinAsignar();
    }
    
    /**
     * Obtiene incidencias ordenadas por prioridad
     */
    @Transactional(readOnly = true)
    public List<Incidencia> obtenerIncidenciasOrdenadasPorPrioridad() {
        return incidenciaRepository.findIncidenciasOrdenadasPorPrioridad();
    }
    
    /**
     * Busca incidencias por texto
     */
    @Transactional(readOnly = true)
    public List<Incidencia> buscarIncidencias(String texto) {
        return incidenciaRepository.findByTituloContainingOrDescripcionContaining(texto, texto);
    }
    
    /**
     * Elimina una incidencia
     */
    public void eliminarIncidencia(Long id) {
        incidenciaRepository.deleteById(id);
    }
    
    /**
     * Verifica si una incidencia existe
     */
    @Transactional(readOnly = true)
    public boolean existeIncidencia(Long id) {
        return incidenciaRepository.existsById(id);
    }
    
    /**
     * Obtiene estadísticas de incidencias
     */
    @Transactional(readOnly = true)
    public IncidenciaRepository.IncidenciaStats obtenerEstadisticas() {
        return incidenciaRepository.getEstadisticas();
    }
    
    /**
     * Crea un registro en el historial de la incidencia
     */
    private void crearRegistroHistorial(Incidencia incidencia, Usuario usuario, TipoEvento tipoEvento, 
                                       String descripcion) {
        HistorialIncidencia historial = new HistorialIncidencia(incidencia, usuario, tipoEvento, descripcion);
        incidencia.getHistorial().add(historial);
    }
    
    /**
     * Crea un registro en el historial de la incidencia con valores anterior y nuevo
     */
    private void crearRegistroHistorial(Incidencia incidencia, Usuario usuario, TipoEvento tipoEvento, 
                                       String descripcion, String valorAnterior, String valorNuevo) {
        HistorialIncidencia historial = new HistorialIncidencia(incidencia, usuario, tipoEvento, 
                                                               descripcion, valorAnterior, valorNuevo);
        incidencia.getHistorial().add(historial);
    }
} 