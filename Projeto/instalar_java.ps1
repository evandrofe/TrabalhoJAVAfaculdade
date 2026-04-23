# Script PowerShell para instalar Java JDK automaticamente
# Execute como Administrador

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "   Instalacao Automatica do Java JDK" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se está executando como administrador
$currentUser = [Security.Principal.WindowsIdentity]::GetCurrent()
$principal = New-Object Security.Principal.WindowsPrincipal($currentUser)
$adminRole = [Security.Principal.WindowsBuiltInRole]::Administrator

if (-not $principal.IsInRole($adminRole)) {
    Write-Host "ERRO: Execute este script como Administrador!" -ForegroundColor Red
    Write-Host "Clique com botao direito no PowerShell e selecione 'Executar como administrador'" -ForegroundColor Red
    Write-Host ""
    Write-Host "Pressione qualquer tecla para continuar..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 1
}

$jdkUrl = "https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.10%2B7/OpenJDK17U-jdk_x64_windows_hotspot_17.0.10_7.zip"
$tempDir = "C:\Temp"
$jdkZip = "$tempDir\jdk17.zip"
$jdkDir = "$tempDir\jdk17"
$installDir = "C:\Java\jdk17"

Write-Host "Verificando se o Java ja esta instalado..." -ForegroundColor Yellow
try {
    $javaVersion = & java -version 2>&1
    Write-Host "Java ja esta instalado:" -ForegroundColor Green
    Write-Host $javaVersion -ForegroundColor White
    Write-Host ""
    Write-Host "Pressione qualquer tecla para continuar..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 0
} catch {
    Write-Host "Java nao encontrado. Iniciando instalacao..." -ForegroundColor Yellow
}

# Criar diretório temporário
if (!(Test-Path $tempDir)) {
    New-Item -ItemType Directory -Path $tempDir | Out-Null
}

# Baixar JDK
Write-Host ""
Write-Host "Baixando JDK 17..." -ForegroundColor Yellow
try {
    Invoke-WebRequest -Uri $jdkUrl -OutFile $jdkZip -UseBasicParsing
    Write-Host "Download concluido." -ForegroundColor Green
} catch {
    Write-Host "ERRO no download: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# Extrair JDK
Write-Host ""
Write-Host "Extraindo JDK..." -ForegroundColor Yellow
try {
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    [System.IO.Compression.ZipFile]::ExtractToDirectory($jdkZip, $jdkDir)
    Write-Host "Extracao concluida." -ForegroundColor Green
} catch {
    Write-Host "ERRO na extracao: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# Mover para local permanente
Write-Host ""
Write-Host "Instalando JDK em $installDir..." -ForegroundColor Yellow
if (!(Test-Path "C:\Java")) {
    New-Item -ItemType Directory -Path "C:\Java" | Out-Null
}

# Encontrar o diretório extraído
$extractedDir = Get-ChildItem $jdkDir | Where-Object { $_.PSIsContainer } | Select-Object -First 1
if ($extractedDir) {
    Move-Item $extractedDir.FullName $installDir -Force
    Write-Host "Instalacao concluida." -ForegroundColor Green
} else {
    Write-Host "ERRO: Nao foi possivel encontrar o diretorio extraido." -ForegroundColor Red
    exit 1
}

# Configurar variáveis de ambiente
Write-Host ""
Write-Host "Configurando variaveis de ambiente..." -ForegroundColor Yellow

# JAVA_HOME
[System.Environment]::SetEnvironmentVariable("JAVA_HOME", $installDir, [System.EnvironmentVariableTarget]::Machine)

# Adicionar ao PATH
$currentPath = [System.Environment]::GetEnvironmentVariable("Path", [System.EnvironmentVariableTarget]::Machine)
if ($currentPath -notlike "*$installDir\bin*") {
    $newPath = "$currentPath;$installDir\bin"
    [System.Environment]::SetEnvironmentVariable("Path", $newPath, [System.EnvironmentVariableTarget]::Machine)
}

# Limpar arquivos temporários
Write-Host ""
Write-Host "Limpando arquivos temporarios..." -ForegroundColor Yellow
Remove-Item $jdkZip -Force -ErrorAction SilentlyContinue
Remove-Item $jdkDir -Recurse -Force -ErrorAction SilentlyContinue

# Testar instalação
Write-Host ""
Write-Host "Testando instalacao..." -ForegroundColor Yellow
try {
    $javaVersion = & java -version 2>&1
    $javacVersion = & javac -version 2>&1

    Write-Host "=====================================" -ForegroundColor Green
    Write-Host "   Java JDK instalado com sucesso!" -ForegroundColor Green
    Write-Host "=====================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Java Version:" -ForegroundColor Cyan
    Write-Host $javaVersion -ForegroundColor White
    Write-Host ""
    Write-Host "Java Compiler Version:" -ForegroundColor Cyan
    Write-Host $javacVersion -ForegroundColor White
    Write-Host ""
    Write-Host "JAVA_HOME: $installDir" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Agora voce pode compilar e executar o projeto!" -ForegroundColor Green
} catch {
    Write-Host "ERRO: Instalacao falhou. Verifique as variaveis de ambiente." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Pressione qualquer tecla para continuar..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")