public final class Rocher extends Obstacle{

    private int tailleMin;

    public Rocher(Point position, int tailleMin){
        super(position);
        this.tailleMin = tailleMin;
    }

    public boolean peutPasser(Animal animal){
        return animal.getTaille() >= tailleMin;
    }
    
}