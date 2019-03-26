public class Animal extends Entite {
    private final String nom;

    private final int taille;

    private final int faimMax;
    private int faim;

    private final String[] proies;

    private Animal(String nom, int taille, int faimMax, String[] proies) {
        this.nom = nom;
        this.taille = taille;
        this.faimMax = faimMax;
        this.faim = faimMax;
        this.proies = proies;
    }

    public Animal Lapin() {
        return new Animal("Lapin", 20, 50, new String[]{});
    }

    public Animal Renard() {
        return new Animal("Renard", 50, 100, new String[]{"Lapin"});
    }

    public void update() {
        // Se deplace etc
    }

    public String getNom() {
        return nom;
    }

    public int getTaille() {
        return taille;
    }

    public String toString() {
        return nom + "@" + position + ", " + faim + "/" + faimMax;
    }

}
