import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UsuarioRepository {
    private static final String ARQUIVO = "user.data";

    public Usuario carregar() {
        Path path = Path.of(ARQUIVO);

        if (!Files.exists(path)) {
            return null;
        }

        try {
            String linha = Files.readString(path).trim();
            if (linha.isBlank()) {
                return null;
            }
            return Usuario.fromLine(linha);
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuário: " + e.getMessage());
            return null;
        }
    }

    public void salvar(Usuario usuario) {
        Path path = Path.of(ARQUIVO);

        try {
            Files.writeString(path, usuario.toLine());
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }
}