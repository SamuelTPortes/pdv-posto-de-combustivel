@echo off
chcp 65001 >nul
echo ========================================
echo   PDV - Iniciando Backend
echo ========================================
echo.
echo [INFO] Certifique-se que o PostgreSQL esta instalado e rodando!
echo [INFO] Banco de dados: pdv_posto
echo.
echo Aguarde o backend iniciar...
echo.

cd /d "%~dp0"

.\mvnw.cmd spring-boot:run

pause

