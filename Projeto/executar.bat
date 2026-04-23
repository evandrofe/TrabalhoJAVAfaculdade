@echo off
REM Script para executar o projeto SistemaEventos
REM Uso: Abra este arquivo ou execute no PowerShell/CMD

cd /d "%~dp0"

REM Verifica se o diretório bin existe
if not exist "bin" (
    echo.
    echo ERRO: Diretor bin nao encontrado!
    echo Execute primeiro: compilar.bat
    echo.
    pause
    exit /b 1
)

REM Verifica se a classe compilada existe
if not exist "bin\sistemaeventos\Main.class" (
    echo.
    echo ERRO: Main.class nao foi encontrada!
    echo Execute primeiro: compilar.bat
    echo.
    pause
    exit /b 1
)

REM Executa o programa
echo.
echo ======================================
echo   Sistema de Eventos da Cidade
echo ======================================
echo.

java -cp bin sistemaeventos.Main

echo.
echo ======================================
echo   Programa finalizado
echo ======================================
pause
