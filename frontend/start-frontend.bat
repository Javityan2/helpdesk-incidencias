@echo off
echo ========================================
echo   INICIANDO FRONTEND ANGULAR
echo ========================================
echo.

echo Instalando dependencias...
call npm install

echo.
echo Iniciando servidor de desarrollo...
call npm start

echo.
echo ========================================
echo   FRONTEND INICIADO
echo ========================================
echo.
echo La aplicación estará disponible en:
echo http://localhost:4200
echo.
pause 