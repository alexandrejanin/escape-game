package Application;

import Entites.Animal;
import Entites.IJoueTour;
import Labyrinthe.Labyrinthe;
import Utilitaires.Vecteur;

import java.util.Scanner;

public final class Joueur implements IJoueTour {
    private final Animal animal;

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

        System.out.print("Vous avez choisi: " + animal + "\nPeut manger: ");
        for (String proie : animal.getProies()) {
            System.out.print(proie + " ");
        }
        System.out.println();
    }

    public Animal getAnimal() {
        return animal;
    }

    @Override
    public Vecteur joueTour(Labyrinthe labyrinthe) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.next();

            if (input.length() != 1) {
                System.out.println("Veuillez entrer 1 caractère");
                continue;
            }

            Vecteur prochainePos = new Vecteur(animal.getPosition());

            switch (input.charAt(0)) {
                // Pavé numérique
                case '1':
                    prochainePos.x--;
                    prochainePos.y++;
                    break;
                case '2':
                    prochainePos.y++;
                    break;
                case '3':
                    prochainePos.x++;
                    prochainePos.y++;
                    break;
                case '4':
                    prochainePos.x--;
                    break;
                case '5':
                    break;
                case '6':
                    prochainePos.x++;
                    break;
                case '7':
                    prochainePos.x--;
                    prochainePos.y--;
                    break;
                case '8':
                    prochainePos.y--;
                    break;
                case '9':
                    prochainePos.x++;
                    prochainePos.y--;
                    break;

                // ZQSD/WASD
                case 'Z':
                case 'z':
                case 'W':
                case 'w':
                    prochainePos.y--;
                    break;
                case 'D':
                case 'd':
                    prochainePos.x++;
                    break;
                case 'Q':
                case 'q':
                case 'A':
                case 'a':
                    prochainePos.x--;
                    break;
                case 'S':
                case 's':
                    prochainePos.y++;
                    break;
                default:
                    System.out.println("Caractère non valide (ZQSD ou pavé numérique)");
                    continue;
            }


            if (!labyrinthe.peutBouger(prochainePos, animal)) {
                System.out.println("Tu te prends un mur boloss");
                continue;
            }

            return prochainePos;
        }
    }

    @Override
    public void bouge(Labyrinthe labyrinthe, Vecteur position) {
        animal.bouge(labyrinthe, position);
    }
}
