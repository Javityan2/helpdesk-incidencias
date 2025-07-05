-- Datos de prueba para testing con Postman
-- Este archivo se ejecuta automáticamente al iniciar la aplicación

-- Insertar usuarios de prueba
INSERT INTO usuarios (empleado_id, nombre, apellido, email, password, rol, departamento, cargo, activo, fecha_creacion, fecha_actualizacion) VALUES
('ADMIN001', 'Admin', 'Sistema', 'admin@empresa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN', 'IT', 'Administrador del Sistema', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('EMP001', 'Juan', 'Pérez', 'juan.perez@empresa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'EMPLEADO', 'IT', 'Desarrollador', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('EMP002', 'María', 'García', 'maria.garcia@empresa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'TECNICO', 'Soporte', 'Técnico de Soporte', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('EMP003', 'Carlos', 'López', 'carlos.lopez@empresa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'SUPERVISOR', 'Soporte', 'Supervisor de Soporte', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('EMP004', 'Ana', 'Martínez', 'ana.martinez@empresa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'EMPLEADO', 'RRHH', 'Recursos Humanos', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('EMP005', 'Luis', 'Rodríguez', 'luis.rodriguez@empresa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'TECNICO', 'IT', 'Técnico de Redes', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Nota: La contraseña encriptada corresponde a "password123"
-- Puedes usar estas credenciales para testing:
-- admin@empresa.com / password123
-- juan.perez@empresa.com / password123
-- maria.garcia@empresa.com / password123
-- etc. 