-- Script de verificación para Helpdesk Incidencias
-- Ejecutar DESPUÉS de iniciar Spring Boot
-- Este script verifica que todo esté configurado correctamente

USE helpdesk_incidencias;

-- ===================================
-- VERIFICAR TABLAS CREADAS
-- ===================================

-- Ver todas las tablas
SHOW TABLES;

-- Verificar que las tablas principales existen
SELECT 
    TABLE_NAME,
    TABLE_ROWS,
    DATA_LENGTH,
    INDEX_LENGTH
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = 'helpdesk_incidencias'
ORDER BY TABLE_NAME;

-- ===================================
-- VERIFICAR ESTRUCTURA DE TABLAS
-- ===================================

-- Estructura de la tabla usuarios
DESCRIBE usuarios;

-- Verificar índices de la tabla usuarios
SHOW INDEX FROM usuarios;

-- ===================================
-- VERIFICAR DATOS DE PRUEBA
-- ===================================

-- Contar usuarios creados
SELECT COUNT(*) as total_usuarios FROM usuarios;

-- Ver usuarios activos
SELECT 
    id,
    empleado_id,
    nombre,
    apellido,
    email,
    rol,
    departamento,
    activo,
    fecha_creacion
FROM usuarios 
WHERE activo = true
ORDER BY id;

-- Verificar que las contraseñas estén encriptadas
SELECT 
    empleado_id,
    nombre,
    CASE 
        WHEN password LIKE '$2a$%' THEN 'Encriptada'
        ELSE 'No encriptada'
    END as estado_password
FROM usuarios;

-- ===================================
-- VERIFICAR CONFIGURACIÓN
-- ===================================

-- Verificar configuración de caracteres
SELECT 
    @@character_set_database as charset_db,
    @@collation_database as collation_db,
    @@character_set_server as charset_server,
    @@collation_server as collation_server;

-- Verificar configuración de conexiones
SHOW VARIABLES LIKE 'max_connections';
SHOW VARIABLES LIKE 'wait_timeout';
SHOW VARIABLES LIKE 'interactive_timeout';

-- ===================================
-- VERIFICAR PERMISOS
-- ===================================

-- Verificar permisos del usuario actual
SHOW GRANTS;

-- Verificar usuarios que pueden acceder a esta base de datos
SELECT 
    User,
    Host,
    Db,
    Select_priv,
    Insert_priv,
    Update_priv,
    Delete_priv
FROM mysql.db 
WHERE Db = 'helpdesk_incidencias';

-- ===================================
-- VERIFICAR CONEXIONES ACTIVAS
-- ===================================

-- Ver conexiones activas
SELECT 
    ID,
    USER,
    HOST,
    DB,
    COMMAND,
    TIME,
    STATE
FROM information_schema.PROCESSLIST 
WHERE DB = 'helpdesk_incidencias';

-- ===================================
-- VERIFICAR RENDIMIENTO
-- ===================================

-- Verificar tamaño de la base de datos
SELECT 
    table_schema as 'Database',
    ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) as 'Size (MB)'
FROM information_schema.tables 
WHERE table_schema = 'helpdesk_incidencias'
GROUP BY table_schema;

-- Verificar fragmentación de tablas
SELECT 
    TABLE_NAME,
    TABLE_ROWS,
    DATA_LENGTH,
    INDEX_LENGTH,
    ROUND((DATA_LENGTH + INDEX_LENGTH) / 1024, 2) as 'Size (KB)'
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = 'helpdesk_incidencias'
ORDER BY (DATA_LENGTH + INDEX_LENGTH) DESC;

-- ===================================
-- COMANDOS DE PRUEBA
-- ===================================

-- Probar consulta de usuarios por rol
SELECT 
    rol,
    COUNT(*) as cantidad
FROM usuarios 
GROUP BY rol;

-- Probar consulta de usuarios por departamento
SELECT 
    departamento,
    COUNT(*) as cantidad
FROM usuarios 
GROUP BY departamento;

-- Verificar usuarios técnicos
SELECT 
    id,
    empleado_id,
    nombre,
    apellido,
    departamento,
    cargo
FROM usuarios 
WHERE rol = 'TECNICO' AND activo = true;

-- ===================================
-- RESULTADO ESPERADO
-- ===================================

/*
RESULTADO ESPERADO DESPUÉS DE EJECUTAR SPRING BOOT:

1. Tablas creadas:
   - usuarios
   - incidencias (cuando implementes)
   - comentarios (cuando implementes)
   - historial_incidencias (cuando implementes)

2. Datos de prueba:
   - 6 usuarios creados
   - Contraseñas encriptadas
   - Al menos 1 admin, 2 técnicos, 1 supervisor, 2 usuarios

3. Configuración:
   - Charset: utf8mb4
   - Collation: utf8mb4_unicode_ci
   - Conexiones activas: 1-2

4. Credenciales de prueba:
   - admin@empresa.com / password123
   - juan.perez@empresa.com / password123
   - maria.garcia@empresa.com / password123
*/ 