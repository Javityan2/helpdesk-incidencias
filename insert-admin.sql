USE helpdesk_incidencias;

-- Insertar usuario admin
INSERT INTO usuarios (empleado_id, nombre, apellido, email, password, rol, departamento, cargo, activo, fecha_creacion) 
VALUES ('ADMIN001', 'Admin', 'Sistema', 'admin@helpdesk.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN', 'TI', 'Administrador del Sistema', true, NOW())
ON DUPLICATE KEY UPDATE empleado_id = empleado_id;

-- Verificar que se insertó correctamente
SELECT id, empleado_id, nombre, apellido, email, rol, activo FROM usuarios WHERE email = 'admin@helpdesk.com'; 