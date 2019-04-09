package Entites;

import Utilitaires.Vecteur;

public class Plante extends Entite {

    private final String nom;
    private final Character car;

    private Plante(Vecteur position, String nom, Character car) {
        super(position);
        this.nom = nom;
        this.car = car;
    }

    @Override
    public Character getCar() {
        return car;
    }

    public String getNom() {
        return nom;
    }

    private static Plante herbes(Vecteur position) {
        return new Plante(position, "Herbes", '|');
    }

    private static Plante carotte(Vecteur position) {
        return new Plante(position, "Carotte", '!');
    }
}
