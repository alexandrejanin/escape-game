public abstract class Obstacle extends Entite{

    public abstract boolean peutPasser(Animal animal);

    public Obstacle(Point position){
        super(position);
    }
}