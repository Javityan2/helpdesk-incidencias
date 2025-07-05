package com.helpdesk.incidencias.application.services;

import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.Rol;
import com.helpdesk.incidencias.domain.repositories.UsuarioRepository;
import com.helpdesk.incidencias.application.dtos.AuthRequest;
import com.helpdesk.incidencias.application.dtos.AuthResponse;
import com.helpdesk.incidencias.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para UsuarioService
 */
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UsuarioService usuarioService;

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
    void testObtenerTodosLosUsuarios() {
        // Arrange
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioTest));

        // Act
        List<Usuario> resultado = usuarioService.obtenerTodosLosUsuarios();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(usuarioTest.getEmpleadoId(), resultado.get(0).getEmpleadoId());
        verify(usuarioRepository).findAll();
    }

    @Test
    void testObtenerUsuario() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioTest));

        // Act
        Optional<Usuario> resultado = usuarioService.obtenerUsuario(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(usuarioTest.getEmpleadoId(), resultado.get().getEmpleadoId());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void testObtenerUsuarioNotFound() {
        // Arrange
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Usuario> resultado = usuarioService.obtenerUsuario(999L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(usuarioRepository).findById(999L);
    }

    @Test
    void testObtenerUsuarioPorEmpleadoId() {
        // Arrange
        when(usuarioRepository.findByEmpleadoId("EMP001")).thenReturn(Optional.of(usuarioTest));

        // Act
        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorEmpleadoId("EMP001");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(usuarioTest.getEmpleadoId(), resultado.get().getEmpleadoId());
        verify(usuarioRepository).findByEmpleadoId("EMP001");
    }

    @Test
    void testCrearUsuario() {
        // Arrange
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmpleadoId("EMP002");
        nuevoUsuario.setNombre("María");
        nuevoUsuario.setApellido("García");
        nuevoUsuario.setEmail("maria.garcia@empresa.com");
        nuevoUsuario.setPassword("password456");

        when(usuarioRepository.existsByEmpleadoId("EMP002")).thenReturn(false);
        when(usuarioRepository.existsByEmail("maria.garcia@empresa.com")).thenReturn(false);
        when(passwordEncoder.encode("password456")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioTest);

        // Act
        Usuario resultado = usuarioService.crearUsuario(nuevoUsuario);

        // Assert
        assertNotNull(resultado);
        verify(usuarioRepository).existsByEmpleadoId("EMP002");
        verify(usuarioRepository).existsByEmail("maria.garcia@empresa.com");
        verify(passwordEncoder).encode("password456");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testCrearUsuarioEmpleadoIdDuplicado() {
        // Arrange
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmpleadoId("EMP001");
        nuevoUsuario.setEmail("nuevo@empresa.com");

        when(usuarioRepository.existsByEmpleadoId("EMP001")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.crearUsuario(nuevoUsuario);
        });
        verify(usuarioRepository).existsByEmpleadoId("EMP001");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testActualizarUsuario() {
        // Arrange
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1L);
        usuarioActualizado.setNombre("Juan Actualizado");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioTest));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioTest);

        // Act
        Usuario resultado = usuarioService.actualizarUsuario(1L, usuarioActualizado);

        // Assert
        assertNotNull(resultado);
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testActualizarUsuarioNotFound() {
        // Arrange
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(999L);

        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.actualizarUsuario(999L, usuarioActualizado);
        });
        verify(usuarioRepository).findById(999L);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testEliminarUsuario() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioTest));

        // Act
        usuarioService.eliminarUsuario(1L);

        // Assert
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void testEliminarUsuarioNotFound() {
        // Arrange
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.eliminarUsuario(999L);
        });
        verify(usuarioRepository).findById(999L);
        verify(usuarioRepository, never()).deleteById(anyLong());
    }

    @Test
    void testAutenticarPorEmpleadoIdSuccess() {
        // Arrange
        when(usuarioRepository.findByEmpleadoId("EMP001")).thenReturn(Optional.of(usuarioTest));
        when(passwordEncoder.matches("password123", "password123")).thenReturn(true);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioTest);

        // Act
        Optional<Usuario> resultado = usuarioService.autenticarPorEmpleadoId("EMP001", "password123");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(usuarioTest.getEmpleadoId(), resultado.get().getEmpleadoId());
        verify(usuarioRepository).findByEmpleadoId("EMP001");
        verify(passwordEncoder).matches("password123", "password123");
        verify(usuarioRepository).save(usuarioTest);
    }

    @Test
    void testAutenticarPorEmpleadoIdInvalidCredentials() {
        // Arrange
        when(usuarioRepository.findByEmpleadoId("EMP001")).thenReturn(Optional.of(usuarioTest));
        when(passwordEncoder.matches("wrongpassword", "password123")).thenReturn(false);

        // Act
        Optional<Usuario> resultado = usuarioService.autenticarPorEmpleadoId("EMP001", "wrongpassword");

        // Assert
        assertFalse(resultado.isPresent());
        verify(usuarioRepository).findByEmpleadoId("EMP001");
        verify(passwordEncoder).matches("wrongpassword", "password123");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testAutenticarPorEmpleadoIdUsuarioNotFound() {
        // Arrange
        when(usuarioRepository.findByEmpleadoId("EMP999")).thenReturn(Optional.empty());

        // Act
        Optional<Usuario> resultado = usuarioService.autenticarPorEmpleadoId("EMP999", "password123");

        // Assert
        assertFalse(resultado.isPresent());
        verify(usuarioRepository).findByEmpleadoId("EMP999");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testExisteUsuario() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioTest));

        // Act
        boolean resultado = usuarioService.existeUsuario(1L);

        // Assert
        assertTrue(resultado);
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void testExisteEmpleadoId() {
        // Arrange
        when(usuarioRepository.existsByEmpleadoId("EMP001")).thenReturn(true);

        // Act
        boolean resultado = usuarioService.existeEmpleadoId("EMP001");

        // Assert
        assertTrue(resultado);
        verify(usuarioRepository).existsByEmpleadoId("EMP001");
    }

    @Test
    void testExisteEmail() {
        // Arrange
        when(usuarioRepository.existsByEmail("juan.perez@empresa.com")).thenReturn(true);

        // Act
        boolean resultado = usuarioService.existeEmail("juan.perez@empresa.com");

        // Assert
        assertTrue(resultado);
        verify(usuarioRepository).existsByEmail("juan.perez@empresa.com");
    }
} 