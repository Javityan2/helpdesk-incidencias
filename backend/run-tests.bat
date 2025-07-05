@echo off
echo ========================================
echo   EJECUTANDO PRUEBAS DEL BACKEND
echo ========================================
echo.

echo Limpiando y compilando el proyecto...
call mvn clean compile

echo.
echo Ejecutando pruebas unitarias...
call mvn test

echo.
echo ========================================
echo   PRUEBAS COMPLETADAS
echo ========================================
echo.
echo Para ver el reporte de cobertura:
echo call mvn jacoco:report
echo.
pause 