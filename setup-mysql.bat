@echo off
echo ========================================
echo CONFIGURACION DE MYSQL - HELPDESK
echo ========================================
echo.

echo [1/3] Conectando a MySQL...
echo Por favor, ingresa la contraseña de root cuando se solicite:
echo.

mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS helpdesk_incidencias CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

if %errorlevel% neq 0 (
    echo ERROR: No se pudo crear la base de datos
    echo Verifica que MySQL esté ejecutándose y la contraseña sea correcta
    pause
    exit /b 1
)

echo ✓ Base de datos creada exitosamente
echo.

echo [2/3] Creando usuario de desarrollo...
mysql -u root -p -e "CREATE USER IF NOT EXISTS 'helpdesk_user'@'localhost' IDENTIFIED BY 'helpdesk_password';"

if %errorlevel% neq 0 (
    echo ERROR: No se pudo crear el usuario
    pause
    exit /b 1
)

echo ✓ Usuario creado exitosamente
echo.

echo [3/3] Otorgando permisos...
mysql -u root -p -e "GRANT ALL PRIVILEGES ON helpdesk_incidencias.* TO 'helpdesk_user'@'localhost'; FLUSH PRIVILEGES;"

if %errorlevel% neq 0 (
    echo ERROR: No se pudieron otorgar permisos
    pause
    exit /b 1
)

echo ✓ Permisos otorgados exitosamente
echo.

echo ========================================
echo ✓ CONFIGURACION DE MYSQL COMPLETADA
echo ========================================
echo.
echo Base de datos: helpdesk_incidencias
echo Usuario: helpdesk_user
echo Contraseña: helpdesk_password
echo.
echo Ahora puedes ejecutar: start-full-stack.bat
echo.
pause 