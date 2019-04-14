package Entites;

import Labyrinthe.Labyrinthe;
import Utilitaires.AStar;
import Utilitaires.Random;
import Utilitaires.Vecteur;

import java.util.ArrayList;

public final class Animal extends Entite {

    private static final int rayonObs = 6;
    private final String nom;
    private final Character car;
    private final int taille;
    private final String[] proies;

    private int appetit;

    private Animal(Vecteur position, String nom, Character car, int appetit, int taille, String[] proies) {
        super(position);
        this.nom = nom;
        this.car = car;
        this.appetit = appetit;
        this.taille = taille;
        this.proies = proies;
    }

    // Renvoie un animal aléatoire
    public static Animal aleatoire(Vecteur position) {
        double random = Math.random();

        if (random < .20) {
            return lion(position);
        } else if (random < .50) {
            return gazelle(position);
        } else if (random < .80) {
            return zebu(position);
        } else {
            return lapin(position);
        }
    }

    public static Animal lion(Vecteur position) {
        return new Animal(
                position,
                "Lion",
                'L',
                150,
                200,
                new String[]{"Gazelle", "Zebu", "Lapin"}
        );
    }

    public static Animal gazelle(Vecteur position) {
        return new Animal(
                position,
                "Gazelle",
                'G',
                80,
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
                50,
                new String[]{"Carotte"}
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

    public boolean peutManger(Entite entite) {
        for (String proie : proies) {
            if (proie.equals(entite.getNom())) {
                return true;
            }
        }
        return false;
    }

    // Renvoie l'animal a manger
    public Vecteur step(Labyrinthe labyrinthe) {
        Entite proiePlusProche = null;
        for (Entite entite : labyrinthe.getEntites()) {

            double distance = position.distance(entite.position);

            // Entite trop loin, on passe
            if (distance > rayonObs) {
                continue;
            }

            // Predateur proche, on s'eloigne
            if (entite instanceof Animal && ((Animal) entite).peutManger(this)) {
                Vecteur prochainePos = new Vecteur(position);
                if (entite.position.x < position.x && labyrinthe.peutBouger(prochainePos.x + 1, prochainePos.y, this)) {
                    prochainePos.x++;
                } else if (entite.position.x > position.x && labyrinthe.peutBouger(prochainePos.x - 1, prochainePos.y, this)) {
                    prochainePos.x--;
                }
                if (entite.position.y < position.y && labyrinthe.peutBouger(prochainePos.x, prochainePos.y + 1, this)) {
                    prochainePos.y++;
                } else if (entite.position.y > position.y && labyrinthe.peutBouger(prochainePos.x, prochainePos.y - 1, this)) {
                    prochainePos.y--;
                }
                return prochainePos;
            }
            // Proie proche
            else if (peutManger(entite)) {
                // Adjacente: on bouge sur sa case pour la manger
                if (distance < 2) {
                    return entite.getPosition();
                } // Sinon, on cherche seulement la proie la plus proche
                else if (proiePlusProche == null || distance < position.distance(proiePlusProche.position)) {
                    proiePlusProche = entite;
                }
            }
        }

        // On cherche un chemin vers la proie la plus proche
        if (proiePlusProche != null) {
            ArrayList<Vecteur> chemin = AStar.getChemin(labyrinthe, this, proiePlusProche.getPosition());
            if (chemin != null) return chemin.get(0);
        }


        // Si l'animal n'a rien a faire, il bouge au hasard
        int essais = 0;
        do {
            essais++;
            Vecteur prochainePos = new Vecteur(position);
            prochainePos.x += Random.getInt(-1, 1);
            prochainePos.y += Random.getInt(-1, 1);
            if (labyrinthe.peutBouger(prochainePos, this)) {
                return prochainePos;
            }
        } while (essais < 20);

        return position;
    }

    public String toString() {
        return nom + " " + position;
    }
}
