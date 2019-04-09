public abstract class Obstacle extends Entite{

    public abstract boolean peutPasser(Animal animal);

    public Obstacle(Vecteur position, Character car){
        super(position, car);
    }
}