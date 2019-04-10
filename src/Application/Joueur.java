package Application;

import Entites.Animal;
import Utilitaires.Vecteur;

import java.util.Scanner;

final class Joueur {

    private Animal animal;

    Joueur() {
        // Liste des animaux que le joueur peut sélectionner
        Animal[] animaux = new Animal[]{
                Animal.lion(new Vecteur(0, 0)),
                Animal.gazelle(new Vecteur(0, 0)),
                Animal.zebu(new Vecteur(0, 0)),
                Animal.lapin(new Vecteur(0, 0))
        };

        System.out.println("Selectionnez un animal:");
        for (int i = 0; i < animaux.length; i++) {
            System.out.println((i + 1) + ": " + animaux[i].getNom());
        }

        Scanner scanner = new Scanner(System.in);

        // On demande la selection jusqu'a avoir un indice valide
        int selection;
        do selection = scanner.nextInt(); while (selection < 1 || selection > animaux.length);

        animal = animaux[selection - 1];

        System.out.println("Vous avez choisi: " + animal);
    }
}
