# Sistema de Eventos da Cidade

Sistema de gerenciamento de eventos desenvolvido em Java com interface de console.

## Estrutura do Projeto

```
Projeto/
├── src/
│   └── sistemaeventos/
│       ├── Main.java                    # Ponto de entrada da aplicação
│       ├── ConsoleUI.java               # Interface com o usuário
│       ├── SistemaEventosService.java   # Lógica de negócio
│       ├── Evento.java                  # Modelo de evento
│       ├── Usuario.java                 # Modelo de usuário
│       ├── CategoriaEvento.java         # Enum de categorias
│       ├── EventoRepository.java        # Persistência de eventos
│       └── UsuarioRepository.java       # Persistência de usuários
├── data/
│   ├── events.data                      # Arquivo de dados de eventos
│   └── user.data                        # Arquivo de dados do usuário
├── lib/                                 # Dependências (se houver)
├── bin/                                 # Arquivos compilados (gerado)
├── .vscode/                             # Configurações VS Code
├── compilar.bat / compilar.ps1          # Scripts de compilação
├── executar.bat / executar.ps1          # Scripts de execução
├── .gitignore                           # Arquivos ignorados pelo Git
└── README.md                            # Este arquivo
```

## 🚀 Instalação e Configuração

### 1. Instalar Java JDK

O projeto requer Java 11 ou superior. Se você não tem Java instalado:

#### Opção A: Download Automático (Recomendado)
Execute o script PowerShell para instalar automaticamente:
```powershell
# No PowerShell como Administrador
.\instalar_java.ps1
```

#### Opção B: Instalação Manual
1. Baixe o JDK 17+ do [Adoptium](https://adoptium.net/)
2. Extraia para `C:\Temp\jdk17\` ou outro diretório
3. Configure as variáveis de ambiente:

```cmd
setx JAVA_HOME "C:\Temp\jdk17\jdk-17.0.10+7"
setx Path "%Path%;%JAVA_HOME%\bin"
```

### 2. Verificar Instalação
```cmd
java -version
javac -version
```

## Compilação

### Opção 1: Scripts Automáticos (Recomendado)

**Windows (PowerShell):**
```powershell
.\compilar.ps1
```

**Windows (Command Prompt):**
```cmd
compilar.bat
```

### Opção 2: Compilação Manual
```cmd
cd Projeto
mkdir bin
javac -d bin src\sistemaeventos\*.java
```

### Opção 3: VS Code
- Pressione `Ctrl + Shift + B` (já configurado)
- Ou use o menu Terminal > Run Task > Compilar Projeto

## Execução

### Opção 1: Scripts Automáticos (Recomendado)

**Windows (PowerShell):**
```powershell
.\executar.ps1
```

**Windows (Command Prompt):**
```cmd
executar.bat
```

### Opção 2: Execução Manual
```cmd
cd Projeto
java -cp bin sistemaeventos.Main
```

### Opção 3: VS Code
- Pressione `Ctrl + Shift + B` e escolha "Executar Programa"
- Ou use o menu Terminal > Run Task > Executar Programa

## Funcionalidades

- **Cadastro de Usuário**: Registre um novo usuário ou atualize seus dados
- **Cadastro de Eventos**: Crie eventos com data, hora, local e categoria
- **Listagem de Eventos**: Visualize todos os eventos ordenados por data/hora
- **Confirmar Participação**: Confirme sua presença em eventos de interesse
- **Listar Eventos Confirmados**: Veja apenas os eventos que você confirmou
- **Cancelar Participação**: Cancele sua inscrição em um evento
- **Status de Eventos**: Veja o status de cada evento (próximo, acontecendo, já ocorreu)

## Categorias de Eventos

- Festa
- Esportivo
- Show
- Feira
- Palestra
- Teatro
- Outros

## Persistência de Dados

Os dados são armazenados em arquivos de texto simples codificados em Base64 para proteção:
- `data/events.data`: Lista de eventos
- `data/user.data`: Dados do usuário cadastrado

## Requisitos

- Java 11 ou superior
- Windows 10+ ou Linux/macOS
- 2 MB de espaço em disco para dados

## Notas de Uso

- Certifique-se de estar no diretório `Projeto` ao executar o programa
- Os dados são salvos automaticamente ao sair do sistema (opção 7)
- O formato de data/hora é: `dd/MM/yyyy HH:mm` (exemplo: `25/12/2024 19:30`)
- Para caracteres especiais, use codificação UTF-8 no terminal

## 📝 Solução de Problemas

### Erro: "javac não é reconhecido"
- Execute `.\instalar_java.ps1` como Administrador
- Ou configure manualmente as variáveis de ambiente JAVA_HOME e Path

### Erro de Codificação (caracteres estranhos)
- Use terminal com suporte UTF-8
- No Windows: `chcp 65001` antes de executar

### Arquivos não compilam
- Certifique-se de que está no diretório `Projeto`
- Execute `.\compilar.ps1` primeiro

## 🔧 Desenvolvimento

Para modificar o código:
1. Edite os arquivos em `src/sistemaeventos/`
2. Execute `.\compilar.ps1` para compilar
3. Execute `.\executar.ps1` para testar

## 📚 Estrutura de Código

- **Main.java**: Ponto de entrada
- **ConsoleUI.java**: Interface usuário (MVC - View)
- **SistemaEventosService.java**: Lógica negócio (MVC - Controller)
- **Evento.java/Usuario.java**: Modelos de dados (MVC - Model)
- **EventoRepository.java/UsuarioRepository.java**: Camada persistência
