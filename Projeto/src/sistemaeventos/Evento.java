package sistemaeventos;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

public class Evento {
    private String id;
    private String nome;
    private String endereco;
    private CategoriaEvento categoria;
    private LocalDateTime horario;
    private int duracaoMinutos;
    private String descricao;

    public Evento(String nome, String endereco, CategoriaEvento categoria,
                  LocalDateTime horario, int duracaoMinutos, String descricao) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.duracaoMinutos = duracaoMinutos;
        this.descricao = descricao;
    }

    public Evento(String id, String nome, String endereco, CategoriaEvento categoria,
                  LocalDateTime horario, int duracaoMinutos, String descricao) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.duracaoMinutos = duracaoMinutos;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public CategoriaEvento getCategoria() {
        return categoria;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean estaOcorrendoAgora() {
        LocalDateTime agora = LocalDateTime.now();
        return (agora.isEqual(horario) || agora.isAfter(horario))
                && agora.isBefore(horario.plusMinutes(duracaoMinutos));
    }

    public boolean jaOcorreu() {
        return LocalDateTime.now().isAfter(horario.plusMinutes(duracaoMinutos));
    }

    public String getStatus() {
        if (estaOcorrendoAgora()) {
            return "ACONTECENDO AGORA";
        }
        if (jaOcorreu()) {
            return "JA OCORREU";
        }
        return "PROXIMO EVENTO";
    }

    private static String encode(String valor) {
        return Base64.getEncoder().encodeToString(valor.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String valor) {
        return new String(Base64.getDecoder().decode(valor), StandardCharsets.UTF_8);
    }

    public String toLine() {
        return String.join("|",
                id,
                encode(nome),
                encode(endereco),
                categoria.name(),
                horario.toString(),
                String.valueOf(duracaoMinutos),
                encode(descricao)
        );
    }

    public static Evento fromLine(String line) {
        String[] partes = line.split("\\|");
        if (partes.length < 7) {
            throw new IllegalArgumentException("Linha inválida para evento.");
        }

        return new Evento(
                partes[0],
                decode(partes[1]),
                decode(partes[2]),
                CategoriaEvento.valueOf(partes[3]),
                LocalDateTime.parse(partes[4]),
                Integer.parseInt(partes[5]),
                decode(partes[6])
        );
    }
}
