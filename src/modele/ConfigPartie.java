/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import javafx.scene.paint.Color;

/**
 *
 * @author Maxime
 */
public class ConfigPartie {

    /**
     * Nom du joueur 1.
     */
    public static String nomJoueur1 = "";

    /**
     * Nom du joueur 2.
     */
    public static String nomJoueur2 = "";

    /**
     * Couleur du joueur 1, généralement noir.
     */
    public static Color couleurJoueur1 = Color.BLACK;

    /**
     * Couleur du joueur 2, généralement noir.
     */
    public static Color couleurJoueur2 = Color.WHITE;

    public static boolean tour = true;
    
    /**
     * Premier tour ou non.
     */
    public static boolean premierTour = true;

    /**
     * Récupère le nom du joueur en train de jouer.
     *
     * @return chaîne
     */
    public static String recupererNomJoueurTour() {
        if (tour) {
            return nomJoueur1;
        } else {
            return nomJoueur2;
        }
    }

    public static Color recupererCouleurTour() {
        if (tour) {
            return couleurJoueur1;
        } else {
            return couleurJoueur2;
        }
    }

    public static void changerTour() {
        tour = !tour;
    }
}
