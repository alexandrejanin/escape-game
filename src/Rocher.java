public class Rocher extends Obstacle {
    private final int tailleMin = 50;

    public boolean peutPasser(Animal animal) {
        return animal.getTaille() >= tailleMin;
    }

    public String messageErreur() {
        return "L'animal est trop petit pour passer au-dessus du rocher";
    }
}
