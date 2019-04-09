import java.util.ArrayList;

public class Leaf {

    private final static int MIN_LEAF_SIZE = 6;
    private int x, y, largeur, hauteur; // position et taille de Leaf
    private Leaf gauche, droite;
    private Rectangle salle;
    private ArrayList<Rectangle> couloir;

    public Leaf(int x, int y, int largeur, int hauteur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public boolean nePossedePasEnfants() {
        return gauche == null && droite == null;
    }

    public boolean split() {
        if (gauche != null || droite != null) {
            return false; // c'est déja split
        }

        boolean splitH = Random.getBoolean(0.5);

        if (largeur > hauteur && (double) largeur / hauteur >= 1.25) {
            splitH = false;
        } else if (hauteur > largeur && (double) hauteur / largeur >= 1.25) {
            splitH = true;
        }

        int max = (splitH ? hauteur : largeur) - MIN_LEAF_SIZE;
        if (max <= MIN_LEAF_SIZE) {
            return false; // zone trop petite à split
        }

        int split = Random.getInt(MIN_LEAF_SIZE, max);

        if (splitH) {
            gauche = new Leaf(x, y, largeur, split);
            droite = new Leaf(x, y + split, largeur, hauteur - split);
        } else {
            gauche = new Leaf(x, y, split, hauteur);
            droite = new Leaf(x + split, y, largeur - split, hauteur);
        }

        return true; // split successfully done!!!!1111
    }

    public void creerSalle() {
        if (!nePossedePasEnfants()) {
            if (gauche != null) {
                gauche.creerSalle();
            }
            if (droite != null) {
                droite.creerSalle();
            }
            if (gauche != null && droite != null) {
                creerCouloirs(gauche.getSalleRec(), droite.getSalleRec());
            }
        } else {
            int largeurSalle = Random.getInt(3, largeur - 2);
            int hauteurSalle = Random.getInt(3, hauteur - 2);
            int xSalle = Random.getInt(1, largeur - largeurSalle - 1);
            int ySalle = Random.getInt(1, hauteur - hauteurSalle - 1);
            salle = new Rectangle(x + xSalle, y + ySalle, largeurSalle, hauteurSalle);
        }
    }

    public Rectangle getSalleRec() {
        if (salle != null) {
            return salle;
        } else {
            Rectangle salleGauche = null; //obligé d'initialiser cette valeur car elle est locale
            Rectangle salleDroite = null;
            if (gauche != null) {
                salleGauche = gauche.getSalleRec();
            }
            if (droite != null) {
                salleDroite = droite.getSalleRec();
            }
            if (salleGauche == null && salleDroite == null) {
                return null;
            } else if (salleDroite == null) {
                return salleGauche;
            } else if (salleGauche == null) {
                return salleDroite;
            } else if (Random.getBoolean(0.5)) {
                return salleGauche;
            } else {
                return salleDroite;
            }
        }
    }

    public void creerCouloirs(Rectangle rectGauche, Rectangle rectDroit) {
        couloir = new ArrayList<Rectangle>();
        Vecteur point1 = new Vecteur(
                Random.getInt(rectGauche.getGauche() + 1, rectGauche.getDroite() - 2),
                Random.getInt(rectGauche.getHaut() + 1, rectGauche.getBas() - 2)
        );
        Vecteur point2 = new Vecteur(
                Random.getInt(rectDroit.getGauche() + 1, rectDroit.getDroite() - 2),
                Random.getInt(rectDroit.getHaut() + 1, rectDroit.getBas() - 2)
        );

        int width = point2.getX() - point1.getX();
        int height = point2.getY() - point1.getY();

        if (width < 0) {
            if (height < 0) {
                if (Random.getBoolean(0.5)) {
                    couloir.add(new Rectangle(point2.getX(), point1.getY(), Math.abs(width), 1));
                    couloir.add(new Rectangle(point2.getX(), point2.getY(), 1, Math.abs(height)));
                } else {
                    couloir.add(new Rectangle(point2.getX(), point2.getY(), Math.abs(width), 1));
                    couloir.add(new Rectangle(point1.getX(), point2.getY(), 1, Math.abs(height)));
                }
            } else if (height > 0) {
                if (Random.getBoolean(0.5)) {
                    couloir.add(new Rectangle(point2.getX(), point1.getY(), Math.abs(width), 1));
                    couloir.add(new Rectangle(point2.getX(), point1.getY(), 1, Math.abs(height)));
                } else {
                    couloir.add(new Rectangle(point2.getX(), point2.getY(), Math.abs(width), 1));
                    couloir.add(new Rectangle(point1.getX(), point1.getY(), 1, Math.abs(height)));
                }
            } else {
                couloir.add(new Rectangle(point2.getX(), point2.getY(), Math.abs(width), 1));
            }
        } else if (width > 0) {
            if (height < 0) {
                if (Random.getBoolean(0.5)) {
                    couloir.add(new Rectangle(point1.getX(), point2.getY(), Math.abs(width), 1));
                    couloir.add(new Rectangle(point1.getX(), point2.getY(), 1, Math.abs(height)));
                } else {
                    couloir.add(new Rectangle(point1.getX(), point1.getY(), Math.abs(width), 1));
                    couloir.add(new Rectangle(point2.getX(), point2.getY(), 1, Math.abs(height)));
                }
            } else if (height > 0) {
                if (Random.getBoolean(0.5)) {
                    couloir.add(new Rectangle(point1.getX(), point1.getY(), Math.abs(width), 1));
                    couloir.add(new Rectangle(point2.getX(), point1.getY(), 1, Math.abs(height)));
                } else {
                    couloir.add(new Rectangle(point1.getX(), point2.getY(), Math.abs(width), 1));
                    couloir.add(new Rectangle(point1.getX(), point1.getY(), 1, Math.abs(height)));
                }
            } else {
                couloir.add(new Rectangle(point1.getX(), point1.getY(), Math.abs(width), 1));
            }
        } else {
            if (height < 0) {
                couloir.add(new Rectangle(point2.getX(), point2.getY(), 1, Math.abs(height)));
            } else if (height > 0) {
                couloir.add(new Rectangle(point1.getX(), point1.getY(), 1, Math.abs(height)));
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public Leaf getGauche() {
        return gauche;
    }

    public Leaf getDroite() {
        return droite;
    }

    public Rectangle getSalle() {
        return salle;
    }

    public ArrayList<Rectangle> getCouloir() {
        return couloir;
    }
}
