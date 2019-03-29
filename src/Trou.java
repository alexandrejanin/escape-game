public final class Trou extends Obstacle{

    private int tailleMax;

    public Trou(Point position, int tailleMax){
        super(position);
        this.tailleMax = tailleMax;
    }
    public boolean peutPasser(Animal animal){
        return animal.getTaille() <= tailleMax;
    }
}