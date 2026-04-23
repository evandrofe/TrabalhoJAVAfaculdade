package sistemaeventos;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EventoRepository {
    private static final String ARQUIVO = "data/events.data";

    public List<Evento> carregar() {
        List<Evento> eventos = new ArrayList<>();
        Path path = Path.of(ARQUIVO);

        if (!Files.exists(path)) {
            return eventos;
        }

        try {
            List<String> linhas = Files.readAllLines(path);
            for (String linha : linhas) {
                if (!linha.isBlank()) {
                    eventos.add(Evento.fromLine(linha));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar eventos: " + e.getMessage());
        }

        return eventos;
    }

    public void salvar(List<Evento> eventos) {
        Path path = Path.of(ARQUIVO);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Evento evento : eventos) {
                writer.write(evento.toLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar eventos: " + e.getMessage());
        }
    }
}
