@echo off
echo ========================================
echo PRUEBA DE FUNCIONAMIENTO - HELPDESK INCIDENCIAS
echo ========================================
echo.

echo [1/4] Verificando Backend...
echo Probando conexión a http://localhost:8080...
curl -s -o nul -w "HTTP Status: %%{http_code}\n" http://localhost:8080/api/auth/test-auth
if %errorlevel% neq 0 (
    echo ERROR: Backend no responde
    echo Asegúrate de que el backend esté ejecutándose en puerto 8080
) else (
    echo Backend OK
)
echo.

echo [2/4] Verificando Frontend...
echo Probando conexión a http://localhost:4200...
curl -s -o nul -w "HTTP Status: %%{http_code}\n" http://localhost:4200
if %errorlevel% neq 0 (
    echo ERROR: Frontend no responde
    echo Asegúrate de que el frontend esté ejecutándose en puerto 4200
) else (
    echo Frontend OK
)
echo.

echo [3/4] Verificando Base de Datos...
echo Probando conexión a MySQL...
mysql -u root -p1234 -e "USE helpdesk_incidencias; SELECT COUNT(*) as 'Usuarios' FROM usuarios;" 2>nul
if %errorlevel% neq 0 (
    echo ADVERTENCIA: No se puede conectar a MySQL
    echo Verifica que MySQL esté ejecutándose
) else (
    echo Base de datos OK
)
echo.

echo [4/4] Verificando Swagger...
echo Probando conexión a Swagger...
curl -s -o nul -w "HTTP Status: %%{http_code}\n" http://localhost:8080/swagger-ui.html
if %errorlevel% neq 0 (
    echo ADVERTENCIA: Swagger no responde
) else (
    echo Swagger OK
)
echo.

echo ========================================
echo RESULTADO DE LA PRUEBA
echo ========================================
echo.
echo Si todos los servicios responden correctamente:
echo ✅ La aplicación está funcionando perfectamente
echo.
echo URLs de acceso:
echo - Frontend: http://localhost:4200
echo - Backend API: http://localhost:8080
echo - Swagger: http://localhost:8080/swagger-ui.html
echo.
echo Credenciales de prueba:
echo - Usuario: admin@helpdesk.com
echo - Contraseña: password
echo.
echo Presiona cualquier tecla para cerrar...
pause >nul 