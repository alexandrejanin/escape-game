import java.util.Scanner;

public class Joueur{

    private Animal animal;

    public Joueur(Animal [] animaux){
        System.out.println("Selectionnez un animal:");
        for(int i=0;i<animaux.length;i++){
            System.out.println((i+1) + ": " + animaux[i].getNom());
        }
        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();
        animal = animaux[selection - 1];

        System.out.println("Vous avez choisi: " + animal);
    }
}