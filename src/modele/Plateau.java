package modele;

import graphique.Config;
import static modele.ConfigPartie.tour;

/**
 *
 * @author Maxime BLAISE
 */
public class Plateau {

    public static final String[][] tab = new String[Config.taillePlateau][Config.taillePlateau];

    /**
     * Constructeur privé.
     */
    private Plateau() {

    }

    /**
     * Initialisation du tableau.
     */
    public static void initialiserTableau() {
        // Parcours
        for (int i = 0; i < Config.taillePlateau; i++) {
            for (int j = 0; j < Config.taillePlateau; j++) {
                // Initialisation
                tab[i][j] = "EMPTY";
            }
        }
    }

    /**
     * Détermine si un joueur gagne ou non.
     *
     * @param posX abscisse du dernier pion placé.
     * @param posY ordonnée du dernier pion placé.
     * @return vrai si la partie a été gagné.
     */
    public static boolean gagne(int posX, int posY) {
        String contenu = tab[posX][posY];
        // DEBUT TEST HORIZONTAL
        int iterateur = 1;
        String contenuTmp = "";
        int indice = 1;
        boolean verifie = true;
        while (verifie) {
            verifie = verifie(posX - indice, posY, contenu);
            if (verifie) {
                iterateur++;
                indice++;
            }
        }
        indice = 1;
        verifie = true;
        while (verifie) {
            verifie = verifie(posX + indice, posY, contenu);
            if (verifie) {
                iterateur++;
                indice++;
            }
        }
        if (iterateur >= 5) {
            System.out.println(iterateur + " éléments à l'horizontal.");
            return true;
        }
        // FIN TEST HORIZONTAL

        return false;
    }

    public static boolean verifie(int i, int j, String contenu) {
        try {
            return tab[i][j].equals(contenu);
        } catch (Exception e) {
            return false;
        }
    }

    public static void mettreEnMemoire(int i, int j) {
        if (tour) {
            tab[i][j] = "1";
        } else {
            tab[i][j] = "2";
        }
    }
}
