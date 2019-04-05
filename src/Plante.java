public class Plante extends Entite{
    
    private String nom;

    private Plante(Vecteur position,String nom){
        super(position);
        this.nom = nom;
    }
    
    private static Plante Herbes(Vecteur position){
        return new Plante(position,"Herbes");
    }
}