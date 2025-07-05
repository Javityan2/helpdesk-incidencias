@echo off
echo ========================================
echo VERIFICACION DEL PROYECTO HELPDESK
echo ========================================
echo.

echo [1/5] Verificando Java...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java no encontrado
    exit /b 1
)
echo ✓ Java encontrado
echo.

echo [2/5] Verificando Maven...
mvn -version
if %errorlevel% neq 0 (
    echo ERROR: Maven no encontrado
    exit /b 1
)
echo ✓ Maven encontrado
echo.

echo [3/5] Verificando Node.js...
node --version
if %errorlevel% neq 0 (
    echo ERROR: Node.js no encontrado
    exit /b 1
)
echo ✓ Node.js encontrado
echo.

echo [4/5] Compilando Backend...
cd backend
mvn clean compile
if %errorlevel% neq 0 (
    echo ERROR: Fallo en compilacion del backend
    exit /b 1
)
echo ✓ Backend compilado correctamente
cd ..
echo.

echo [5/5] Verificando dependencias del Frontend...
cd frontend
npm install
if %errorlevel% neq 0 (
    echo ERROR: Fallo en instalacion de dependencias del frontend
    exit /b 1
)
echo ✓ Dependencias del frontend instaladas
cd ..
echo.

echo ========================================
echo ✓ VERIFICACION COMPLETADA EXITOSAMENTE
echo ========================================
echo.
echo Para ejecutar el proyecto:
echo 1. Configurar MySQL y ejecutar database/setup.sql
echo 2. Ejecutar: start-full-stack.bat
echo 3. Acceder a: http://localhost:4200
echo.
pause 