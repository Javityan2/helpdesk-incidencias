-- =====================================================
-- SCRIPT DE DATOS DE DEMO - HELPDESK INCIDENCIAS
-- =====================================================
-- Este script prepara la base de datos con datos de ejemplo
-- para demostrar todas las funcionalidades del sistema
-- =====================================================

USE helpdesk_incidencias;

-- =====================================================
-- 1. LIMPIAR DATOS EXISTENTES (OPCIONAL)
-- =====================================================
-- Descomenta las siguientes líneas si quieres limpiar la BD antes de insertar datos de demo
-- DELETE FROM comentarios;
-- DELETE FROM incidencias;
-- DELETE FROM usuarios WHERE empleado_id NOT IN ('ADMIN001', 'EMP001', 'EMP002', 'EMP003', 'EMP004', 'EMP005');

-- =====================================================
-- 2. USUARIOS DE DEMO (DIFERENTES ROLES)
-- =====================================================

-- Usuario Administrador
INSERT INTO usuarios (empleado_id, nombre, apellido, email, password, rol, departamento, cargo, activo, fecha_creacion) 
VALUES ('ADMIN001', 'Juan', 'Pérez', 'admin@helpdesk.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ADMIN', 'TI', 'Administrador del Sistema', true, NOW())
ON DUPLICATE KEY UPDATE 
    nombre = 'Juan', 
    apellido = 'Pérez', 
    email = 'admin@helpdesk.com',
    password = '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    rol = 'ADMIN',
    departamento = 'TI',
    cargo = 'Administrador del Sistema',
    activo = true;

