import java.util.ArrayList;
public final class Animal extends Entite{
    
    private final String nom;
    private int appetit;
    private final int mobilite;
    private final int taille;
    private final String[] proies;
    private static final int rayonObs = 6;

    private Animal(Vecteur position, String nom, Character car, int appetit, int mobilite, int taille, String[] proies){
        super(position, car);
        this.nom = nom;
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

    private boolean peutManger(Entite entite){
        for(String proie : proies){
            if (proie.equals(entite.getNom())){
                return true;
            }
        }
        return false;
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

    // Renvoie l'animal a manger
    public Entite step(ArrayList<Entite> entites, Labyrinthe labyrinthe){
        for(Entite entite : entites){
            
            double distance = position.distance(entite.position);
            
            if (distance > rayonObs){
                continue;
            }

            Animal animal = entite instanceof Animal ? (Animal) entite : null;
            
            // Predateur a distance d'observation, on s'eloigne
            if (animal.peutManger(this)){
                if (entite.position.x < position.x && labyrinthe.getCaseLibre(position.x + 1, position.y)){
                    position.x++;
                }
                else if (entite.position.x > position.x && labyrinthe.getCaseLibre(position.x - 1, position.y)){
                    position.x--;
                }
                if (entite.position.y < position.y && labyrinthe.getCaseLibre(position.x, position.y + 1)){
                    position.y++;
                }
                else if (entite.position.y > position.y && labyrinthe.getCaseLibre(position.x, position.y - 1)){
                    position.y--;
                }
                return null;
            }
            else if (peutManger(entite)){
                // Proie adjacente, on bouge sur sa position et on la mange
                if(distance < 2){
                    position.x = entite.position.x;
                    position.y = entite.position.y;
                    return entite;
                }

                // Proie a distance d'observation, on s'en rapproche
                if (entite.position.x < position.x && labyrinthe.getCaseLibre(position.x - 1, position.y)){
                    position.x--;
                }
                else if (entite.position.x > position.x && labyrinthe.getCaseLibre(position.x + 1, position.y)){
                    position.x++;
                }
                if (entite.position.y < position.y && labyrinthe.getCaseLibre(position.x, position.y - 1)){
                    position.y--;
                }
                else if (entite.position.y > position.y && labyrinthe.getCaseLibre(position.x, position.y + 1)){
                    position.y++;
                }
                return null;
            }
        }

        int prochainX, prochainY;
        do {
            prochainX = position.x + Random.getInt(-1, 1);
            prochainY = position.y + Random.getInt(-1, 1);
        } while(!labyrinthe.getCaseLibre(prochainX, prochainY));

        position.x = prochainX;
        position.y = prochainY;

        return null;
    }

    public String toString(){
        String ret = "Type : " + nom + ", position : " + position +  ", appetit :  " + appetit + ", mobilite : " + mobilite + ", taille : " + taille + ", peut manger : ";
        for (String nom : proies){
            ret += nom + ", ";
        }
        return ret;
    }
}