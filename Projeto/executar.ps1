# Script PowerShell para executar o projeto SistemaEventos
# Uso: Execute este script no PowerShell

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "   Sistema de Eventos da Cidade" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Verifica se o diretório bin existe
if (!(Test-Path "bin")) {
    Write-Host "ERRO: Diretório bin não encontrado!" -ForegroundColor Red
    Write-Host "Execute primeiro: .\compilar.ps1" -ForegroundColor Red
    Write-Host ""
    Write-Host "Pressione qualquer tecla para continuar..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 1
}

# Verifica se a classe compilada existe
if (!(Test-Path "bin\sistemaeventos\Main.class")) {
    Write-Host "ERRO: Main.class não foi encontrada!" -ForegroundColor Red
    Write-Host "Execute primeiro: .\compilar.ps1" -ForegroundColor Red
    Write-Host ""
    Write-Host "Pressione qualquer tecla para continuar..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 1
}

# Executa o programa
Write-Host "Iniciando o programa..." -ForegroundColor Yellow
Write-Host ""

try {
    & java -cp bin sistemaeventos.Main
} catch {
    Write-Host ""
    Write-Host "ERRO ao executar: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "=====================================" -ForegroundColor Green
Write-Host "   Programa finalizado" -ForegroundColor Green
Write-Host "=====================================" -ForegroundColor Green
Write-Host ""
Write-Host "Pressione qualquer tecla para continuar..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")