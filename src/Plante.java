public class Plante extends Entite{
    
    private String nom;

    private Plante(Point position,String nom){
        super(position);
        this.nom = nom;
    }
    
    private static Plante Herbes(Point position){
        return new Plante(position,"Herbes");
    }
}