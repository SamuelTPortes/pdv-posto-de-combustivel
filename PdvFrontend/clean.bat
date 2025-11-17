@echo off
chcp 65001 >nul
echo ========================================
echo   Limpando arquivos compilados...
echo ========================================
echo.

cd /d "%~dp0src"

if exist com\br\pdvpostocombustivelfrontend\frontend\*.class (
    del /s /q com\br\pdvpostocombustivelfrontend\frontend\*.class >nul 2>&1
    echo Arquivos .class removidos com sucesso!
) else (
    echo Nenhum arquivo .class encontrado.
)

echo.
pause

