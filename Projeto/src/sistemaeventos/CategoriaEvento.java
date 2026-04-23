package sistemaeventos;

public enum CategoriaEvento {
    FESTA,
    ESPORTIVO,
    SHOW,
    FEIRA,
    PALESTRA,
    TEATRO,
    OUTROS;

    public static CategoriaEvento fromInt(int opcao) {
        return switch (opcao) {
            case 1 -> FESTA;
            case 2 -> ESPORTIVO;
            case 3 -> SHOW;
            case 4 -> FEIRA;
            case 5 -> PALESTRA;
            case 6 -> TEATRO;
            default -> OUTROS;
        };
    }
}
