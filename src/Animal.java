public final class Animal extends Entite{
    
    private final String nom;
    private final Character car;
    private int appetit;
    private final int mobilite;
    private final int taille;
    private final String[] proies;

    private Animal(Vecteur position, String nom, Character car, int appetit, int mobilite, int taille, String[] proies){
        super(position);
        this.nom = nom;
        this.car = car;
        this.appetit = appetit;
        this.mobilite = mobilite;
        this.taille = taille;
        this.proies = proies;
    }

    public String getNom(){
        return nom;
    }

    public int getTaille(){
        return taille;
    }

    public Character getCar(){
        return car;
    }

    public static Animal aleatoire(Vecteur position){
        switch (Random.getInt(1, 4)){
            case 1: return lion(position);
            case 2: return gazelle(position);
            case 3: return zebu(position);
            case 4: return lapin(position);
        }
        return null;
    }

    public static Animal lion(Vecteur position){
        return new Animal(
            position,
            "Lion",
            'L',
            150,
            140,
            200,
            new String[]{"Gazelle", "Zebu"}
        );
    }

    public static Animal gazelle(Vecteur position){
        return new Animal(
            position,
            "Gazelle",
            'G',
            80,
            100,
            120,
            new String[]{"Herbes"}
        );
    }

    public static Animal zebu(Vecteur position){
        return new Animal(
            position,
            "Zebu",
            'Z',
            110,
            200,
            300,
            new String[]{"Herbes"}
        );
    }

    public static Animal lapin(Vecteur position){
        return new Animal(
            position,
            "Lapin",
            'l',
            40,
            100,
            50,
            new String[]{"Herbes", "Carotte"}
        );
    }

    public String toString(){
        String ret = "Type : " + nom + ", position : " + position +  ", appetit :  " + appetit + ", mobilite : " + mobilite + ", taille : " + taille + ", peut manger : ";
        for (String nom : proies){
            ret += nom + ", ";
        }
        return ret;
    }
}