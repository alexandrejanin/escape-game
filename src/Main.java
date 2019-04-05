public class Main{

    public static void main(String[] args){
        //Générer le labyrinthe : 
        Labyrinthe labyrinthe = new Labyrinthe(50,50);
        //Créer un tableau d'animaux
        Animal[] listeAnimaux = new Animal[] {
            Animal.Lion(new Vecteur(0, 0)), 
            Animal.Gazelle(new Vecteur(0,0))
        };
        
        //Générer le joueur
        Joueur joueur = new Joueur(listeAnimaux);
        System.out.println(labyrinthe);
    }
}