package Entites;

import Utilitaires.Vecteur;

public final class Trou extends Obstacle {

    private int tailleMax;

    public Trou(Vecteur position) {
        super(position);
        tailleMax = 100;
    }


    public boolean peutPasser(Animal animal) {
        return animal.getTaille() <= tailleMax;
    }

    @Override
    public Character getCar() {
        return 'O';
    }

    @Override
    public String getNom() {
        return "Trou";
    }
}
