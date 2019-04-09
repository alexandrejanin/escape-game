package Entites;

import Utilitaires.Vecteur;

public final class Rocher extends Obstacle {

    private int tailleMin;

    public Rocher(Vecteur position, int tailleMin) {
        super(position);
        this.tailleMin = tailleMin;
    }

    public boolean peutPasser(Animal animal) {
        return animal.getTaille() >= tailleMin;
    }

    @Override
    public Character getCar() {
        return '*';
    }

    @Override
    public String getNom() {
        return "Rocher";
    }

}
