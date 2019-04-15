/*
Génération du labyrinthe d'apres 'How to Use BSP Trees to Generate Game Maps'
https://gamedevelopment.tutsplus.com/tutorials/how-to-use-bsp-trees-to-generate-game-maps--gamedev-12268
 */

package Labyrinthe;

import Application.Joueur;
import Entites.Animal;
import Entites.Entite;
import Entites.IJoueTour;
import Entites.Obstacle;
import Entites.Plante;
import Utilitaires.EssaisDepassesException;
import Utilitaires.Random;
import Utilitaires.Vecteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Labyrinthe {
    // Parametres de génération de labyrinthe
    private final static int MAX_LEAF_SIZE = 20;
    private final static int MIN_LEAF_SIZE = 5;
    private final ArrayList<Entite> entites;
    private final Case[][] labyrinthe;
    private final int hauteur;
    private final int largeur;
    private final Vecteur sortie;

    public Labyrinthe(int largeur, int hauteur, int nbAnimaux, int nbPlantes, double probaObstacle, Joueur joueur) {
        entites = new ArrayList<>();

        labyrinthe = new Case[hauteur][largeur];
        this.hauteur = hauteur;
        this.largeur = largeur;

        // Crée le labyrinthe
        ArrayList<Leaf> leafs = new ArrayList<>();
        Leaf racine = new Leaf(0, 0, largeur, hauteur);
        leafs.add(racine);

        boolean aSplit = true;
        while (aSplit) {
            aSplit = false;
            for (int i = 0; i < leafs.size(); i++) {
                Leaf leaf = leafs.get(i);
                if (leaf.getGauche() == null && leaf.getDroite() == null) {
                    if (leaf.getLargeur() > MAX_LEAF_SIZE || leaf.getHauteur() > MAX_LEAF_SIZE || Random.getBoolean(0.25)) {
                        if (leaf.split(MIN_LEAF_SIZE)) {
                            leafs.add(leaf.getGauche());
                            leafs.add(leaf.getDroite());
                            aSplit = true;
                        }
                    }
                }
            }
        }
        racine.creerSalle();

        // Initialise la grille
        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                labyrinthe[y][x] = Case.Mur;
            }
        }

        // Convertit de liste de Leafs à une grille de Mur/Sol
        for (Leaf leaf : leafs) {
            Rectangle salle = leaf.getSalle();
            if (salle != null) {
                int xMax = salle.getX() + salle.getLargeur();
                int yMax = salle.getY() + salle.getHauteur();
                for (int x = salle.getX(); x < xMax; x++) {
                    for (int y = salle.getY(); y < yMax; y++) {
                        labyrinthe[y][x] = Case.Sol;
                    }
                }
            }

            ArrayList<Rectangle> couloirs = leaf.getCouloir();
            if (couloirs != null) {
                for (Rectangle rect : couloirs) {
                    int xMax = rect.getX() + rect.getLargeur();
                    int yMax = rect.getY() + rect.getHauteur();
                    for (int x = rect.getX(); x < xMax; x++) {
                        for (int y = rect.getY(); y < yMax; y++) {
                            labyrinthe[y][x] = Case.Sol;
                        }
                    }

                    // Chance de placer un obstalce
                    if (Random.getBoolean(probaObstacle)) {
                        Vecteur position = new Vecteur(
                                Random.getInt(rect.getX(), xMax),
                                Random.getInt(rect.getY(), yMax)
                        );
                        entites.add(Obstacle.aleatoire(position));
                    }
                }
            }

        }

        // Ajoute plantes
        for (int i = 0; i < nbPlantes; i++) {
            int x, y;
            do {
                x = Random.getInt(0, largeur - 1);
                y = Random.getInt(0, hauteur - 1);
            } while (getCase(x, y) == Case.Mur);

            entites.add(Plante.aleatoire(new Vecteur(x, y)));
        }

        // Ajoute animaux
        for (int i = 0; i < nbAnimaux; i++) {
            int x, y;
            do {
                x = Random.getInt(0, largeur - 1);
                y = Random.getInt(0, hauteur - 1);
            } while (getCase(x, y) == Case.Mur);

            entites.add(Animal.aleatoire(new Vecteur(x, y)));
        }

        // Ajoute l'animal du joueur au labyrinthe
        Animal animalJoueur = joueur.getAnimal();

        int essaisJoueur = 0;
        Vecteur positionJoueur;
        do {
            positionJoueur = new Vecteur(Random.getInt(0, largeur), Random.getInt(0, hauteur));
            essaisJoueur++;
            if (essaisJoueur > 1000) {
                throw new EssaisDepassesException("Essais de placement du joueur dépassés");
            }
        } while (!peutBouger(positionJoueur, animalJoueur));

        animalJoueur.setPosition(positionJoueur);
        entites.add(animalJoueur);


        // Ajoute la sortie au labyrinthe
        int essaisSortie = 0;
        Vecteur positionSortie;
        do {
            positionSortie = new Vecteur(Random.getInt(0, largeur), Random.getInt(0, hauteur));
            essaisSortie++;
            if (essaisSortie > 1000) {
                throw new EssaisDepassesException("Essais de placement de la sortie dépassés");
            }
        } while (!peutBouger(positionSortie, animalJoueur) || positionJoueur.distance(positionSortie) < Math.min(largeur, hauteur) / 2);
        sortie = positionSortie;
    }

    public List<Entite> getEntites() {
        return Collections.unmodifiableList(entites);
    }

    public Case getCase(int x, int y) {
        return labyrinthe[y][x];
    }

    public boolean peutBouger(Vecteur position, Animal animal) {
        return peutBouger(position.x, position.y, animal);
    }

    public boolean peutBouger(int x, int y, Animal animal) {
        // Sors du labyrinthe? -> false
        if (x < 0 || x >= largeur || y < 0 || y >= hauteur) return false;

        // Est un mur? -> false
        if (labyrinthe[y][x] == Case.Mur) return false;

        for (Entite entite : entites) {
            if (entite.getX() == x && entite.getY() == y) {
                // Un autre animal qui ne peut pas etre mangé occupe la case -> false
                if (entite instanceof Animal && entite != animal && !animal.peutManger(entite)) {
                    return false;
                }
                // Un obstacle qui ne peut pas etre franchi -> false
                if (entite instanceof Obstacle && !((Obstacle) entite).peutPasser(animal)) {
                    return false;
                }
            }
        }

        return true;
    }

    // Fais jouer le joueur puis les animaux
    // Renvoie true si la partie est finie
    public boolean joueTour(Joueur joueur) {
        if (joueur.getAnimal().getPosition().equals(sortie)) {
            System.out.println("Félicitations! Tu as gagné!");
            return true;
        }
        if (!entites.contains(joueur.getAnimal())) {
            System.out.println("Tu es mort!");
            return true;
        }

        HashMap<IJoueTour, Vecteur> prochainePosition = new HashMap<>();

        // On demande au joueur de deplacer son animal
        prochainePosition.put(joueur.getAnimal(), joueur.joueTour(this));

        for (Entite entite : entites) {
            if (entite == joueur.getAnimal()) {
                continue;
            }
            // Si l'entite est un animal, il joue un tour et renvoie sa prochaine position
            if (entite instanceof IJoueTour) {
                IJoueTour animal = (IJoueTour) entite;
                prochainePosition.put(animal, animal.joueTour(this));
            }
        }

        // On supprime d'abord toutes les entites qui se font manger
        for (Map.Entry<IJoueTour, Vecteur> entry : prochainePosition.entrySet()) {
            if (entry.getKey() instanceof Animal) {
                Animal animal = (Animal) entry.getKey();
                entites.removeIf(entite -> entite.getPosition().equals(entry.getValue()) && animal.peutManger(entite));
            }
        }

        // Puis on fait bouger toutes les entites
        for (Map.Entry<IJoueTour, Vecteur> entry : prochainePosition.entrySet()) {
            entry.getKey().bouge(this, entry.getValue());
        }

        return false;
    }

    // Affiche le labyrinthe sous forme de caractères
    public String affichage(Joueur joueur) {
        Character[][] characters = new Character[hauteur][largeur];

        // Affiche labyrinthe
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                switch (getCase(x, y)) {
                    case Mur:
                        characters[y][x] = '#';
                        break;
                    case Sol:
                        characters[y][x] = ' ';
                        break;
                }
            }
        }

        // puis plantes
        for (Entite entite : entites) {
            if (entite instanceof Plante) {
                characters[entite.getY()][entite.getX()] = entite.getCar();
            }
        }

        // puis obstacles
        for (Entite entite : entites) {
            if (entite instanceof Obstacle) {
                characters[entite.getY()][entite.getX()] = entite.getCar();
            }
        }

        // puis animaux
        for (Entite entite : entites) {
            if (entite instanceof Animal) {
                characters[entite.getY()][entite.getX()] = entite.getCar();
            }
        }

        // Affiche l'animal du joueur
        characters[joueur.getAnimal().getY()][joueur.getAnimal().getX()] = 'J';

        // Affiche la sortie
        characters[sortie.y][sortie.x] = '♦';

        // Tableau -> string
        StringBuilder string = new StringBuilder();
        for (int y = -Animal.RAYON_VISION; y <= Animal.RAYON_VISION; y++) {
            for (int x = -Animal.RAYON_VISION; x <= Animal.RAYON_VISION; x++) {
                int labX = joueur.getAnimal().getX() + x;
                int labY = joueur.getAnimal().getY() + y;

                if (labX < 0 || labX >= largeur || labY < 0 || labY >= hauteur) {
                    string.append("-");
                } else {
                    string.append(characters[labY][labX]);
                }
            }
            string.append('\n');
        }

        return string.toString();
    }

    private enum Case {
        Sol, Mur
    }
}
