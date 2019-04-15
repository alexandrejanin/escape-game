package Entites;

import Utilitaires.Random;
import Utilitaires.Vecteur;

public abstract class Obstacle extends Entite {

    Obstacle(Vecteur position) {
        super(position);
    }

    // Renvoie un obstacle aléatoire
    public static Obstacle aleatoire(Vecteur position) {
        if (Random.getBoolean(.5)) {
            return new Trou(position);
        } else {
            return new Rocher(position);
        }
    }

    public static String listeObstacles() {
        Vecteur v = new Vecteur(0, 0);
        StringBuilder string = new StringBuilder("Obstacles:\n");
        for (Obstacle obstacle : new Obstacle[]{new Rocher(v), new Trou(v)}) {
            string.append(obstacle.getNom()).append(": ").append(obstacle.getCar()).append('\n');
        }
        return string.toString();
    }

    public abstract boolean peutPasser(Animal animal);
}
