public class Vecteur{

    public int x;
    public int y;

    public Vecteur(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + x +"," + y + ")";
    }

    public double distance(Vecteur vecteur){
        return Math.sqrt((vecteur.x - x) * (vecteur.x - x) + (vecteur.y - y) * (vecteur.y - y));
    }
}