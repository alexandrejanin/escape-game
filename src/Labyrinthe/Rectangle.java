package Labyrinthe;

final class Rectangle {
    private int x, y, largeur, hauteur;

    Rectangle(int x, int y, int largeur, int hauteur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getLargeur() {
        return largeur;
    }

    int getHauteur() {
        return hauteur;
    }

    int getDroite() {
        return x + largeur;
    }

    int getGauche() {
        return x;
    }

    int getHaut() {
        return y;
    }

    int getBas() {
        return y + hauteur;
    }
}
