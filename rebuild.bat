@echo off
chcp 65001 >nul
echo ========================================
echo   REBUILD - Backend + Frontend
echo ========================================
echo.

echo [1/2] Rebuilding Backend...
cd /d "%~dp0\pdvpostocombustivelbackend"
call mvnw clean package -DskipTests
if %errorlevel% neq 0 (
    echo [ERRO] Falha ao compilar backend!
    pause
    exit /b 1
)
echo [✓] Backend compiled successfully!
echo.

echo [2/2] Rebuilding Frontend...
cd /d "%~dp0\PdvFrontend"
call run.bat
if %errorlevel% neq 0 (
    echo [ERRO] Falha ao compilar frontend!
    pause
    exit /b 1
)

echo.
echo [✓] Rebuild completo!
pause

