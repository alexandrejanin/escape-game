package Entites;

import Utilitaires.Vecteur;

public abstract class Entite {

    protected Vecteur position;

    Entite(Vecteur position) {
        this.position = position;
    }

    public Vecteur getPosition() {
        return position;
    }

    public void setPosition(Vecteur position) {
        if (position != null) this.position = position;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public abstract Character getCar();

    public abstract String getNom();

}
