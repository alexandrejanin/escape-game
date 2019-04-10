package Entites;

import Utilitaires.Random;
import Utilitaires.Vecteur;

abstract class Obstacle extends Entite {

    Obstacle(Vecteur position) {
        super(position);
    }

    public static Obstacle aleatoire(Vecteur position) {
        switch (Random.getInt(1, 2)) {
            case 1:
                return new Trou(position);
            case 2:
                return new Rocher(position);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public abstract boolean peutPasser(Animal animal);
}
