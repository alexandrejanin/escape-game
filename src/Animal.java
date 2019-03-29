public final class Animal extends Entite{
    
    private final String nom;
    private int appetit;
    private final int mobilite;
    private final int taille;
    private final String[] proies;

    private Animal(Point position, String nom, int appetit, int mobilite, int taille, String[] proies){
        super(position);
        this.nom = nom;
        this.appetit = appetit;
        this.mobilite = mobilite;
        this.taille = taille;
        this.proies = proies;
    }

    public static Animal Lion(Point position){
        return new Animal(
            position,
            "Lion",
            100,
            140,
            200,
            new String[]{"Gazelle", "Zebu"}
        );
    }

    public static Animal Gazelle(Point position){
        return new Animal(
            position,
            "Gazelle",
            80,
            100,
            120,
            new String[]{"Herbes"}
        );
    }

    public String getNom(){
        return nom;
    }

    public int getTaille(){
        return taille;
    }

    public String toString(){
        String ret = "Type : " + nom + ", position : " + position +  ", appetit :  " + appetit + ", mobilite : " + mobilite + ", taille : " + taille + ", peut manger : ";
        for (String nom : proies){
            ret += nom + ", ";
        }
        return ret;
    }
}