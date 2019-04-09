package Application;

import Entites.Animal;
import Entites.Entite;
import Labyrinthe.Labyrinthe;
import Labyrinthe.Case;
import Utilitaires.Random;
import Utilitaires.Vecteur;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Joueur joueur = new Joueur();

        Labyrinthe labyrinthe = new Labyrinthe(50, 50);

        ArrayList<Entite> entites = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            int x, y;
            do {
                x = Random.getInt(0, labyrinthe.getLargeur() - 1);
                y = Random.getInt(0, labyrinthe.getHauteur() - 1);
            } while (labyrinthe.getCase(x, y) == Case.Mur);

            entites.add(Animal.aleatoire(new Vecteur(x, y)));
        }

        System.out.println(Affichage.affichage(labyrinthe, entites));

        while (true) {
            ArrayList<Entite> entitesAManger = new ArrayList<>();

            for (Entite entite : entites) {
                if (entite instanceof Animal) {
                    Entite proie = ((Animal) entite).step(entites, labyrinthe);
                    if (proie != null) entitesAManger.add(proie);
                }
            }

            for (Entite entite : entitesAManger) {
                entites.remove(entite);
            }

            System.out.println(Affichage.affichage(labyrinthe, entites));

            // Attend d'appuyer sur entrée pour continuer
            System.in.read();
        }
    }
}
