package Entites;

import Utilitaires.Vecteur;

public final class Rocher extends Obstacle {

    private int tailleMin;

    public Rocher(Vecteur position) {
        super(position);
        tailleMin = 120;
    }

    public boolean peutPasser(Animal animal) {
        return animal.getTaille() >= tailleMin;
    }

    @Override
    public Character getCar() {
        return '♣';
    }

    @Override
    public String getNom() {
        return "Rocher";
    }

}
