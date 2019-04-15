package Entites;

import Labyrinthe.Labyrinthe;
import Utilitaires.Vecteur;

public interface IJoueTour {
    Vecteur joueTour(Labyrinthe labyrinthe);

    void bouge(Labyrinthe labyrinthe, Vecteur position);
}
