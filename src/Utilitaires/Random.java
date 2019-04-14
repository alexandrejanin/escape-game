package Utilitaires;

public final class Random {

    // Renvoie un boolean avec `proba` chances d'etre true
    public static boolean getBoolean(double proba) {
        return Math.random() < proba;
    }

    // Renvoie un entier entre min et max, inclus
    public static int getInt(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }

    public static Vecteur getPointDansCercle(Vecteur centre, int rayon) {
        double angle = Math.random() * 2 * Math.PI;
        double dist = Math.random() * rayon;

        int x = (int) Math.round(dist * Math.cos(angle));
        int y = (int) Math.round(dist * Math.sin(angle));

        return new Vecteur(x, y);
    }
}
