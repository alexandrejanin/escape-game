package Entites;

import Utilitaires.Vecteur;

abstract class Obstacle extends Entite {

    public abstract boolean peutPasser(Animal animal);

    Obstacle(Vecteur position) {
        super(position);
    }
}
