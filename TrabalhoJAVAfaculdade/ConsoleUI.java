import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final SistemaEventosService service;
    private final Scanner scanner;
    private final DateTimeFormatter formatter;

    public ConsoleUI() {
        this.service = new SistemaEventosService();
        this.scanner = new Scanner(System.in);
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    public void iniciar() {
        System.out.println("======================================");
        System.out.println("   SISTEMA DE EVENTOS DA CIDADE");
        System.out.println("======================================");

        if (!service.usuarioCadastrado()) {
            System.out.println("\nNenhum usuário cadastrado.");
            cadastrarUsuario();
        } else {
            Usuario usuario = service.getUsuario();
            System.out.println("\nBem-vindo, " + usuario.getNome() + "!");
            System.out.println("Cidade: " + usuario.getCidade());
        }

        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> cadastrarEvento();
                case 3 -> listarEventos();
                case 4 -> confirmarParticipacao();
                case 5 -> listarEventosConfirmados();
                case 6 -> cancelarParticipacao();
                case 7 -> {
                    service.salvarTudo();
                    System.out.println("Dados salvos com sucesso.");
                    System.out.println("Saindo do sistema...");
                }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 7);
    }

    private void exibirMenu() {
        System.out.println("\n--------------- MENU ---------------");
        System.out.println("1 - Cadastrar/Atualizar usuário");
        System.out.println("2 - Cadastrar evento");
        System.out.println("3 - Listar todos os eventos");
        System.out.println("4 - Confirmar participação em evento");
        System.out.println("5 - Ver eventos confirmados");
        System.out.println("6 - Cancelar participação");
        System.out.println("7 - Salvar e sair");
        System.out.println("------------------------------------");
    }

    private void cadastrarUsuario() {
        System.out.println("\n--- Cadastro de usuário ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        service.cadastrarUsuario(nome, email, telefone, cidade);
        System.out.println("Usuário cadastrado com sucesso.");
    }

    private void cadastrarEvento() {
        System.out.println("\n--- Cadastro de evento ---");

        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        CategoriaEvento categoria = escolherCategoria();

        System.out.print("Horário (dd/MM/yyyy HH:mm): ");
        String horarioTexto = scanner.nextLine();
        LocalDateTime horario = LocalDateTime.parse(horarioTexto, formatter);

        int duracao = lerInt("Duração em minutos: ");

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        service.cadastrarEvento(nome, endereco, categoria, horario, duracao, descricao);
        System.out.println("Evento cadastrado com sucesso.");
    }

    private CategoriaEvento escolherCategoria() {
        System.out.println("Categorias:");
        System.out.println("1 - Festa");
        System.out.println("2 - Esportivo");
        System.out.println("3 - Show");
        System.out.println("4 - Feira");
        System.out.println("5 - Palestra");
        System.out.println("6 - Teatro");
        System.out.println("7 - Outros");

        int opcao = lerInt("Escolha a categoria: ");
        return CategoriaEvento.fromInt(opcao);
    }

    private void listarEventos() {
        System.out.println("\n--- Lista de eventos ---");
        List<Evento> eventos = service.listarEventosOrdenados();

        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            System.out.println("\n[" + (i + 1) + "] " + e.getNome());
            System.out.println("Categoria: " + e.getCategoria());
            System.out.println("Endereço: " + e.getEndereco());
            System.out.println("Horário: " + e.getHorario().format(formatter));
            System.out.println("Duração: " + e.getDuracaoMinutos() + " min");
            System.out.println("Descrição: " + e.getDescricao());
            System.out.println("Status: " + e.getStatus());
        }
    }

    private void confirmarParticipacao() {
        List<Evento> eventos = service.listarEventosOrdenados();

        if (eventos.isEmpty()) {
            System.out.println("Não há eventos para confirmar.");
            return;
        }

        listarEventos();
        int numero = lerInt("\nDigite o número do evento para confirmar participação: ");

        boolean ok = service.confirmarParticipacao(numero - 1);
        if (ok) {
            System.out.println("Participação confirmada com sucesso.");
        } else {
            System.out.println("Não foi possível confirmar participação.");
        }
    }

    private void listarEventosConfirmados() {
        System.out.println("\n--- Eventos confirmados ---");
        List<Evento> eventos = service.listarEventosConfirmados();

        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento confirmado.");
            return;
        }

        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            System.out.println("\n[" + (i + 1) + "] " + e.getNome());
            System.out.println("Categoria: " + e.getCategoria());
            System.out.println("Endereço: " + e.getEndereco());
            System.out.println("Horário: " + e.getHorario().format(formatter));
            System.out.println("Descrição: " + e.getDescricao());
            System.out.println("Status: " + e.getStatus());
        }
    }

    private void cancelarParticipacao() {
        List<Evento> eventos = service.listarEventosConfirmados();

        if (eventos.isEmpty()) {
            System.out.println("Não há participações para cancelar.");
            return;
        }

        listarEventosConfirmados();
        int numero = lerInt("\nDigite o número do evento para cancelar participação: ");

        boolean ok = service.cancelarParticipacao(numero - 1);
        if (ok) {
            System.out.println("Participação cancelada com sucesso.");
        } else {
            System.out.println("Não foi possível cancelar participação.");
        }
    }

    private int lerInt(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}