-- Usuario Técnico
INSERT INTO usuarios (empleado_id, nombre, apellido, email, password, rol, departamento, cargo, activo, fecha_creacion) 
VALUES ('EMP001', 'María', 'García', 'maria.garcia@empresa.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'TECNICO', 'TI', 'Técnico de Soporte', true, NOW())
ON DUPLICATE KEY UPDATE 
    nombre = 'María', 
    apellido = 'García', 
    email = 'maria.garcia@empresa.com',
    password = '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    rol = 'TECNICO',
    departamento = 'TI',
    cargo = 'Técnico de Soporte',
    activo = true;

-- Usuario Supervisor
INSERT INTO usuarios (empleado_id, nombre, apellido, email, password, rol, departamento, cargo, activo, fecha_creacion) 
VALUES ('EMP002', 'Carlos', 'López', 'carlos.lopez@empresa.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'SUPERVISOR', 'RRHH', 'Supervisor de Recursos Humanos', true, NOW())
ON DUPLICATE KEY UPDATE 
    nombre = 'Carlos', 
    apellido = 'López', 
    email = 'carlos.lopez@empresa.com',
    password = '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    rol = 'SUPERVISOR',
    departamento = 'RRHH',
    cargo = 'Supervisor de Recursos Humanos',
    activo = true;

-- Usuario Regular
INSERT INTO usuarios (empleado_id, nombre, apellido, email, password, rol, departamento, cargo, activo, fecha_creacion) 
VALUES ('EMP003', 'Ana', 'Martínez', 'ana.martinez@empresa.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'USUARIO', 'Contabilidad', 'Contador Senior', true, NOW())
ON DUPLICATE KEY UPDATE 
    nombre = 'Ana', 
    apellido = 'Martínez', 
    email = 'ana.martinez@empresa.com',
    password = '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    rol = 'USUARIO',
    departamento = 'Contabilidad',
    cargo = 'Contador Senior',
    activo = true;

-- Usuario Regular 2
INSERT INTO usuarios (empleado_id, nombre, apellido, email, password, rol, departamento, cargo, activo, fecha_creacion) 
VALUES ('EMP004', 'Luis', 'Rodríguez', 'luis.rodriguez@empresa.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'USUARIO', 'Ventas', 'Representante de Ventas', true, NOW())
ON DUPLICATE KEY UPDATE 
    nombre = 'Luis', 
    apellido = 'Rodríguez', 
    email = 'luis.rodriguez@empresa.com',
    password = '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    rol = 'USUARIO',
    departamento = 'Ventas',
    cargo = 'Representante de Ventas',
    activo = true;

-- =====================================================
-- 3. INCIDENCIAS DE DEMO (VARIADAS Y REALISTAS)
-- =====================================================

-- Obtener IDs de usuarios para las incidencias
SET @admin_id = (SELECT id FROM usuarios WHERE empleado_id = 'ADMIN001');
SET @tecnico_id = (SELECT id FROM usuarios WHERE empleado_id = 'EMP001');
SET @supervisor_id = (SELECT id FROM usuarios WHERE empleado_id = 'EMP002');
SET @usuario1_id = (SELECT id FROM usuarios WHERE empleado_id = 'EMP003');
SET @usuario2_id = (SELECT id FROM usuarios WHERE empleado_id = 'EMP004');

-- Incidencias de diferentes estados y prioridades
INSERT INTO incidencias (titulo, descripcion, estado, prioridad, categoria, fecha_creacion, usuario_id, asignado_a) VALUES
-- Incidencias ABIERTAS
('Error crítico en sistema de nómina', 'El sistema de nómina no permite procesar los pagos de este mes. Los empleados no pueden ver sus recibos de nómina y hay errores en el cálculo de horas extra.', 'ABIERTA', 'ALTA', 'SISTEMAS', DATE_SUB(NOW(), INTERVAL 2 DAY), @usuario1_id, @tecnico_id),
('Problema con red WiFi en oficina principal', 'La conexión WiFi es inestable en toda la oficina principal. Los empleados no pueden conectarse o se desconectan constantemente. Esto está afectando la productividad.', 'ABIERTA', 'ALTA', 'REDES', DATE_SUB(NOW(), INTERVAL 1 DAY), @usuario2_id, @tecnico_id),
('Solicitud de acceso a sistema CRM', 'Necesito acceso al sistema CRM para gestionar los clientes de mi área. Actualmente no tengo permisos y esto está retrasando mi trabajo.', 'ABIERTA', 'MEDIA', 'ACCESOS', NOW(), @usuario2_id, NULL),
('Impresora de sala de juntas no funciona', 'La impresora HP LaserJet en la sala de juntas muestra error "Paper Jam" pero no hay papel atascado. Ya intenté reiniciarla sin éxito.', 'ABIERTA', 'MEDIA', 'HARDWARE', DATE_SUB(NOW(), INTERVAL 3 HOUR), @usuario1_id, NULL),
('Actualización urgente de antivirus', 'El antivirus muestra que está desactualizado desde hace 2 semanas. Necesito la actualización para mantener la seguridad del equipo.', 'ABIERTA', 'BAJA', 'SEGURIDAD', DATE_SUB(NOW(), INTERVAL 1 HOUR), @usuario2_id, NULL),

-- Incidencias EN_PROCESO
('Problema con correo electrónico corporativo', 'No puedo enviar correos desde Outlook. El servidor SMTP rechaza las conexiones. Esto está afectando la comunicación con clientes.', 'EN_PROCESO', 'ALTA', 'CORREO', DATE_SUB(NOW(), INTERVAL 5 DAY), @usuario1_id, @tecnico_id),
('Solicitud de laptop nueva para empleado', 'Necesito una laptop nueva para el nuevo empleado que se incorpora la próxima semana. Específicamente necesito una Dell Latitude con Windows 11 Pro.', 'EN_PROCESO', 'MEDIA', 'HARDWARE', DATE_SUB(NOW(), INTERVAL 3 DAY), @supervisor_id, @tecnico_id),
('Error en aplicación web de inventario', 'La aplicación web de inventario muestra error 500 al intentar generar reportes. Los usuarios no pueden exportar datos para sus análisis mensuales.', 'EN_PROCESO', 'ALTA', 'SISTEMAS', DATE_SUB(NOW(), INTERVAL 4 DAY), @usuario1_id, @tecnico_id),

-- Incidencias CERRADAS
('Cambio de contraseña por seguridad', 'Solicito cambio de contraseña por motivos de seguridad. Mi cuenta fue comprometida y necesito una nueva contraseña temporal.', 'CERRADA', 'BAJA', 'ACCESOS', DATE_SUB(NOW(), INTERVAL 7 DAY), @usuario2_id, @tecnico_id),
('Consulta sobre días de vacaciones', '¿Cómo puedo ver cuántos días de vacaciones tengo disponibles? No encuentro esta información en el portal de empleados.', 'CERRADA', 'BAJA', 'CONSULTA', DATE_SUB(NOW(), INTERVAL 10 DAY), @usuario1_id, @supervisor_id),
('Problema resuelto con proyector', 'El proyector de la sala de conferencias no se conectaba correctamente. Ya se resolvió cambiando el cable HDMI.', 'CERRADA', 'MEDIA', 'HARDWARE', DATE_SUB(NOW(), INTERVAL 15 DAY), @usuario2_id, @tecnico_id);

-- =====================================================
-- 4. COMENTARIOS DE DEMO (INTERNOS Y EXTERNOS)
-- =====================================================

-- Obtener IDs de incidencias para los comentarios
SET @incidencia1_id = (SELECT id FROM incidencias WHERE titulo LIKE '%nómina%' LIMIT 1);
SET @incidencia2_id = (SELECT id FROM incidencias WHERE titulo LIKE '%WiFi%' LIMIT 1);
SET @incidencia3_id = (SELECT id FROM incidencias WHERE titulo LIKE '%CRM%' LIMIT 1);
SET @incidencia4_id = (SELECT id FROM incidencias WHERE titulo LIKE '%correo%' LIMIT 1);

-- Comentarios externos (visibles para el usuario)
INSERT INTO comentarios (incidencia_id, usuario_id, contenido, fecha_creacion, es_interno) VALUES
(@incidencia1_id, @tecnico_id, 'Hemos identificado el problema. Es un error en la configuración de la base de datos. Estamos trabajando en la solución.', DATE_SUB(NOW(), INTERVAL 1 DAY), 0),
(@incidencia1_id, @usuario1_id, 'Gracias por la actualización. ¿Cuándo estiman que esté resuelto?', DATE_SUB(NOW(), INTERVAL 12 HOUR), 0),
(@incidencia2_id, @tecnico_id, 'Hemos reiniciado el router principal y actualizado la configuración. Por favor, prueba la conexión ahora.', DATE_SUB(NOW(), INTERVAL 6 HOUR), 0),
(@incidencia3_id, @admin_id, 'He aprobado tu solicitud de acceso al CRM. Recibirás las credenciales por correo en las próximas horas.', DATE_SUB(NOW(), INTERVAL 2 HOUR), 0),
(@incidencia4_id, @tecnico_id, 'El problema está en el servidor SMTP. Hemos contactado al proveedor y están trabajando en la solución.', DATE_SUB(NOW(), INTERVAL 3 DAY), 0);

-- Comentarios internos (solo visibles para técnicos y admin)
INSERT INTO comentarios (incidencia_id, usuario_id, contenido, fecha_creacion, es_interno) VALUES
(@incidencia1_id, @tecnico_id, 'INTERNO: El problema es que la tabla de empleados tiene registros duplicados. Necesitamos limpiar la BD antes del próximo procesamiento.', DATE_SUB(NOW(), INTERVAL 1 DAY), 1),
(@incidencia2_id, @admin_id, 'INTERNO: El router necesita ser reemplazado. Ya está en proceso la compra del nuevo equipo.', DATE_SUB(NOW(), INTERVAL 5 HOUR), 1),
(@incidencia4_id, @tecnico_id, 'INTERNO: El proveedor de correo está teniendo problemas técnicos. Han estimado 24-48 horas para la resolución completa.', DATE_SUB(NOW(), INTERVAL 2 DAY), 1);

-- =====================================================
-- 5. NOTIFICACIONES DE DEMO
-- =====================================================

INSERT INTO notificaciones (usuario_id, titulo, mensaje, tipo, leida, fecha_creacion) VALUES
(@usuario1_id, 'Nueva incidencia asignada', 'Se te ha asignado una nueva incidencia: "Error crítico en sistema de nómina"', 'ASIGNACION', 0, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(@usuario1_id, 'Comentario en incidencia', 'Se ha agregado un comentario en tu incidencia: "Problema con red WiFi en oficina principal"', 'COMENTARIO', 0, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(@tecnico_id, 'Nueva incidencia de alta prioridad', 'Se ha creado una nueva incidencia de alta prioridad: "Error crítico en sistema de nómina"', 'PRIORIDAD', 0, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(@tecnico_id, 'Incidencia cerrada', 'La incidencia "Cambio de contraseña por seguridad" ha sido cerrada exitosamente', 'CIERRE', 1, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(@admin_id, 'Reporte semanal', 'Se ha generado el reporte semanal de incidencias. 15 incidencias procesadas esta semana.', 'REPORTE', 0, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(@usuario2_id, 'Solicitud aprobada', 'Tu solicitud de acceso al sistema CRM ha sido aprobada. Revisa tu correo para las credenciales.', 'APROBACION', 0, DATE_SUB(NOW(), INTERVAL 2 HOUR));

-- =====================================================
-- 6. FAVORITOS DE DEMO
-- =====================================================

INSERT INTO favoritos (usuario_id, incidencia_id, fecha_creacion) VALUES
(@usuario1_id, @incidencia1_id, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(@usuario1_id, @incidencia2_id, DATE_SUB(NOW(), INTERVAL 12 HOUR)),
(@tecnico_id, @incidencia1_id, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(@tecnico_id, @incidencia4_id, DATE_SUB(NOW(), INTERVAL 3 DAY)),
(@admin_id, @incidencia1_id, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(@admin_id, @incidencia2_id, DATE_SUB(NOW(), INTERVAL 6 HOUR));

-- =====================================================
-- 7. BORRADORES DE DEMO
-- =====================================================

INSERT INTO borradores (usuario_id, titulo, descripcion, categoria, prioridad, fecha_creacion) VALUES
(@usuario2_id, 'Solicitud de monitor adicional', 'Necesito un monitor adicional para mi estación de trabajo. El actual es muy pequeño para las tareas que realizo.', 'HARDWARE', 'MEDIA', DATE_SUB(NOW(), INTERVAL 2 HOUR)),
(@usuario1_id, 'Problema con sistema de backup', 'El sistema de backup automático no está funcionando correctamente. Los archivos no se respaldan desde hace una semana.', 'SISTEMAS', 'ALTA', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(@tecnico_id, 'Actualización de software de seguridad', 'Se requiere actualizar el software de seguridad en todos los equipos de la oficina. Algunos están desactualizados.', 'SEGURIDAD', 'MEDIA', DATE_SUB(NOW(), INTERVAL 4 HOUR));

-- =====================================================
-- 8. VERIFICACIÓN DE DATOS INSERTADOS
-- =====================================================

-- Mostrar resumen de datos insertados
SELECT 'USUARIOS' as tipo, COUNT(*) as cantidad FROM usuarios WHERE empleado_id IN ('ADMIN001', 'EMP001', 'EMP002', 'EMP003', 'EMP004')
UNION ALL
SELECT 'INCIDENCIAS' as tipo, COUNT(*) as cantidad FROM incidencias
UNION ALL
SELECT 'COMENTARIOS' as tipo, COUNT(*) as cantidad FROM comentarios
UNION ALL
SELECT 'NOTIFICACIONES' as tipo, COUNT(*) as cantidad FROM notificaciones
UNION ALL
SELECT 'FAVORITOS' as tipo, COUNT(*) as cantidad FROM favoritos
UNION ALL
SELECT 'BORRADORES' as tipo, COUNT(*) as cantidad FROM borradores;

-- =====================================================
-- 9. CREDENCIALES DE ACCESO PARA LA DEMO
-- =====================================================
/*
USUARIOS DE DEMO DISPONIBLES:

1. ADMINISTRADOR:
   - Email: admin@helpdesk.com
   - Contraseña: password
   - Rol: ADMIN (acceso completo)

2. TÉCNICO:
   - Email: maria.garcia@empresa.com
   - Contraseña: password
   - Rol: TECNICO (gestión de incidencias)

3. SUPERVISOR:
   - Email: carlos.lopez@empresa.com
   - Contraseña: password
   - Rol: SUPERVISOR (supervisión y reportes)

4. USUARIO 1:
   - Email: ana.martinez@empresa.com
   - Contraseña: password
   - Rol: USUARIO (crear y gestionar propias incidencias)

5. USUARIO 2:
   - Email: luis.rodriguez@empresa.com
   - Contraseña: password
   - Rol: USUARIO (crear y gestionar propias incidencias)

FUNCIONALIDADES DISPONIBLES PARA PROBAR:
- Login con diferentes roles
- Crear, editar y gestionar incidencias
- Sistema de comentarios (internos y externos)
- Notificaciones en tiempo real
- Favoritos y borradores
- Herramientas de análisis visual
- Filtros y búsquedas
- Dashboard con estadísticas
*/ 