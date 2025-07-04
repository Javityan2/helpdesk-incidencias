package com.helpdesk.incidencias.infrastructure.repositories;

import com.helpdesk.incidencias.domain.entities.Usuario;
import com.helpdesk.incidencias.domain.entities.Rol;
import com.helpdesk.incidencias.domain.repositories.UsuarioRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de usuarios usando Spring Data JPA
 */
@Repository
public interface UsuarioRepositoryImpl extends JpaRepository<Usuario, Long>, UsuarioRepository {
    
    @Override
    @Query("SELECT u FROM Usuario u WHERE u.empleadoId = :empleadoId")
    Optional<Usuario> findByEmpleadoId(@Param("empleadoId") String empleadoId);
    
    @Override
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);
    
    @Override
    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.password = :password")
    Optional<Usuario> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
    
    @Override
    @Query("SELECT u FROM Usuario u WHERE u.empleadoId = :empleadoId AND u.password = :password")
    Optional<Usuario> findByEmpleadoIdAndPassword(@Param("empleadoId") String empleadoId, @Param("password") String password);
    
    @Override
    @Query("SELECT u FROM Usuario u WHERE u.activo = true")
    List<Usuario> findByActivoTrue();
    
    @Override
    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol")
    List<Usuario> findByRol(@Param("rol") Rol rol);
    
    @Override
    @Query("SELECT u FROM Usuario u WHERE u.departamento = :departamento")
    List<Usuario> findByDepartamento(@Param("departamento") String departamento);
    
    @Override
    @Query("SELECT u FROM Usuario u WHERE u.rol IN ('TECNICO', 'SUPERVISOR', 'ADMIN')")
    List<Usuario> findTecnicos();
    
    @Override
    @Query("SELECT u FROM Usuario u WHERE u.rol IN ('SUPERVISOR', 'ADMIN')")
    List<Usuario> findSupervisores();
    
    @Override
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);
    
    @Override
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.empleadoId = :empleadoId")
    boolean existsByEmpleadoId(@Param("empleadoId") String empleadoId);
    
    @Override
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol = :rol")
    long countByRol(@Param("rol") Rol rol);
    
    @Override
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.activo = true")
    long countByActivoTrue();
} 