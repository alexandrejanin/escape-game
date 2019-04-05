public class Leaf{

    private final static int MIN_LEAF_SIZE = 6;
    private int x,y,largeur,hauteur; // position et taille de Leaf
    private Leaf gauche,droite;
    private Rectangle salle;
    private Vecteur hall;

    public Leaf(int x,int y, int largeur,int hauteur){
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }


    public boolean nePossedePasEnfants(){
        return gauche == null && droite == null;
    }
    public boolean split(){
        if (gauche != null || droite != null){
            return false; // c'est déja split
        }

        boolean splitH = (Math.random())<0.5;
        if(largeur>hauteur && (double)largeur/hauteur >= 1.25){
            splitH = false;
        }
        else if (hauteur > largeur && (double)hauteur / largeur >= 1.25){
            splitH = true;
        }

        int max = (splitH ? hauteur : largeur) - MIN_LEAF_SIZE;
        if(max <= MIN_LEAF_SIZE){
            return false; // zone trop petite à split
        }

        int split = (int)(MIN_LEAF_SIZE + Math.random() * (max - MIN_LEAF_SIZE + 1));

        if(splitH){
            gauche = new Leaf(x,y,largeur,split);
            droite = new Leaf(x,y + split,largeur,hauteur - split);
        }
        else{
            gauche = new Leaf(x,y,split,hauteur);
            droite = new Leaf(x+split,y,largeur - split,hauteur);
        }

        return true; // split successfully done!!!!1111
    }

    public void creerSalle(){
        if (! nePossedePasEnfants()){
            if(gauche != null){
                gauche.creerSalle();
            }
            if(droite != null){
                droite.creerSalle();
            }
        }
        else{
            int largeurSalle = (int)(3 + Math.random() * (largeur - 4));
            int hauteurSalle = (int)(3 + Math.random() * (hauteur - 4));
            int xSalle = (int)(1 + Math.random() * (largeur - largeurSalle - 1));
            int ySalle = (int)(1 + Math.random() * (hauteur - hauteurSalle - 1));
            salle = new Rectangle(x + xSalle,y+ySalle,largeurSalle,hauteurSalle);
        }
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getLargeur(){
        return largeur;
    }
    public int getHauteur(){
        return hauteur;
    }
    public Leaf getGauche(){
        return gauche;
    }
    public Leaf getDroite(){
        return droite;
    }
    public Rectangle getSalle(){
        return salle;
    }
}