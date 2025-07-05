@echo off
echo ========================================
echo   INICIANDO HELPDESK INCIDENCIAS
echo ========================================
echo.

echo Iniciando Backend (Spring Boot)...
start "Backend" cmd /k "cd backend && mvn spring-boot:run"

echo.
echo Esperando 10 segundos para que el backend inicie...
timeout /t 10 /nobreak > nul

echo.
echo Iniciando Frontend (Angular)...
start "Frontend" cmd /k "cd frontend && npm install && npm start"

echo.
echo ========================================
echo   APLICACIÓN INICIADA
echo ========================================
echo.
echo Backend: http://localhost:8080
echo Frontend: http://localhost:4200
echo API Docs: http://localhost:8080/swagger-ui.html
echo.
echo Presiona cualquier tecla para cerrar...
pause > nul 