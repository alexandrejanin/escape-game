public class Random{

    public static boolean getBoolean(double proba){
        return Math.random() < proba;
    }

    // Renvoie un entier entre min et max, inclus
    public static int getInt(int min,int max){
        return (int) (min + Math.random()*(max - min + 1));
    }
}