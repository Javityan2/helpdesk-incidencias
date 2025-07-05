package com.helpdesk.incidencias.infrastructure.controllers;

import com.helpdesk.incidencias.application.services.UsuarioService;
import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.Rol;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de integración para UsuarioController
 */
@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

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
        usuarioTest.setPassword("password123");
        usuarioTest.setRol(Rol.ADMIN);
        usuarioTest.setActivo(true);
    }

    @Test
    void testGetAllUsuarios() throws Exception {
        // Arrange
        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(List.of(usuarioTest));

        // Act & Assert
        mockMvc.perform(get("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].empleadoId").value("EMP001"));

        verify(usuarioService).obtenerTodosLosUsuarios();
    }

    @Test
    void testGetUsuarioById() throws Exception {
        // Arrange
        when(usuarioService.obtenerUsuario(1L)).thenReturn(Optional.of(usuarioTest));

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.empleadoId").value("EMP001"));

        verify(usuarioService).obtenerUsuario(1L);
    }

    @Test
    void testGetUsuarioByIdNotFound() throws Exception {
        // Arrange
        when(usuarioService.obtenerUsuario(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));

        verify(usuarioService).obtenerUsuario(999L);
    }

    @Test
    void testGetUsuarioByEmpleadoId() throws Exception {
        // Arrange
        when(usuarioService.obtenerUsuarioPorEmpleadoId("EMP001")).thenReturn(Optional.of(usuarioTest));

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/empleado/EMP001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.empleadoId").value("EMP001"));

        verify(usuarioService).obtenerUsuarioPorEmpleadoId("EMP001");
    }

    @Test
    void testGetUsuariosActivos() throws Exception {
        // Arrange
        when(usuarioService.obtenerUsuariosActivos()).thenReturn(List.of(usuarioTest));

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/activos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].empleadoId").value("EMP001"));

        verify(usuarioService).obtenerUsuariosActivos();
    }

    @Test
    void testDeleteUsuario() throws Exception {
        // Arrange
        when(usuarioService.obtenerUsuario(1L)).thenReturn(Optional.of(usuarioTest));

        // Act & Assert
        mockMvc.perform(delete("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(usuarioService).obtenerUsuario(1L);
        verify(usuarioService).eliminarUsuario(1L);
    }

    @Test
    void testDeleteUsuarioNotFound() throws Exception {
        // Arrange
        when(usuarioService.obtenerUsuario(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/api/usuarios/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));

        verify(usuarioService).obtenerUsuario(999L);
        verify(usuarioService, never()).eliminarUsuario(anyLong());
    }

    @Test
    void testGetEstadisticas() throws Exception {
        // Arrange
        UsuarioService.UsuarioStats stats = new UsuarioService.UsuarioStats(10, 8, 5, 2, 2, 1);
        when(usuarioService.obtenerEstadisticas()).thenReturn(stats);

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/estadisticas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalUsuarios").value(10))
                .andExpect(jsonPath("$.data.usuariosActivos").value(8));

        verify(usuarioService).obtenerEstadisticas();
    }
} 