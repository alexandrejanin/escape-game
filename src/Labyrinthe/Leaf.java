/*
D'apres 'How to Use BSP Trees to Generate Game Maps'
https://gamedevelopment.tutsplus.com/tutorials/how-to-use-bsp-trees-to-generate-game-maps--gamedev-12268
 */

package Labyrinthe;

import Utilitaires.Random;
import Utilitaires.Vecteur;

import java.util.ArrayList;

final class Leaf {

    private int x, y, largeur, hauteur; // position et taille de Leaf
    private Leaf gauche, droite;
    private Rectangle salle;
    private ArrayList<Rectangle> couloir;

    Leaf(int x, int y, int largeur, int hauteur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    boolean split(int minLeafSize) {
        if (gauche != null || droite != null) {
            return false; // c'est déja split
        }

        boolean splitH = Random.getBoolean(0.5);

        if (largeur > hauteur && (double) largeur / hauteur >= 1.25) {
            splitH = false;
        } else if (hauteur > largeur && (double) hauteur / largeur >= 1.25) {
            splitH = true;
        }

        int max = (splitH ? hauteur : largeur) - minLeafSize;
        if (max <= minLeafSize) {
            return false; // zone trop petite à split
        }

        int split = Random.getInt(minLeafSize, max);

        if (splitH) {
            gauche = new Leaf(x, y, largeur, split);
            droite = new Leaf(x, y + split, largeur, hauteur - split);
        } else {
            gauche = new Leaf(x, y, split, hauteur);
            droite = new Leaf(x + split, y, largeur - split, hauteur);
        }

        return true; // split successfully done!!!!1111
    }

    void creerSalle() {
        if (gauche == null && droite == null) {
            int largeurSalle = Random.getInt(3, largeur - 2);
            int hauteurSalle = Random.getInt(3, hauteur - 2);
            int xSalle = Random.getInt(1, largeur - largeurSalle - 1);
            int ySalle = Random.getInt(1, hauteur - hauteurSalle - 1);
            salle = new Rectangle(x + xSalle, y + ySalle, largeurSalle, hauteurSalle);
        } else {
            if (gauche != null) {
                gauche.creerSalle();
            }
            if (droite != null) {
                droite.creerSalle();
            }
            if (gauche != null && droite != null) {
                creerCouloirs(gauche.getSalleRec(), droite.getSalleRec());
            }
        }
    }

    private Rectangle getSalleRec() {
        if (salle != null) {
            return salle;
        } else {
            Rectangle salleGauche = null; //obligé d'initialiser cette valeur car elle est locale
            Rectangle salleDroite = null;
            if (gauche != null) {
                salleGauche = gauche.getSalleRec();
            }
            if (droite != null) {
                salleDroite = droite.getSalleRec();
            }
            if (salleGauche == null && salleDroite == null) {
                return null;
            } else if (salleDroite == null) {
                return salleGauche;
            } else if (salleGauche == null) {
                return salleDroite;
            } else if (Random.getBoolean(0.5)) {
                return salleGauche;
            } else {
                return salleDroite;
            }
        }
    }

    private void creerCouloirs(Rectangle rectGauche, Rectangle rectDroit) {
        couloir = new ArrayList<Rectangle>();
        Vecteur point1 = new Vecteur(Random.getInt(rectGauche.getGauche() + 1,
                rectGauche.getDroite() - 2), Random.getInt(rectGauche.getHaut() + 1, rectGauche.getBas() - 2));
        Vecteur point2 = new Vecteur(Random.getInt(rectDroit.getGauche() + 1,
                rectDroit.getDroite() - 2), Random.getInt(rectDroit.getHaut() + 1, rectDroit.getBas() - 2));

        int width = point2.x - point1.x;
        int height = point2.y - point1.y;

        if (width < 0) {
            if (height < 0) {
                if (Random.getBoolean(0.5)) {
                    couloir.add(new Rectangle(point2.x, point1.y, Math.abs(width), 1));
                    couloir.add(new Rectangle(point2.x, point2.y, 1, Math.abs(height)));
                } else {
                    couloir.add(new Rectangle(point2.x, point2.y, Math.abs(width), 1));
                    couloir.add(new Rectangle(point1.x, point2.y, 1, Math.abs(height)));
                }
            } else if (height > 0) {
                if (Random.getBoolean(0.5)) {
                    couloir.add(new Rectangle(point2.x, point1.y, Math.abs(width), 1));
                    couloir.add(new Rectangle(point2.x, point1.y, 1, Math.abs(height)));
                } else {
                    couloir.add(new Rectangle(point2.x, point2.y, Math.abs(width), 1));
                    couloir.add(new Rectangle(point1.x, point1.y, 1, Math.abs(height)));
                }
            } else {
                couloir.add(new Rectangle(point2.x, point2.y, Math.abs(width), 1));
            }
        } else if (width > 0) {
            if (height < 0) {
                if (Random.getBoolean(0.5)) {
                    couloir.add(new Rectangle(point1.x, point2.y, Math.abs(width), 1));
                    couloir.add(new Rectangle(point1.x, point2.y, 1, Math.abs(height)));
                } else {
                    couloir.add(new Rectangle(point1.x, point1.y, Math.abs(width), 1));
                    couloir.add(new Rectangle(point2.x, point2.y, 1, Math.abs(height)));
                }
            } else if (height > 0) {
                if (Random.getBoolean(0.5)) {
                    couloir.add(new Rectangle(point1.x, point1.y, Math.abs(width), 1));
                    couloir.add(new Rectangle(point2.x, point1.y, 1, Math.abs(height)));
                } else {
                    couloir.add(new Rectangle(point1.x, point2.y, Math.abs(width), 1));
                    couloir.add(new Rectangle(point1.x, point1.y, 1, Math.abs(height)));
                }
            } else {
                couloir.add(new Rectangle(point1.x, point1.y, Math.abs(width), 1));
            }
        } else {
            if (height < 0) {
                couloir.add(new Rectangle(point2.x, point2.y, 1, Math.abs(height)));
            } else if (height > 0) {
                couloir.add(new Rectangle(point1.x, point1.y, 1, Math.abs(height)));
            }
        }
    }

    int getLargeur() {
        return largeur;
    }

    int getHauteur() {
        return hauteur;
    }

    Leaf getGauche() {
        return gauche;
    }

    Leaf getDroite() {
        return droite;
    }

    Rectangle getSalle() {
        return salle;
    }

    ArrayList<Rectangle> getCouloir() {
        return couloir;
    }
}
