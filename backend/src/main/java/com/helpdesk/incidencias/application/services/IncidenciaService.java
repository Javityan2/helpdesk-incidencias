package com.helpdesk.incidencias.application.services;

import com.helpdesk.incidencias.application.dtos.IncidenciaDTO;
import com.helpdesk.incidencias.application.dtos.IncidenciaCreateRequest;
import com.helpdesk.incidencias.application.dtos.IncidenciaUpdateRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    
    // ===================================
    // MÉTODOS PARA DTOs
    // ===================================
    
    /**
     * Crea una nueva incidencia desde DTO
     */
    public IncidenciaDTO crearIncidencia(IncidenciaCreateRequest request) {
        Incidencia incidencia = new Incidencia();
        incidencia.setTitulo(request.getTitulo());
        incidencia.setDescripcion(request.getDescripcion());
        incidencia.setPrioridad(request.getPrioridad());
        incidencia.setCategoria(request.getCategoria());
        incidencia.setTiempoEstimadoHoras(request.getTiempoEstimadoHoras());
        incidencia.setComentariosInternos(request.getComentariosInternos());
        
        Incidencia incidenciaCreada = crearIncidencia(incidencia);
        return convertirADTO(incidenciaCreada);
    }
    
    /**
     * Actualiza una incidencia desde DTO
     */
    public IncidenciaDTO actualizarIncidencia(Long id, IncidenciaUpdateRequest request) {
        Incidencia incidenciaActualizada = new Incidencia();
        incidenciaActualizada.setTitulo(request.getTitulo());
        incidenciaActualizada.setDescripcion(request.getDescripcion());
        incidenciaActualizada.setPrioridad(request.getPrioridad());
        incidenciaActualizada.setCategoria(request.getCategoria());
        incidenciaActualizada.setTiempoEstimadoHoras(request.getTiempoEstimadoHoras());
        incidenciaActualizada.setTiempoRealHoras(request.getTiempoRealHoras());
        incidenciaActualizada.setSolucion(request.getSolucion());
        incidenciaActualizada.setComentariosInternos(request.getComentariosInternos());
        
        Incidencia incidencia = actualizarIncidencia(id, incidenciaActualizada);
        return convertirADTO(incidencia);
    }
    
    /**
     * Obtiene incidencia por ID como DTO
     */
    @Transactional(readOnly = true)
    public Optional<IncidenciaDTO> obtenerIncidenciaPorId(Long id) {
        Optional<Incidencia> incidenciaOpt = obtenerIncidencia(id);
        return incidenciaOpt.map(this::convertirADTO);
    }
    
    /**
     * Obtiene incidencias filtradas como DTOs
     */
    @Transactional(readOnly = true)
    public List<IncidenciaDTO> obtenerIncidenciasFiltradas(EstadoIncidencia estado, Prioridad prioridad, 
                                                          CategoriaIncidencia categoria, String usuarioId) {
        List<Incidencia> incidencias = incidenciaRepository.findByFiltros(estado, prioridad, categoria, usuarioId);
        return incidencias.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene incidencias paginadas como DTOs
     */
    @Transactional(readOnly = true)
    public Page<IncidenciaDTO> obtenerIncidenciasPaginadas(EstadoIncidencia estado, Prioridad prioridad,
                                                          CategoriaIncidencia categoria, String usuarioId, 
                                                          Pageable pageable) {
        Page<Incidencia> incidencias = incidenciaRepository.findByFiltrosPaginados(estado, prioridad, categoria, usuarioId, pageable);
        return incidencias.map(this::convertirADTO);
    }
    
    /**
     * Cambia el estado de una incidencia y retorna DTO
     */
    public IncidenciaDTO cambiarEstadoIncidencia(Long id, EstadoIncidencia nuevoEstado) {
        // TODO: Obtener usuario del contexto de seguridad
        Usuario usuario = new Usuario(); // Temporal
        Incidencia incidencia = cambiarEstado(id, nuevoEstado, usuario);
        return convertirADTO(incidencia);
    }
    
    /**
     * Asigna técnico a incidencia y retorna DTO
     */
    public IncidenciaDTO asignarTecnico(Long id, Long tecnicoId) {
        // TODO: Obtener usuario del contexto de seguridad
        Usuario usuario = new Usuario(); // Temporal
        Incidencia incidencia = asignarIncidencia(id, tecnicoId, usuario);
        return convertirADTO(incidencia);
    }
    
    /**
     * Obtiene incidencias populares como DTOs
     */
    @Transactional(readOnly = true)
    public List<IncidenciaDTO> obtenerIncidenciasPopulares(int limit) {
        List<Incidencia> incidencias = incidenciaRepository.findIncidenciasPopulares(limit);
        return incidencias.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // ===================================
    // MÉTODOS DE ESTADÍSTICAS
    // ===================================
    
    /**
     * Obtiene estadísticas generales
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticasGenerales() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalIncidencias = incidenciaRepository.count();
        long incidenciasAbiertas = incidenciaRepository.countByEstado(EstadoIncidencia.ABIERTA);
        long incidenciasEnProceso = incidenciaRepository.countByEstado(EstadoIncidencia.EN_PROCESO);
        long incidenciasResueltas = incidenciaRepository.countByEstado(EstadoIncidencia.RESUELTA);
        long incidenciasCerradas = incidenciaRepository.countByEstado(EstadoIncidencia.CERRADA);
        
        estadisticas.put("totalIncidencias", totalIncidencias);
        estadisticas.put("incidenciasAbiertas", incidenciasAbiertas);
        estadisticas.put("incidenciasEnProceso", incidenciasEnProceso);
        estadisticas.put("incidenciasResueltas", incidenciasResueltas);
        estadisticas.put("incidenciasCerradas", incidenciasCerradas);
        estadisticas.put("porcentajeResueltas", totalIncidencias > 0 ? (double) incidenciasResueltas / totalIncidencias * 100 : 0);
        
        return estadisticas;
    }
    
    /**
     * Obtiene estadísticas por departamento
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticasPorDepartamento() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        List<Object[]> resultados = incidenciaRepository.findEstadisticasPorDepartamento();
        Map<String, Long> porDepartamento = new HashMap<>();
        
        for (Object[] resultado : resultados) {
            String departamento = (String) resultado[0];
            Long cantidad = (Long) resultado[1];
            porDepartamento.put(departamento, cantidad);
        }
        
        estadisticas.put("porDepartamento", porDepartamento);
        return estadisticas;
    }
    
    /**
     * Obtiene estadísticas por técnico
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticasPorTecnico() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        List<Object[]> resultados = incidenciaRepository.findEstadisticasPorTecnico();
        Map<String, Long> porTecnico = new HashMap<>();
        
        for (Object[] resultado : resultados) {
            String tecnico = (String) resultado[0];
            Long cantidad = (Long) resultado[1];
            porTecnico.put(tecnico, cantidad);
        }
        
        estadisticas.put("porTecnico", porTecnico);
        return estadisticas;
    }
    
    /**
     * Obtiene incidencias por estado
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerIncidenciasPorEstado() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        for (EstadoIncidencia estado : EstadoIncidencia.values()) {
            long cantidad = incidenciaRepository.countByEstado(estado);
            estadisticas.put(estado.getDescripcion(), cantidad);
        }
        
        return estadisticas;
    }
    
    /**
     * Obtiene incidencias por prioridad
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerIncidenciasPorPrioridad() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        for (Prioridad prioridad : Prioridad.values()) {
            long cantidad = incidenciaRepository.countByPrioridad(prioridad);
            estadisticas.put(prioridad.getDescripcion(), cantidad);
        }
        
        return estadisticas;
    }
    
    /**
     * Obtiene incidencias por categoría
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerIncidenciasPorCategoria() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        List<Object[]> resultados = incidenciaRepository.findEstadisticasPorCategoria();
        
        for (Object[] resultado : resultados) {
            String categoria = (String) resultado[0];
            Long cantidad = (Long) resultado[1];
            estadisticas.put(categoria, cantidad);
        }
        
        return estadisticas;
    }
    
    /**
     * Obtiene tendencias por fecha
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerTendencias(LocalDate fechaInicio, LocalDate fechaFin) {
        Map<String, Object> tendencias = new HashMap<>();
        
        if (fechaInicio == null) {
            fechaInicio = LocalDate.now().minusDays(30);
        }
        if (fechaFin == null) {
            fechaFin = LocalDate.now();
        }
        
        List<Object[]> resultados = incidenciaRepository.findTendenciasPorFecha(fechaInicio, fechaFin);
        Map<String, Long> porFecha = new HashMap<>();
        
        for (Object[] resultado : resultados) {
            String fecha = resultado[0].toString();
            Long cantidad = (Long) resultado[1];
            porFecha.put(fecha, cantidad);
        }
        
        tendencias.put("porFecha", porFecha);
        tendencias.put("fechaInicio", fechaInicio);
        tendencias.put("fechaFin", fechaFin);
        
        return tendencias;
    }
    
    /**
     * Obtiene tiempo promedio de resolución
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerTiempoPromedioResolucion() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        Double promedioHoras = incidenciaRepository.findTiempoPromedioResolucion();
        Double promedioDias = promedioHoras != null ? promedioHoras / 24.0 : 0.0;
        
        estadisticas.put("promedioHoras", promedioHoras != null ? promedioHoras : 0);
        estadisticas.put("promedioDias", promedioDias);
        
        return estadisticas;
    }
    
    /**
     * Obtiene incidencias críticas como Map
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerIncidenciasCriticasMap() {
        Map<String, Object> resultado = new HashMap<>();
        List<Incidencia> incidencias = obtenerIncidenciasCriticas();
        
        List<IncidenciaDTO> incidenciasDTO = incidencias.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        
        resultado.put("incidencias", incidenciasDTO);
        resultado.put("total", incidencias.size());
        
        return resultado;
    }
    
    /**
     * Obtiene incidencias sin asignar como Map
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerIncidenciasSinAsignarMap() {
        Map<String, Object> resultado = new HashMap<>();
        List<Incidencia> incidencias = obtenerIncidenciasSinAsignar();
        
        List<IncidenciaDTO> incidenciasDTO = incidencias.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        
        resultado.put("incidencias", incidenciasDTO);
        resultado.put("total", incidencias.size());
        
        return resultado;
    }
    
    /**
     * Obtiene reporte de productividad
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerReporteProductividad(LocalDate fechaInicio, LocalDate fechaFin) {
        Map<String, Object> reporte = new HashMap<>();
        
        if (fechaInicio == null) {
            fechaInicio = LocalDate.now().minusDays(30);
        }
        if (fechaFin == null) {
            fechaFin = LocalDate.now();
        }
        
        // Estadísticas generales del período
        long incidenciasCreadas = incidenciaRepository.countByFechaCreacionBetween(
            fechaInicio.atStartOfDay(), fechaFin.atTime(23, 59, 59));
        long incidenciasResueltas = incidenciaRepository.countByFechaResolucionBetween(
            fechaInicio.atStartOfDay(), fechaFin.atTime(23, 59, 59));
        
        reporte.put("fechaInicio", fechaInicio);
        reporte.put("fechaFin", fechaFin);
        reporte.put("incidenciasCreadas", incidenciasCreadas);
        reporte.put("incidenciasResueltas", incidenciasResueltas);
        reporte.put("tasaResolucion", incidenciasCreadas > 0 ? (double) incidenciasResueltas / incidenciasCreadas * 100 : 0);
        
        return reporte;
    }
    
    // ===================================
    // MÉTODOS DE CONVERSIÓN
    // ===================================
    
    /**
     * Convierte una entidad Incidencia a DTO
     */
    private IncidenciaDTO convertirADTO(Incidencia incidencia) {
        IncidenciaDTO dto = new IncidenciaDTO();
        
        // Datos básicos
        dto.setId(incidencia.getId());
        dto.setTitulo(incidencia.getTitulo());
        dto.setDescripcion(incidencia.getDescripcion());
        dto.setPrioridad(incidencia.getPrioridad());
        dto.setEstado(incidencia.getEstado());
        dto.setCategoria(incidencia.getCategoria());
        
        // Usuario que creó la incidencia
        if (incidencia.getUsuario() != null) {
            dto.setUsuarioId(incidencia.getUsuario().getId());
            dto.setUsuarioEmpleadoId(incidencia.getUsuario().getEmpleadoId());
            dto.setUsuarioNombre(incidencia.getUsuario().getNombre());
            dto.setUsuarioApellido(incidencia.getUsuario().getApellido());
            dto.setUsuarioEmail(incidencia.getUsuario().getEmail());
            dto.setUsuarioDepartamento(incidencia.getUsuario().getDepartamento());
        }
        
        // Técnico asignado
        if (incidencia.getAsignado() != null) {
            dto.setAsignadoId(incidencia.getAsignado().getId());
            dto.setAsignadoEmpleadoId(incidencia.getAsignado().getEmpleadoId());
            dto.setAsignadoNombre(incidencia.getAsignado().getNombre());
            dto.setAsignadoApellido(incidencia.getAsignado().getApellido());
            dto.setAsignadoEmail(incidencia.getAsignado().getEmail());
            dto.setAsignadoRol(incidencia.getAsignado().getRol());
        }
        
        // Fechas
        dto.setFechaCreacion(incidencia.getFechaCreacion());
        dto.setFechaActualizacion(incidencia.getFechaActualizacion());
        dto.setFechaResolucion(incidencia.getFechaResolucion());
        
        // Tiempos
        dto.setTiempoEstimadoHoras(incidencia.getTiempoEstimadoHoras());
        dto.setTiempoRealHoras(incidencia.getTiempoRealHoras());
        
        // Contenido
        dto.setSolucion(incidencia.getSolucion());
        dto.setComentariosInternos(incidencia.getComentariosInternos());
        
        // Estadísticas
        dto.setNumeroComentarios(incidencia.getComentarios() != null ? incidencia.getComentarios().size() : 0);
        dto.setNumeroHistorial(incidencia.getHistorial() != null ? incidencia.getHistorial().size() : 0);
        dto.setNumeroDiagramas(incidencia.getDiagramasFlujo() != null ? incidencia.getDiagramasFlujo().size() : 0);
        
        return dto;
    }
    
    // ===================================
    // MÉTODOS EXISTENTES (mantener)
    // ===================================
    
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
        return incidenciaRepository.findIncidenciasActivas(List.of(EstadoIncidencia.ABIERTA, EstadoIncidencia.EN_PROCESO));
    }
    
    /**
     * Obtiene incidencias críticas
     */
    @Transactional(readOnly = true)
    public List<Incidencia> obtenerIncidenciasCriticas() {
        return incidenciaRepository.findIncidenciasCriticas(List.of(EstadoIncidencia.ABIERTA, EstadoIncidencia.EN_PROCESO), Prioridad.CRITICA);
    }
    
    /**
     * Cuenta incidencias activas
     */
    @Transactional(readOnly = true)
    public long contarIncidenciasActivas() {
        return incidenciaRepository.countIncidenciasActivas(List.of(EstadoIncidencia.ABIERTA, EstadoIncidencia.EN_PROCESO));
    }
    
    /**
     * Cuenta incidencias críticas
     */
    @Transactional(readOnly = true)
    public long contarIncidenciasCriticas() {
        return incidenciaRepository.countIncidenciasCriticas(List.of(EstadoIncidencia.ABIERTA, EstadoIncidencia.EN_PROCESO), Prioridad.CRITICA);
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
        return incidenciaRepository.findByTituloContainingOrDescripcionContaining(texto);
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