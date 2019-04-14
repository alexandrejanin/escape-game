/*
D'apres 'How to Use BSP Trees to Generate Game Maps'
https://gamedevelopment.tutsplus.com/tutorials/how-to-use-bsp-trees-to-generate-game-maps--gamedev-12268
 */

package Labyrinthe;

import Entites.Animal;
import Entites.Entite;
import Entites.Plante;
import Utilitaires.Random;
import Utilitaires.Vecteur;

import java.util.ArrayList;

public final class Labyrinthe {

    // Parametres de génération de labyrinthe
    private final static int MAX_LEAF_SIZE = 16;
    private final static int MIN_LEAF_SIZE = 6;

    private final ArrayList<Entite> entites;
    private final Case[][] labyrinthe;
    StringBuilder string = new StringBuilder();

    public Labyrinthe(int largeur, int hauteur, int nbAnimaux, int nbPlantes, double probaObstacle) {
        entites = new ArrayList<>();
        labyrinthe = new Case[hauteur][largeur];

        // Crée le labyrinthe
        ArrayList<Leaf> leafs = new ArrayList<>();
        Leaf racine = new Leaf(0, 0, largeur, hauteur);

        leafs.add(racine);

        boolean aSplit = true;
        while (aSplit) {
            aSplit = false;
            for (int i = 0; i < leafs.size(); i++) {
                Leaf leaf = leafs.get(i);
                if (leaf.getGauche() == null && leaf.getDroite() == null) {
                    if (leaf.getLargeur() > MAX_LEAF_SIZE || leaf.getHauteur() > MAX_LEAF_SIZE || Random.getBoolean(0.25)) {
                        if (leaf.split(MIN_LEAF_SIZE)) {
                            leafs.add(leaf.getGauche());
                            leafs.add(leaf.getDroite());
                            aSplit = true;
                        }
                    }
                }
            }
        }
        racine.creerSalle();

        // Initialise la grille
        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                labyrinthe[y][x] = Case.Mur;
            }
        }

        // Convertit de liste de Leafs à une grille de Mur/Sol
        for (Leaf leaf : leafs) {
            Rectangle salle = leaf.getSalle();
            if (salle != null) {
                int xMax = salle.getX() + salle.getLargeur();
                int yMax = salle.getY() + salle.getHauteur();
                for (int x = salle.getX(); x < xMax; x++) {
                    for (int y = salle.getY(); y < yMax; y++) {
                        labyrinthe[y][x] = Case.Sol;
                    }
                }
            }

            ArrayList<Rectangle> couloirs = leaf.getCouloir();
            if (couloirs != null) {
                for (Rectangle rect : couloirs) {
                    int xMax = rect.getX() + rect.getLargeur();
                    int yMax = rect.getY() + rect.getHauteur();
                    for (int x = rect.getX(); x < xMax; x++) {
                        for (int y = rect.getY(); y < yMax; y++) {
                            labyrinthe[y][x] = Case.Sol;
                        }
                    }
                }
            }

        }

        // Ajoute plantes
        for (int i = 0; i < nbPlantes; i++) {
            int x, y;
            do {
                x = Random.getInt(0, largeur - 1);
                y = Random.getInt(0, hauteur - 1);
            } while (getCase(x, y) == Case.Mur);

            entites.add(Plante.aleatoire(new Vecteur(x, y)));
        }

        // Ajoute animaux
        for (int i = 0; i < nbAnimaux; i++) {
            int x, y;
            do {
                x = Random.getInt(0, largeur - 1);
                y = Random.getInt(0, hauteur - 1);
            } while (getCase(x, y) == Case.Mur);

            entites.add(Animal.aleatoire(new Vecteur(x, y)));
        }
    }

    public int getLargeur() {
        return labyrinthe[0].length;
    }

    public int getHauteur() {
        return labyrinthe.length;
    }

    public Case getCase(int x, int y) {
        return labyrinthe[y][x];
    }

    public boolean peutBouger(Vecteur vecteur, Animal animal) {
        return peutBouger(vecteur.x, vecteur.y, animal);
    }

    public boolean peutBouger(int x, int y, Animal animal) {

        if (x < 0 || x >= getLargeur() || y < 0 || y >= getHauteur()) return false;
        if (labyrinthe[y][x] == Case.Mur) return false;

        for (Entite entite : entites) {
            if (entite instanceof Animal && entite != animal && entite.getX() == x && entite.getY() == y) {
                return animal.peutManger(entite);
            }
        }

        return true;
    }

    // Step toutes les entitees
    public void step() {
        ArrayList<Entite> entitesAManger = new ArrayList<>();

        for (Entite entite : entites) {
            if (entite instanceof Animal) {
                Entite proie = ((Animal) entite).step(entites, this);
                if (proie != null) entitesAManger.add(proie);
            }
        }

        for (Entite entite : entitesAManger) {
            entites.remove(entite);
        }
    }

    public String affichage() {
        Character[][] characters = new Character[getHauteur()][getLargeur()];

        // Affiche labyrinthe
        for (int y = 0; y < getHauteur(); y++) {
            for (int x = 0; x < getLargeur(); x++) {
                switch (getCase(x, y)) {
                    case Mur:
                        characters[y][x] = '#';
                        break;
                    case Sol:
                        characters[y][x] = ' ';
                        break;
                }
            }
        }

        // Affiche plantes
        for (Entite entite : entites) {
            if (entite instanceof Plante) {
                characters[entite.getY()][entite.getX()] = entite.getCar();
            }
        }

        // Affiche animaux
        for (Entite entite : entites) {
            if (entite instanceof Animal) {
                characters[entite.getY()][entite.getX()] = entite.getCar();
            }
        }

        // Tableau -> string
        StringBuilder string = new StringBuilder();
        for (int y = 0; y < getHauteur(); y++) {
            for (int x = 0; x < getLargeur(); x++) {
                string.append(characters[y][x]);
            }
            string.append("\n");
        }

        return string.toString();
    }
}
