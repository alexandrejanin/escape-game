package Entites;

public class PositionNullException extends Exception {
    private final Entite entite;

    public PositionNullException(Entite entite) {
        this.entite = entite;
    }

    public Entite getEntite() {
        return entite;
    }
}
