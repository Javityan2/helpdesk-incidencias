USE helpdesk_incidencias;

-- Insertar incidencias de ejemplo
INSERT INTO incidencias (titulo, descripcion, estado, prioridad, fecha_creacion, usuario_id) VALUES
('No puedo acceder al correo', 'El sistema de correo no permite iniciar sesión desde ayer.', 'ABIERTA', 'ALTA', NOW(), 8),
('Problema con impresora', 'La impresora de la oficina marca error de papel atascado.', 'EN_PROCESO', 'MEDIA', NOW(), 11),
('Solicitud de acceso a sistema', 'Necesito acceso al sistema de gestión de proyectos.', 'ABIERTA', 'BAJA', NOW(), 12),
('Error en aplicación web', 'La aplicación web muestra error 500 al guardar datos.', 'ABIERTA', 'ALTA', NOW(), 8),
('Cambio de contraseña', 'Solicito cambio de contraseña por seguridad.', 'CERRADA', 'BAJA', NOW(), 11),
('Actualización de software', 'Requiere actualización urgente de antivirus.', 'EN_PROCESO', 'MEDIA', NOW(), 12),
('Problema con red WiFi', 'La red WiFi es inestable en la sala de reuniones.', 'ABIERTA', 'MEDIA', NOW(), 8),
('Solicitud de equipo nuevo', 'Solicito laptop nueva para nuevo empleado.', 'ABIERTA', 'ALTA', NOW(), 23),
('Error en nómina', 'El sistema de nómina no calcula bien los descuentos.', 'ABIERTA', 'ALTA', NOW(), 11),
('Consulta sobre vacaciones', '¿Cómo puedo ver mis días de vacaciones disponibles?', 'CERRADA', 'BAJA', NOW(), 12);

-- Insertar comentarios de ejemplo para algunas incidencias
INSERT INTO comentarios (incidencia_id, usuario_id, contenido, fecha_creacion, es_interno) VALUES
(1, 23, 'Estamos revisando el acceso al correo.', NOW(), 0),
(2, 8, '¿La impresora muestra algún código de error específico?', NOW(), 0),
(4, 23, 'El error 500 está siendo investigado por el equipo de desarrollo.', NOW(), 0),
(7, 12, 'Se ha reiniciado el router, por favor verifica la conexión.', NOW(), 0),
(8, 23, 'La solicitud de laptop está en proceso de aprobación.', NOW(), 0); 