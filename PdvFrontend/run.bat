@echo off
chcp 65001 >nul
echo ========================================
echo   PDV Posto de Combustivel - Frontend
echo ========================================
echo.

cd /d "%~dp0"

echo [1/2] Limpando classes antigas...
if exist com rmdir /s /q com

echo [1.1] Gerando lista de fontes Java...
if exist .sources.txt del /q .sources.txt
for /R src %%f in (*.java) do @echo %%f>>.sources.txt

echo [1.2] Compilando aplicacao...
javac -encoding UTF-8 -d . -sourcepath src @.sources.txt
if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Falha na compilacao!
    pause
    if exist .sources.txt del /q .sources.txt
    exit /b 1
)
if exist .sources.txt del /q .sources.txt

echo [2/2] Iniciando aplicacao...
echo.
java -cp . com.br.pdvpostocombustivelfrontend.frontend.Main

if %errorlevel% neq 0 (!
    echo.
    echo [ERRO] Falha ao executar a aplicacao!
    pause
    exit /b 1
)
