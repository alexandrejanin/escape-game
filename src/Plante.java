public class Plante extends Entite{
    
    private String nom;

    private Plante(Vecteur position,String nom){
        super(position);
        this.nom = nom;
    }
    
    private static Plante herbes(Vecteur position){
        return new Plante(position,"Herbes");
    }
    
    private static Plante carotte(Vecteur position){
        return new Plante(position,"Carotte");
    }
}