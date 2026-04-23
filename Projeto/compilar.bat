@echo off
REM Script para compilar o projeto SistemaEventos
REM Uso: Abra este arquivo ou execute no PowerShell/CMD

echo ======================================
echo   Compilando Sistema de Eventos
echo ======================================

cd /d "%~dp0"

REM Verifica se o javac esta disponivel
javac -version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ERRO: javac nao foi encontrado!
    echo Certifique-se de que o Java Development Kit (JDK) esta instalado
    echo e que a variavel JAVA_HOME esta configurada corretamente.
    echo.
    pause
    exit /b 1
)

REM Cria o diretorio bin se nao existir
if not exist "bin" mkdir bin

REM Compila os arquivos Java
echo.
echo Compilando arquivos Java...
javac -d bin src\sistemaeventos\*.java

if %errorlevel% neq 0 (
    echo.
    echo ERRO na compilacao!
    pause
    exit /b 1
)

echo.
echo ======================================
echo   Compilacao concluida com sucesso!
echo ======================================
echo.
echo Para executar o programa, use:
echo   java -cp bin sistemaeventos.Main
echo.
pause
