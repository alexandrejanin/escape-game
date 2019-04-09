package Application;

import Entites.Entite;
import Labyrinthe.Labyrinthe;

import java.util.ArrayList;

class Affichage {

    static String affichage(Labyrinthe labyrinthe, ArrayList<Entite> entites) {
        StringBuilder string = new StringBuilder();
        for (int y = 0; y < labyrinthe.getHauteur(); y++) {
            for (int x = 0; x < labyrinthe.getLargeur(); x++) {
                boolean entitePresente = false;

                for (Entite entite : entites) {
                    if (entite.getX() == x && entite.getY() == y) {
                        string.append(entite.getCar());
                        entitePresente = true;
                        break;
                    }
                }

                // Si il n'y a pas d'animaux, on affiche le sol ou mur
                if (!entitePresente) {
                    switch (labyrinthe.getCase(x, y)) {
                        case Mur:
                            string.append("#");
                            break;
                        case Sol:
                            string.append(" ");
                            break;
                    }
                }
            }
            string.append("\n");
        }
        return string.toString();
    }
}
