import java.io.IOException;
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

        ArrayList<Entite> entites = new ArrayList<Entite>();
        for (int i = 0; i < 25; i++){
            int x, y;
            do {
                x = Random.getInt(0, labyrinthe.getLargeur() - 1);
                y = Random.getInt(0, labyrinthe.getLongueur() - 1);
            } while (labyrinthe.getCase(x, y) == Case.Mur);

            entites.add(Animal.aleatoire(new Vecteur(x, y)));
        }

        System.out.println(Affichage.affichage(labyrinthe, entites));

        while (true){
            ArrayList<Entite> entitesAManger = new ArrayList<Entite>();

            for (Entite entite : entites){
                Animal animal = entite instanceof Animal ? (Animal) entite : null;
            
                Entite proie = animal.step(entites, labyrinthe);
                if (proie != null) entitesAManger.add(proie);
            }

            for (Entite entite : entitesAManger){
                entites.remove(entite);
            }

            System.out.println(Affichage.affichage(labyrinthe, entites));
            try{
                System.in.read();
            } catch (IOException e){
                System.out.println(e);
            }
        }
    }
}