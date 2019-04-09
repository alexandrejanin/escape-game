import java.util.ArrayList;

public class Affichage{

    public static String affichage(Labyrinthe labyrinthe, ArrayList<Entite> entites){
        String string = "";
        for(int y = 0; y < labyrinthe.getLongueur(); y++){
            for(int x =0 ; x < labyrinthe.getLargeur(); x++){
                boolean entitePresente = false;
                
                for (Entite entite : entites){
                    if (entite.getX() == x && entite.getY() == y){
                        string += entite.getCar();
                        entitePresente = true;
                        break;
                    }
                }

                // Si il n'y a pas d'animaux, on affiche le sol ou mur
                if (!entitePresente){
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