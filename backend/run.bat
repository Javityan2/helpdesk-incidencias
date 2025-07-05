@echo off
echo Iniciando Helpdesk Incidencias...
echo.

REM Verificar que Java esté instalado
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java no está instalado o no está en el PATH
    echo Por favor instala Java 17 o superior
    pause
    exit /b 1
)

REM Verificar que Maven esté instalado
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven no está instalado o no está en el PATH
    echo Por favor instala Maven o usa tu IDE para ejecutar el proyecto
    pause
    exit /b 1
)

echo Java y Maven encontrados. Iniciando aplicación...
echo.
mvn spring-boot:run

pause 