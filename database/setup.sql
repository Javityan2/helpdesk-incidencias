-- Script de configuración de base de datos para Helpdesk Incidencias
-- Ejecutar este script en MySQL Workbench

-- ===================================
-- CONFIGURACIÓN INICIAL
-- ===================================

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS helpdesk_incidencias
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE helpdesk_incidencias;

-- ===================================
-- VERIFICAR CONFIGURACIÓN
-- ===================================

-- Verificar que la base de datos existe
SHOW DATABASES;

-- Verificar configuración de caracteres
SELECT @@character_set_database, @@collation_database;

-- ===================================
-- CREAR USUARIO DE DESARROLLO (OPCIONAL)
-- ===================================

-- Crear usuario específico para la aplicación
CREATE USER IF NOT EXISTS 'helpdesk_user'@'localhost' IDENTIFIED BY 'helpdesk_password';

-- Otorgar permisos completos
GRANT ALL PRIVILEGES ON helpdesk_incidencias.* TO 'helpdesk_user'@'localhost';

-- Aplicar cambios
FLUSH PRIVILEGES;

-- Verificar usuarios creados
SELECT User, Host FROM mysql.user WHERE User LIKE '%helpdesk%';

-- ===================================
-- VERIFICAR CONEXIÓN
-- ===================================

-- Probar conexión con el usuario de desarrollo
-- (Ejecutar esto después de que Spring Boot cree las tablas)

-- Verificar tablas creadas
-- SHOW TABLES;

-- Verificar estructura de tabla usuarios
-- DESCRIBE usuarios;

-- Verificar datos de prueba
-- SELECT id, empleado_id, nombre, apellido, email, rol, activo FROM usuarios;

-- ===================================
-- COMANDOS ÚTILES PARA DEBUGGING
-- ===================================

-- Verificar estado del servidor
SHOW STATUS LIKE 'Uptime';

-- Verificar variables de configuración
SHOW VARIABLES LIKE 'max_connections';
SHOW VARIABLES LIKE 'wait_timeout';

-- Verificar procesos activos
SHOW PROCESSLIST;

-- ===================================
-- LIMPIEZA (SI ES NECESARIO)
-- ===================================

-- Descomentar si necesitas limpiar la base de datos
-- DROP DATABASE IF EXISTS helpdesk_incidencias;
-- DROP USER IF EXISTS 'helpdesk_user'@'localhost';

-- ===================================
-- NOTAS IMPORTANTES
-- ===================================

/*
1. Este script crea la base de datos y configura un usuario de desarrollo
2. Las tablas se crearán automáticamente cuando ejecutes Spring Boot
3. Los datos de prueba se cargarán desde data.sql
4. Para usar el usuario de desarrollo, actualiza application.properties:
   spring.datasource.username=helpdesk_user
   spring.datasource.password=helpdesk_password
*/ 