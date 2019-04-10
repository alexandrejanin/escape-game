package Utilitaires;

import Entites.Animal;
import Labyrinthe.Labyrinthe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AStar {
    public static ArrayList<Vecteur> getChemin(Labyrinthe labyrinthe, Animal animal, Vecteur but) {
        HashSet<Vecteur> fermé = new HashSet<>();
        HashSet<Vecteur> ouvert = new HashSet<>();
        HashMap<Vecteur, Vecteur> precedent = new HashMap<>();
        HashMap<Vecteur, Integer> gScore = new HashMap<>();
        HashMap<Vecteur, Integer> fScore = new HashMap<>();

        Vecteur depart = animal.getPosition();

        ouvert.add(depart);
        gScore.put(depart, 0);
        fScore.put(depart, h(depart, but));

        while (!ouvert.isEmpty()) {
            // Case actuelle avec le plus petit fScore
            Vecteur pos = minFScore(ouvert, fScore);

            if (pos.equals(but)) return reconstituerChemin(precedent, pos);

            ouvert.remove(pos);
            fermé.add(pos);

            for (Vecteur voisin : voisins(pos, labyrinthe)) {
                if (fermé.contains(voisin)) continue;

                int g = gScore.get(pos) + 1;

                if (!ouvert.contains(voisin)) {
                    ouvert.add(voisin);
                } else if (g >= gScore.get(voisin)) {
                    continue;
                }

                precedent.put(voisin, pos);
                gScore.put(voisin, g);
                fScore.put(voisin, g + h(voisin, but));
            }

        }

        return null;
    }

    // Estimation de distance
    private static int h(Vecteur a, Vecteur b) {
        return Math.abs(a.x - b.x) * Math.abs(a.x - b.x) + Math.abs(a.y - b.y) * Math.abs(a.y - b.y);
    }

    private static ArrayList<Vecteur> voisins(Vecteur pos, Labyrinthe labyrinthe) {
        ArrayList<Vecteur> voisins = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;

                Vecteur voisin = new Vecteur(pos.x + x, pos.y + y);

                if (!labyrinthe.peutBouger(voisin.x, voisin.y)) continue;

                voisins.add(voisin);
            }
        }

        return voisins;
    }

    // Renvoie la case de `ouvert` ayant le fScore le plus bas
    private static Vecteur minFScore(HashSet<Vecteur> ouvert, HashMap<Vecteur, Integer> fScore) {
        Vecteur minVecteur = null;
        int min = Integer.MAX_VALUE;

        for (Vecteur vecteur : ouvert) {
            int f = fScore.getOrDefault(vecteur, Integer.MAX_VALUE);

            if (minVecteur == null || f < min) {
                minVecteur = vecteur;
                min = f;
            }
        }

        return minVecteur;
    }

    // Reconstitue le chemin en partant du but
    private static ArrayList<Vecteur> reconstituerChemin(HashMap<Vecteur, Vecteur> precedent, Vecteur pos) {
        ArrayList<Vecteur> chemin = new ArrayList<>();
        chemin.add(pos);

        while (precedent.containsKey(pos)) {
            pos = precedent.get(pos);
            chemin.add(0, pos);
        }

        // On enleve la case de depart
        chemin.remove(0);

        return chemin;
    }
}
