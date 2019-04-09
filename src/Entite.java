public abstract class Entite{

    protected Vecteur position;
    private final Character car;
    
    protected Entite(Vecteur position, Character car){
        this.position = position;
        this.car = car;
    }

    public Vecteur getPosition(){
        return position;
    }

    public int getX(){
        return position.x;
    }

    public int getY(){
        return position.y;
    }

    public Character getCar(){
        return car;
    }
    
    public abstract String getNom();

}