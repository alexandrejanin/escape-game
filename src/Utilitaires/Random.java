package Utilitaires;

public final class Random {

    // Renvoie un boolean avec `proba` chances d'etre true
    public static boolean getBoolean(double proba) {
        return Math.random() < proba;
    }

    // Renvoie un entier entre min et max, inclus
    public static int getInt(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }
}
