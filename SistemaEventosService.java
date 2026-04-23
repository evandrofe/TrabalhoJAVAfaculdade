import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SistemaEventosService {
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private List<Evento> eventos;
    private Usuario usuario;

    public SistemaEventosService() {
        this.eventoRepository = new EventoRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.eventos = eventoRepository.carregar();
        this.usuario = usuarioRepository.carregar();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean usuarioCadastrado() {
        return usuario != null;
    }

    public void cadastrarUsuario(String nome, String email, String telefone, String cidade) {
        this.usuario = new Usuario(nome, email, telefone, cidade);
        usuarioRepository.salvar(usuario);
    }

    public void cadastrarEvento(String nome, String endereco, CategoriaEvento categoria,
                                LocalDateTime horario, int duracaoMinutos, String descricao) {
        Evento evento = new Evento(nome, endereco, categoria, horario, duracaoMinutos, descricao);
        eventos.add(evento);
        salvarEventos();
    }

    public List<Evento> listarEventosOrdenados() {
        List<Evento> copia = new ArrayList<>(eventos);
        copia.sort(Comparator.comparing(Evento::getHorario));
        return copia;
    }

    public List<Evento> listarEventosConfirmados() {
        List<Evento> confirmados = new ArrayList<>();
        if (usuario == null) {
            return confirmados;
        }

        for (Evento evento : eventos) {
            if (usuario.participaDoEvento(evento.getId())) {
                confirmados.add(evento);
            }
        }

        confirmados.sort(Comparator.comparing(Evento::getHorario));
        return confirmados;
    }

    public boolean confirmarParticipacao(int indice) {
        List<Evento> ordenados = listarEventosOrdenados();

        if (usuario == null || indice < 0 || indice >= ordenados.size()) {
            return false;
        }

        Evento evento = ordenados.get(indice);
        usuario.confirmarEvento(evento.getId());
        salvarUsuario();
        return true;
    }

    public boolean cancelarParticipacao(int indice) {
        List<Evento> confirmados = listarEventosConfirmados();

        if (usuario == null || indice < 0 || indice >= confirmados.size()) {
            return false;
        }

        Evento evento = confirmados.get(indice);
        usuario.cancelarEvento(evento.getId());
        salvarUsuario();
        return true;
    }

    public void salvarTudo() {
        salvarEventos();
        salvarUsuario();
    }

    private void salvarEventos() {
        eventoRepository.salvar(eventos);
    }

    private void salvarUsuario() {
        if (usuario != null) {
            usuarioRepository.salvar(usuario);
        }
    }
}