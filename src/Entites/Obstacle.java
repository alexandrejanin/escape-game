package Entites;

import Utilitaires.Random;
import Utilitaires.Vecteur;

public abstract class Obstacle extends Entite {

    Obstacle(Vecteur position) {
        super(position);
    }

    // Renvoie un obstacle aléatoire
    public static Obstacle aleatoire(Vecteur position) {
        if (Random.getBoolean(.5)) {
            return new Trou(position);
        } else {
            return new Rocher(position);
        }
    }

    public abstract boolean peutPasser(Animal animal);
}
