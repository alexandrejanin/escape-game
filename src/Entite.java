public abstract class Entite{

    protected Vecteur position;
    
    protected Entite(Vecteur position){
        this.position = position;
    }

    public Vecteur getPosition(){
        return position;
    }

    public int getX(){
        return position.getX();
    }

    public int getY(){
        return position.getY();
    }
}