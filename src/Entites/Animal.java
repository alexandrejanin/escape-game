package Entites;

import Labyrinthe.Labyrinthe;
import Utilitaires.Random;
import Utilitaires.Vecteur;

import java.util.ArrayList;

public final class Animal extends Entite {

    private static final int rayonObs = 6;
    private final String nom;
    private final Character car;
    private final int mobilite;
    private final int taille;
    private final String[] proies;
    private int appetit;

    private Animal(Vecteur position, String nom, Character car, int appetit, int mobilite, int taille, String[] proies) {
        super(position);
        this.nom = nom;
        this.car = car;
        this.appetit = appetit;
        this.mobilite = mobilite;
        this.taille = taille;
        this.proies = proies;
    }

    public static Animal aleatoire(Vecteur position) {
        switch (Random.getInt(1, 4)) {
            case 1:
                return lion(position);
            case 2:
                return gazelle(position);
            case 3:
                return zebu(position);
            case 4:
                return lapin(position);
        }
        return null;
    }

    public static Animal lion(Vecteur position) {
        return new Animal(
                position,
                "Lion",
                'L',
                150,
                140,
                200,
                new String[]{"Gazelle", "Zebu"}
        );
    }

    public static Animal gazelle(Vecteur position) {
        return new Animal(
                position,
                "Gazelle",
                'G',
                80,
                100,
                120,
                new String[]{"Herbes"}
        );
    }

    public static Animal zebu(Vecteur position) {
        return new Animal(
                position,
                "Zebu",
                'Z',
                110,
                200,
                300,
                new String[]{"Herbes"}
        );
    }

    public static Animal lapin(Vecteur position) {
        return new Animal(
                position,
                "Lapin",
                'l',
                40,
                100,
                50,
                new String[]{"Herbes", "Carotte"}
        );
    }

    @Override
    public Character getCar() {
        return car;
    }

    @Override
    public String getNom() {
        return nom;
    }

    int getTaille() {
        return taille;
    }

    private boolean peutManger(Entite entite) {
        for (String proie : proies) {
            if (proie.equals(entite.getNom())) {
                return true;
            }
        }
        return false;
    }

    // Renvoie l'animal a manger
    public Entite step(ArrayList<Entite> entites, Labyrinthe labyrinthe) {
        for (Entite entite : entites) {

            double distance = position.distance(entite.position);

            if (distance > rayonObs) {
                continue;
            }

            if (!(entite instanceof Animal)) {
                continue;
            }

            Animal animal = (Animal) entite;

            // Predateur a distance d'observation, on s'eloigne
            if (animal.peutManger(this)) {
                if (animal.position.x < position.x && labyrinthe.getCaseLibre(position.x + 1, position.y)) {
                    position.x++;
                } else if (animal.position.x > position.x && labyrinthe.getCaseLibre(position.x - 1, position.y)) {
                    position.x--;
                }
                if (animal.position.y < position.y && labyrinthe.getCaseLibre(position.x, position.y + 1)) {
                    position.y++;
                } else if (animal.position.y > position.y && labyrinthe.getCaseLibre(position.x, position.y - 1)) {
                    position.y--;
                }
                return null;
            } else if (peutManger(animal)) {
                // Proie adjacente, on bouge sur sa position et on la mange
                if (distance < 2) {
                    position.x = animal.position.x;
                    position.y = animal.position.y;
                    return animal;
                }

                // Proie a distance d'observation, on s'en rapproche
                if (animal.position.x < position.x && labyrinthe.getCaseLibre(position.x - 1, position.y)) {
                    position.x--;
                } else if (animal.position.x > position.x && labyrinthe.getCaseLibre(position.x + 1, position.y)) {
                    position.x++;
                }
                if (animal.position.y < position.y && labyrinthe.getCaseLibre(position.x, position.y - 1)) {
                    position.y--;
                } else if (animal.position.y > position.y && labyrinthe.getCaseLibre(position.x, position.y + 1)) {
                    position.y++;
                }
                return null;
            }
        }

        int prochainX, prochainY;
        do {
            prochainX = position.x - 1 + Random.getInt(0, 2);
            prochainY = position.y - 1 + Random.getInt(0, 2);
        } while (!labyrinthe.getCaseLibre(prochainX, prochainY));

        position.x = prochainX;
        position.y = prochainY;

        return null;
    }

    public String toString() {
        String ret = "Type : " + nom + ", position : " + position + ", appetit :  " + appetit + ", mobilite : " + mobilite + ", taille : " + taille + ", peut manger : ";
        for (String nom : proies) {
            ret += nom + ", ";
        }
        return ret;
    }
}
