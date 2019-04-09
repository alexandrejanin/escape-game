public final class Trou extends Obstacle{

    private int tailleMax;

    public Trou(Vecteur position, int tailleMax){
        super(position, 'O');
        this.tailleMax = tailleMax;
    }

    public boolean peutPasser(Animal animal){
        return animal.getTaille() <= tailleMax;
    }

    public String getNom(){
        return "Trou";
    }
}