/*
D'apres 'How to Use BSP Trees to Generate Game Maps'
https://gamedevelopment.tutsplus.com/tutorials/how-to-use-bsp-trees-to-generate-game-maps--gamedev-12268
 */

package Labyrinthe;

import Utilitaires.Random;

import java.util.ArrayList;

public class Labyrinthe {

    private Case[][] labyrinthe;

    public Labyrinthe(int largeurLab, int hauteurLab) {
        this(largeurLab, hauteurLab, 20, 6);
    }

    public Labyrinthe(int largeurLab, int hauteurLab, int maxLeafSize, int minLeafSize) {
        labyrinthe = new Case[hauteurLab][largeurLab]; // a voir
        ArrayList<Leaf> leafs = new ArrayList<>();
        Leaf racine = new Leaf(0, 0, largeurLab, hauteurLab);
        boolean aSplit = true;

        leafs.add(racine);

        while (aSplit) {
            aSplit = false;
            for (int i = 0; i < leafs.size(); i++) {
                Leaf leaf = leafs.get(i);
                if (leaf.getGauche() == null && leaf.getDroite() == null) {
                    if (leaf.getLargeur() > maxLeafSize || leaf.getHauteur() > maxLeafSize || Random.getBoolean(0.25)) {
                        if (leaf.split(minLeafSize)) {
                            leafs.add(leaf.getGauche());
                            leafs.add(leaf.getDroite());
                            aSplit = true;
                        }
                    }
                }
            }
        }
        racine.creerSalle();

        for (int x = 0; x < largeurLab; x++) {
            for (int y = 0; y < hauteurLab; y++) {
                labyrinthe[y][x] = Case.Mur; // on remplit tout de mur
            }
        }

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

    public boolean getCaseLibre(int x, int y) {
        return labyrinthe[y][x] == Case.Sol;
    }
}
