public class Plante extends Entite{
    
    private String nom;

    private Plante(Vecteur position,String nom, Character car){
        super(position, car);
        this.nom = nom;
    }

    public String getNom(){
        return nom;
    }
    
    private static Plante herbes(Vecteur position){
        return new Plante(position,"Herbes", '|');
    }
    
    private static Plante carotte(Vecteur position){
        return new Plante(position,"Carotte", '!');
    }
}