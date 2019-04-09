import java.util.ArrayList;

public class Main{

    public static void main(String[] args) {
        Animal[] animauxJouables = new Animal[] {
            Animal.lion(new Vecteur(0, 0)), 
            Animal.gazelle(new Vecteur(0, 0)),
            Animal.zebu(new Vecteur(0, 0))
        };
        
        Joueur joueur = new Joueur(animauxJouables);
        
        Labyrinthe labyrinthe = new Labyrinthe(50,50);

        ArrayList<Animal> animaux = new ArrayList<Animal>();
        for (int i = 0; i < 25; i++){
            int x, y;
            do {
                x = Random.getInt(0, labyrinthe.getLargeur() - 1);
                y = Random.getInt(0, labyrinthe.getLongueur() - 1);
            } while (labyrinthe.getCase(x, y) == Case.Mur);

            animaux.add(Animal.aleatoire(new Vecteur(x, y)));
        }

        System.out.println(Affichage.affichage(labyrinthe, animaux));
    }
}