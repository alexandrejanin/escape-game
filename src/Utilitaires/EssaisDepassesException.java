package Utilitaires;

public final class EssaisDepassesException extends RuntimeException {
    private final String message;

    public EssaisDepassesException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
