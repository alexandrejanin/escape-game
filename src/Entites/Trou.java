package Entites;

import Utilitaires.Vecteur;

public final class Trou extends Obstacle {

    private int tailleMax;

    public Trou(Vecteur position) {
        this(position, 100);
    }

    public Trou(Vecteur position, int tailleMax) {
        super(position);
        this.tailleMax = tailleMax;
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
