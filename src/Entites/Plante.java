package Entites;

import Utilitaires.Random;
import Utilitaires.Vecteur;

public final class Plante extends Entite {

    private final String nom;
    private final Character car;

    private Plante(Vecteur position, String nom, Character car) {
        super(position);
        this.nom = nom;
        this.car = car;
    }

    public static Plante aleatoire(Vecteur position) {
        if (Random.getBoolean(.3)) {
            return carotte(position);
        } else {
            return herbes(position);
        }
    }

    private static Plante herbes(Vecteur position) {
        return new Plante(position, "Herbes", ',');
    }

    private static Plante carotte(Vecteur position) {
        return new Plante(position, "Carotte", '!');
    }

    public static String listePlantes() {
        Vecteur v = new Vecteur(0, 0);
        StringBuilder string = new StringBuilder("Plantes:\n");
        for (Plante plante : new Plante[]{herbes(v), carotte(v)}) {
            string.append(plante.getNom()).append(": ").append(plante.getCar()).append('\n');
        }
        return string.toString();
    }


    @Override
    public Character getCar() {
        return car;
    }

    public String getNom() {
        return nom;
    }
}
