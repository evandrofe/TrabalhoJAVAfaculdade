package sistemaeventos;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

public class Usuario {
    private String nome;
    private String email;
    private String telefone;
    private String cidade;
    private Set<String> eventosConfirmados;

    public Usuario(String nome, String email, String telefone, String cidade) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cidade = cidade;
        this.eventosConfirmados = new HashSet<>();
    }

    public Usuario(String nome, String email, String telefone, String cidade, Set<String> eventosConfirmados) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cidade = cidade;
        this.eventosConfirmados = eventosConfirmados;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public Set<String> getEventosConfirmados() {
        return eventosConfirmados;
    }

    public void confirmarEvento(String eventoId) {
        eventosConfirmados.add(eventoId);
    }

    public void cancelarEvento(String eventoId) {
        eventosConfirmados.remove(eventoId);
    }

    public boolean participaDoEvento(String eventoId) {
        return eventosConfirmados.contains(eventoId);
    }

    private static String encode(String valor) {
        return Base64.getEncoder().encodeToString(valor.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String valor) {
        return new String(Base64.getDecoder().decode(valor), StandardCharsets.UTF_8);
    }

    public String toLine() {
        String ids = String.join(",", eventosConfirmados);
        return String.join("|",
                encode(nome),
                encode(email),
                encode(telefone),
                encode(cidade),
                encode(ids)
        );
    }

    public static Usuario fromLine(String line) {
        String[] partes = line.split("\\|");
        if (partes.length < 5) {
            throw new IllegalArgumentException("Linha inválida para usuário.");
        }

        Set<String> confirmados = new HashSet<>();
        String ids = decode(partes[4]);
        if (!ids.isBlank()) {
            String[] lista = ids.split(",");
            for (String id : lista) {
                if (!id.isBlank()) {
                    confirmados.add(id);
                }
            }
        }

        return new Usuario(
                decode(partes[0]),
                decode(partes[1]),
                decode(partes[2]),
                decode(partes[3]),
                confirmados
        );
    }
}
