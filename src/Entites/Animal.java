package Entites;

import Labyrinthe.Labyrinthe;
import Utilitaires.AStar;
import Utilitaires.Random;
import Utilitaires.Vecteur;

import java.util.ArrayList;

public final class Animal extends Entite implements IJoueTour {

    public static final int RAYON_VISION = 7;
    private final String nom;
    private final Character car;
    private final int taille;
    private final String[] proies;

    private Animal(Vecteur position, String nom, Character car, int taille, String[] proies) {
        super(position);
        this.nom = nom;
        this.car = car;
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
            return suricate(position);
        }
    }

    public static Animal lion(Vecteur position) {
        return new Animal(
                position,
                "Lion",
                'L',
                200,
                new String[]{"Gazelle", "Zebu", "Lapin"}
        );
    }

    public static Animal gazelle(Vecteur position) {
        return new Animal(
                position,
                "Gazelle",
                'G',
                120,
                new String[]{"Herbes"}
        );
    }

    public static Animal zebu(Vecteur position) {
        return new Animal(
                position,
                "Zebu",
                'Z',
                300,
                new String[]{"Herbes"}
        );
    }

    public static Animal suricate(Vecteur position) {
        return new Animal(
                position,
                "Suricate",
                'S',
                50,
                new String[]{"Insectes"}
        );
    }

    public static String listeAnimaux() {
        Vecteur v = new Vecteur(0, 0);
        StringBuilder string = new StringBuilder("Animaux:\n");
        for (Animal animal : new Animal[]{lion(v), gazelle(v), zebu(v), suricate(v)}) {
            string.append(animal.getNom()).append(": ").append(animal.getCar()).append(" (mange:");
            for (String proie : animal.getProies()) {
                string.append(" ").append(proie);
            }
            string.append(")\n");
        }
        return string.toString();
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

    public String[] getProies() {
        return proies.clone();
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
    @Override
    public Vecteur joueTour(Labyrinthe labyrinthe) {
        Entite proiePlusProche = null;
        for (Entite entite : labyrinthe.getEntites()) {

            double distance = position.distance(entite.position);

            // Entite trop loin, on passe
            if (distance > RAYON_VISION) {
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

    @Override
    public void bouge(Labyrinthe labyrinthe, Vecteur position) {
        if (labyrinthe.peutBouger(position, this)) {
            setPosition(position);
        }
    }

    public String toString() {
        return nom + " " + position;
    }
}
