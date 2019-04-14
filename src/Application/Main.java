package Application;

import Labyrinthe.Labyrinthe;

import java.io.IOException;

public final class Main {

    public static void main(String[] args) throws IOException {
        //Joueur joueur = new Joueur();

        Labyrinthe labyrinthe = new Labyrinthe(40, 40, 25, 25, 0.1);

        while (true) {
            System.out.println(labyrinthe.affichage());

            // Attend d'appuyer sur entrée pour continuer
            System.in.read();

            labyrinthe.step();
        }
    }
}
