Usuario
- nome: String
- email: String
- telefone: String
- cidade: String
- eventosConfirmados: Set<String>

Evento
- id: String
- nome: String
- endereco: String
- categoria: CategoriaEvento
- horario: LocalDateTime
- duracaoMinutos: int
- descricao: String

CategoriaEvento
- FESTA
- ESPORTIVO
- SHOW
- FEIRA
- PALESTRA
- TEATRO
- OUTROS

EventoRepository
- carregar(): List<Evento>
- salvar(List<Evento>): void

UsuarioRepository
- carregar(): Usuario
- salvar(Usuario): void

SistemaEventosService
- cadastrarUsuario(...)
- cadastrarEvento(...)
- listarEventosOrdenados()
- listarEventosConfirmados()
- confirmarParticipacao(...)
- cancelarParticipacao(...)
- salvarTudo()

ConsoleUI
- iniciar()
- exibirMenu()
- cadastrarUsuario()
- cadastrarEvento()
- listarEventos()
- confirmarParticipacao()
- cancelarParticipacao()