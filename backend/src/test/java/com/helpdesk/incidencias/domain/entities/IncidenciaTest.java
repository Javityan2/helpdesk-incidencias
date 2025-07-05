package com.helpdesk.incidencias.domain.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test de ejemplo usando JUnit 5 sin MockK por ahora
 * Para usar MockK necesitamos instalar las dependencias primero
 */
class IncidenciaTest {

    @BeforeEach
    void setUp() {
        // Setup inicial si es necesario
    }

    @Test
    void testIncidenciaCreation() {
        // Arrange
        String titulo = "Problema de red";
        String descripcion = "No se puede acceder a internet";
        
        // Act
        Incidencia incidencia = new Incidencia();
        incidencia.setTitulo(titulo);
        incidencia.setDescripcion(descripcion);
        incidencia.setPrioridad(Prioridad.ALTA);
        incidencia.setEstado(EstadoIncidencia.ABIERTA);
        
        // Assert
        assertNotNull(incidencia);
        assertEquals(titulo, incidencia.getTitulo());
        assertEquals(descripcion, incidencia.getDescripcion());
        assertEquals(Prioridad.ALTA, incidencia.getPrioridad());
        assertEquals(EstadoIncidencia.ABIERTA, incidencia.getEstado());
    }

    @Test
    void testIncidenciaValidation() {
        // Arrange
        Incidencia incidencia = new Incidencia();
        
        // Act & Assert - Validar que los campos obligatorios no sean nulos
        assertDoesNotThrow(() -> {
            incidencia.setTitulo("Título válido");
            incidencia.setDescripcion("Descripción válida");
        });
    }

    @Test
    void testIncidenciaEstadoTransitions() {
        // Arrange
        Incidencia incidencia = new Incidencia();
        incidencia.setTitulo("Test Estado");
        incidencia.setDescripcion("Test Descripción");
        
        // Act & Assert - Probar transiciones de estado
        assertEquals(EstadoIncidencia.ABIERTA, incidencia.getEstado());
        
        incidencia.setEstado(EstadoIncidencia.EN_PROCESO);
        assertEquals(EstadoIncidencia.EN_PROCESO, incidencia.getEstado());
        
        incidencia.setEstado(EstadoIncidencia.RESUELTA);
        assertEquals(EstadoIncidencia.RESUELTA, incidencia.getEstado());
        assertNotNull(incidencia.getFechaResolucion());
    }

    @Test
    void testIncidenciaPrioridad() {
        // Arrange
        Incidencia incidencia = new Incidencia();
        
        // Act & Assert - Probar diferentes prioridades
        incidencia.setPrioridad(Prioridad.BAJA);
        assertEquals(Prioridad.BAJA, incidencia.getPrioridad());
        
        incidencia.setPrioridad(Prioridad.MEDIA);
        assertEquals(Prioridad.MEDIA, incidencia.getPrioridad());
        
        incidencia.setPrioridad(Prioridad.ALTA);
        assertEquals(Prioridad.ALTA, incidencia.getPrioridad());
        
        incidencia.setPrioridad(Prioridad.CRITICA);
        assertEquals(Prioridad.CRITICA, incidencia.getPrioridad());
    }
} 