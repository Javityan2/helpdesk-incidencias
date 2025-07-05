USE helpdesk_incidencias;

-- Eliminar usuario admin existente si existe
DELETE FROM usuarios WHERE empleado_id = 'ADMIN001';

-- Insertar nuevo usuario admin con contraseña 'password' (hash BCrypt simple)
INSERT INTO usuarios (empleado_id, nombre, apellido, email, password, rol, departamento, cargo, activo, fecha_creacion) 
VALUES ('ADMIN001', 'Admin', 'Sistema', 'admin@helpdesk.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ADMIN', 'TI', 'Administrador del Sistema', true, NOW());

-- Verificar que se insertó correctamente
SELECT id, empleado_id, nombre, apellido, email, rol, activo FROM usuarios WHERE empleado_id = 'ADMIN001'; 