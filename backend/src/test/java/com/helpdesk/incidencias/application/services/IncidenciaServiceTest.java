package com.helpdesk.incidencias.application.services;

import com.helpdesk.incidencias.domain.entities.Incidencia;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.EstadoIncidencia;
import com.helpdesk.incidencias.domain.entities.Prioridad;
import com.helpdesk.incidencias.domain.entities.CategoriaIncidencia;
import com.helpdesk.incidencias.domain.repositories.IncidenciaRepository;
import com.helpdesk.incidencias.domain.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para IncidenciaService
 */
@ExtendWith(MockitoExtension.class)
class IncidenciaServiceTest {

    @Mock
    private IncidenciaRepository incidenciaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private IncidenciaService incidenciaService;

    private Incidencia incidenciaTest;
    private Usuario usuarioTest;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        usuarioTest = new Usuario();
        usuarioTest.setId(1L);
        usuarioTest.setEmpleadoId("EMP001");
        usuarioTest.setNombre("Juan");
        usuarioTest.setApellido("Pérez");
        usuarioTest.setEmail("juan.perez@empresa.com");

        incidenciaTest = new Incidencia();
        incidenciaTest.setId(1L);
        incidenciaTest.setTitulo("Problema con el sistema de correo");
        incidenciaTest.setDescripcion("No puedo enviar correos desde Outlook");
        incidenciaTest.setUsuario(usuarioTest);
        incidenciaTest.setEstado(EstadoIncidencia.ABIERTA);
        incidenciaTest.setPrioridad(Prioridad.MEDIA);
        incidenciaTest.setCategoria(CategoriaIncidencia.SOFTWARE);
        incidenciaTest.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void testObtenerTodasLasIncidencias() {
        // Arrange
        when(incidenciaRepository.findAll()).thenReturn(List.of(incidenciaTest));

        // Act
        List<Incidencia> resultado = incidenciaService.obtenerTodasLasIncidencias();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(incidenciaTest.getTitulo(), resultado.get(0).getTitulo());
        verify(incidenciaRepository).findAll();
    }

    @Test
    void testObtenerIncidencia() {
        // Arrange
        when(incidenciaRepository.findById(1L)).thenReturn(Optional.of(incidenciaTest));

        // Act
        Optional<Incidencia> resultado = incidenciaService.obtenerIncidencia(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(incidenciaTest.getTitulo(), resultado.get().getTitulo());
        verify(incidenciaRepository).findById(1L);
    }

    @Test
    void testObtenerIncidenciaNotFound() {
        // Arrange
        when(incidenciaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Incidencia> resultado = incidenciaService.obtenerIncidencia(999L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(incidenciaRepository).findById(999L);
    }

    @Test
    void testObtenerIncidenciasPorEstado() {
        // Arrange
        when(incidenciaRepository.findByEstado(EstadoIncidencia.ABIERTA)).thenReturn(List.of(incidenciaTest));

        // Act
        List<Incidencia> resultado = incidenciaService.obtenerIncidenciasPorEstado(EstadoIncidencia.ABIERTA);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(EstadoIncidencia.ABIERTA, resultado.get(0).getEstado());
        verify(incidenciaRepository).findByEstado(EstadoIncidencia.ABIERTA);
    }

    @Test
    void testObtenerIncidenciasPorPrioridad() {
        // Arrange
        when(incidenciaRepository.findByPrioridad(Prioridad.MEDIA)).thenReturn(List.of(incidenciaTest));

        // Act
        List<Incidencia> resultado = incidenciaService.obtenerIncidenciasPorPrioridad(Prioridad.MEDIA);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(Prioridad.MEDIA, resultado.get(0).getPrioridad());
        verify(incidenciaRepository).findByPrioridad(Prioridad.MEDIA);
    }

    @Test
    void testEliminarIncidencia() {
        // Arrange
        when(incidenciaRepository.findById(1L)).thenReturn(Optional.of(incidenciaTest));

        // Act
        incidenciaService.eliminarIncidencia(1L);

        // Assert
        verify(incidenciaRepository).findById(1L);
        verify(incidenciaRepository).deleteById(1L);
    }

    @Test
    void testEliminarIncidenciaNotFound() {
        // Arrange
        when(incidenciaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            incidenciaService.eliminarIncidencia(999L);
        });
        verify(incidenciaRepository).findById(999L);
        verify(incidenciaRepository, never()).deleteById(anyLong());
    }

    @Test
    void testObtenerIncidenciasPorUsuario() {
        // Arrange
        when(incidenciaRepository.findByUsuario(usuarioTest)).thenReturn(List.of(incidenciaTest));

        // Act
        List<Incidencia> resultado = incidenciaService.obtenerIncidenciasPorUsuario(usuarioTest);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(incidenciaTest.getTitulo(), resultado.get(0).getTitulo());
        verify(incidenciaRepository).findByUsuario(usuarioTest);
    }
} 