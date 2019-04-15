package Application;

import Entites.Animal;
import Entites.Obstacle;
import Entites.Plante;
import Labyrinthe.Labyrinthe;

public final class Main {
    public static void main(String[] args) {
        System.out.println("Joueur: J");
        System.out.println("Sortie: ♦\n");
        System.out.println(Plante.listePlantes());
        System.out.println(Animal.listeAnimaux());
        System.out.println(Obstacle.listeObstacles());

        Joueur joueur = new Joueur();

        Labyrinthe labyrinthe = new Labyrinthe(40, 40, 25, 50, .1, joueur);

        do {
            System.out.println(labyrinthe.affichage(joueur));
        } while (!labyrinthe.joueTour(joueur));
    }
}
