package Entites;

import Utilitaires.Random;
import Utilitaires.Vecteur;

public final class Comestible extends Entite {

    private final String nom;
    private final Character car;

    private Comestible(Vecteur position, String nom, Character car) {
        super(position);
        this.nom = nom;
        this.car = car;
    }

    public static Comestible aleatoire(Vecteur position) {
        if (Random.getBoolean(.3)) {
            return insectes(position);
        } else {
            return herbes(position);
        }
    }

    private static Comestible herbes(Vecteur position) {
        return new Comestible(position, "Herbes", ',');
    }

    private static Comestible insectes(Vecteur position) {
        return new Comestible(position, "Insectes", '.');
    }

    public static String listeComestibles() {
        Vecteur v = new Vecteur(0, 0);
        StringBuilder string = new StringBuilder("Comestibles:\n");
        for (Comestible comestible : new Comestible[]{herbes(v), insectes(v)}) {
            string.append(comestible.getNom()).append(": ").append(comestible.getCar()).append('\n');
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
