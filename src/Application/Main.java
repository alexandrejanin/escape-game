package Application;

import Labyrinthe.Labyrinthe;

public final class Main {
    public static void main(String[] args) {
        Joueur joueur = new Joueur();

        Labyrinthe labyrinthe = new Labyrinthe(40, 40, 25, 50, .1, joueur);

        do {
            System.out.println(labyrinthe.affichage(joueur));
        } while (!labyrinthe.step(joueur));
    }
}
