public final class Rocher extends Obstacle{

    private int tailleMin;

    public Rocher(Vecteur position, int tailleMin){
        super(position);
        this.tailleMin = tailleMin;
    }

    public boolean peutPasser(Animal animal){
        return animal.getTaille() >= tailleMin;
    }
    
}