public class Trou extends Obstacle {
    private final int tailleMax = 25;

    public boolean peutPasser(Animal animal) {
        return animal.getTaille() <= tailleMax;
    }

    public String messageErreur() {
        return "L'animal est trop grand pour passer dans le trou";
    }
}
