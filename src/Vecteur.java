public class Vecteur{

    private int x;
    private int y;

    public Vecteur(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + x +"," + y + ")";
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}