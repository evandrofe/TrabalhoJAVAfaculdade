# Script PowerShell para compilar o projeto SistemaEventos
# Uso: Execute este script no PowerShell

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "  Compilando Sistema de Eventos" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Verifica se o javac está disponível
try {
    $javacVersion = & javac -version 2>&1
    Write-Host "Java Compiler encontrado: $javacVersion" -ForegroundColor Green
} catch {
    Write-Host ""
    Write-Host "ERRO: javac não foi encontrado!" -ForegroundColor Red
    Write-Host "Certifique-se de que o Java Development Kit (JDK) está instalado" -ForegroundColor Red
    Write-Host "e que a variável JAVA_HOME está configurada corretamente." -ForegroundColor Red
    Write-Host ""
    Write-Host "Pressione qualquer tecla para continuar..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 1
}

# Cria o diretório bin se não existir
if (!(Test-Path "bin")) {
    New-Item -ItemType Directory -Path "bin" | Out-Null
    Write-Host "Diretório bin criado." -ForegroundColor Yellow
}

# Compila os arquivos Java
Write-Host ""
Write-Host "Compilando arquivos Java..." -ForegroundColor Yellow
try {
    & javac -d bin src\sistemaeventos\*.java
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "=====================================" -ForegroundColor Green
        Write-Host "  Compilação concluída com sucesso!" -ForegroundColor Green
        Write-Host "=====================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "Para executar o programa, use:" -ForegroundColor Cyan
        Write-Host "  .\executar.ps1" -ForegroundColor White
        Write-Host "  ou" -ForegroundColor Cyan
        Write-Host "  java -cp bin sistemaeventos.Main" -ForegroundColor White
    } else {
        Write-Host ""
        Write-Host "ERRO na compilação!" -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host ""
    Write-Host "ERRO na compilação: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Pressione qualquer tecla para continuar..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")