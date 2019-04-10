package Utilitaires;

import java.util.Objects;

public final class Vecteur {

    public int x;
    public int y;

    public Vecteur(Vecteur v) {
        x = v.x;
        y = v.y;
    }

    public Vecteur(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Vecteur vecteur) {
        return Math.sqrt((vecteur.x - x) * (vecteur.x - x) + (vecteur.y - y) * (vecteur.y - y));
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vecteur)) return false;
        Vecteur vecteur = (Vecteur) o;
        return x == vecteur.x &&
                y == vecteur.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
