-- Datos de prueba para la aplicación Helpdesk Incidencias

-- Insertar usuarios de prueba
INSERT INTO usuarios (empleado_id, nombre, apellido, email, password, rol, departamento, cargo, activo, fecha_creacion) VALUES
('EMP001', 'Juan', 'Pérez', 'juan.perez@empresa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN', 'IT', 'Administrador del Sistema', true, NOW()),
('EMP002', 'María', 'García', 'maria.garcia@empresa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'TECNICO', 'IT', 'Técnico de Soporte', true, NOW()),
('EMP003', 'Carlos', 'López', 'carlos.lopez@empresa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'SUPERVISOR', 'IT', 'Supervisor de IT', true, NOW()),
('EMP004', 'Ana', 'Martínez', 'ana.martinez@empresa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'USUARIO', 'RRHH', 'Recursos Humanos', true, NOW()),
('EMP005', 'Luis', 'Rodríguez', 'luis.rodriguez@empresa.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'USUARIO', 'VENTAS', 'Vendedor', true, NOW());

-- Insertar incidencias de prueba
INSERT INTO incidencias (titulo, descripcion, prioridad, estado, categoria, usuario_id, asignado_id, fecha_creacion, frecuencia_busqueda) VALUES
('Error al acceder al sistema de facturación', 'No puedo acceder al sistema de facturación desde mi computadora. Aparece un error de conexión.', 'ALTA', 'PENDIENTE', 'SISTEMAS', 4, 2, NOW(), 5),
('Problema con la impresora del departamento', 'La impresora del departamento de ventas no está funcionando correctamente. Hace ruidos extraños.', 'MEDIA', 'EN_PROCESO', 'HARDWARE', 5, 2, NOW(), 3),
('Solicitud de nuevo software', 'Necesito instalar Adobe Photoshop en mi computadora para el trabajo de diseño.', 'BAJA', 'PENDIENTE', 'SOFTWARE', 4, NULL, NOW(), 1),
('Error en la base de datos', 'El sistema de inventario está dando errores al consultar productos.', 'ALTA', 'RESUELTA', 'SISTEMAS', 5, 3, NOW(), 8),
('Problema de red en el área de producción', 'No hay conexión a internet en el área de producción. Esto está afectando el trabajo.', 'ALTA', 'EN_PROCESO', 'REDES', 4, 2, NOW(), 4),
('Actualización de antivirus', 'El antivirus necesita ser actualizado en todas las computadoras del departamento.', 'MEDIA', 'PENDIENTE', 'SEGURIDAD', 5, NULL, NOW(), 2),
('Problema con el correo electrónico', 'No puedo enviar correos electrónicos desde Outlook. Aparece un error de autenticación.', 'MEDIA', 'RESUELTA', 'CORREO', 4, 3, NOW(), 6),
('Solicitud de acceso a sistema', 'Necesito acceso al sistema de reportes para generar informes mensuales.', 'BAJA', 'PENDIENTE', 'ACCESOS', 5, NULL, NOW(), 1),
('Problema con el servidor de archivos', 'No puedo acceder a los archivos compartidos en el servidor. Aparece un error de permisos.', 'ALTA', 'EN_PROCESO', 'SISTEMAS', 4, 2, NOW(), 7),
('Mantenimiento preventivo', 'Solicito mantenimiento preventivo para las computadoras del departamento de contabilidad.', 'BAJA', 'PENDIENTE', 'MANTENIMIENTO', 5, NULL, NOW(), 1); 