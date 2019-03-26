public abstract class Obstacle extends Entite {
    public abstract boolean peutPasser(Animal animal);

    public abstract String messageErreur();
}
