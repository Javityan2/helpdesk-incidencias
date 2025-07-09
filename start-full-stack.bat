@echo off
echo ========================================
echo INICIANDO HELPDESK INCIDENCIAS
echo ========================================
echo.

echo [1/4] Verificando Java...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java no está instalado o no está en el PATH
    pause
    exit /b 1
)
echo Java OK
echo.

echo [2/4] Verificando Node.js...
node --version
if %errorlevel% neq 0 (
    echo ERROR: Node.js no está instalado o no está en el PATH
    pause
    exit /b 1
)
echo Node.js OK
echo.

echo [3/4] Verificando MySQL...
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ADVERTENCIA: MySQL no está en el PATH, pero puede estar funcionando
) else (
    echo MySQL OK
)
echo.

echo [4/4] Iniciando aplicaciones...
echo.

echo Iniciando Backend (Spring Boot)...
start "Backend - Spring Boot" cmd /k "cd backend && mvn spring-boot:run"

echo Esperando 10 segundos para que el backend se inicie...
timeout /t 10 /nobreak >nul

echo Iniciando Frontend (Angular)...
start "Frontend - Angular" cmd /k "cd frontend && npm start"

echo.
echo ========================================
echo APLICACIÓN INICIADA
echo ========================================
echo.
echo Backend: http://localhost:8080
echo Swagger: http://localhost:8080/swagger-ui.html
echo Frontend: http://localhost:4200
echo.
echo Presiona cualquier tecla para cerrar esta ventana...
pause >nul 