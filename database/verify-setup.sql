-- Script de verificación de configuración de base de datos
-- Ejecutar este script en MySQL Workbench para verificar que todo esté configurado correctamente

USE helpdesk_incidencias;

-- Verificar que la base de datos existe y está en uso
SELECT DATABASE() as 'Base de datos actual';

-- Verificar que las tablas existen
SHOW TABLES;

-- Verificar estructura de la tabla usuarios
DESCRIBE usuarios;

-- Verificar que hay datos de usuarios
SELECT COUNT(*) as 'Total de usuarios' FROM usuarios;

-- Verificar usuarios específicos
SELECT id, empleado_id, nombre, apellido, email, rol, activo 
FROM usuarios 
WHERE empleado_id IN ('ADMIN001', 'USER001', 'TECH001')
ORDER BY empleado_id;

-- Verificar estructura de la tabla incidencias
DESCRIBE incidencias;

-- Verificar que hay datos de incidencias
SELECT COUNT(*) as 'Total de incidencias' FROM incidencias;

-- Verificar configuración de caracteres
SELECT @@character_set_database, @@collation_database;

-- Verificar variables de configuración importantes
SHOW VARIABLES LIKE 'max_connections';
SHOW VARIABLES LIKE 'wait_timeout';
SHOW VARIABLES LIKE 'interactive_timeout';

-- Verificar estado del servidor
SHOW STATUS LIKE 'Uptime';
SHOW STATUS LIKE 'Threads_connected';

-- Verificar permisos del usuario
SHOW GRANTS FOR CURRENT_USER();

-- Verificar procesos activos (últimos 10)
SELECT * FROM information_schema.processlist 
WHERE db = 'helpdesk_incidencias' 
ORDER BY time DESC 
LIMIT 10; 