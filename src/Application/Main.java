package Application;

import Labyrinthe.Labyrinthe;

import java.io.IOException;

public final class Main {

    public static void main(String[] args) {
        //Joueur joueur = new Joueur();

        Labyrinthe labyrinthe = new Labyrinthe(40, 40, 25, 50, .1);

        while (true) {
            System.out.println(labyrinthe.affichage());

            // Attend d'appuyer sur entrée pour continuer
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

            labyrinthe.step();
        }
    }
}
