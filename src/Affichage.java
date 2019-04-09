import java.util.ArrayList;

public class Affichage{

    public static String affichage(Labyrinthe labyrinthe, ArrayList<Animal> animaux){
        String string = "";
        for(int y = 0; y < labyrinthe.getLongueur(); y++){
            for(int x =0 ; x < labyrinthe.getLargeur(); x++){
                boolean animalPresent = false;
                
                for (Animal animal : animaux){
                    if (animal.getX() == x && animal.getY() == y){
                        string += animal.getCar();
                        animalPresent = true;
                        break;
                    }
                }

                // Si il n'y a pas d'animaux, on affiche le sol ou mur
                if (!animalPresent){
                    switch (labyrinthe.getCase(x, y)){
                        case Mur:
                            string += "#";
                        break;
                        case Sol:
                            string += " ";
                        break;
                    }
                }
            }
            string += "\n";
        }
        return string;
    }
}