/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphique;

import graphique.Fenetre.MainGroup;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.ConfigPartie;

/**
 *
 * @author Maxime
 */
public class FenetreDebutPartie extends Stage {
    
    public MainGroup mg;
    public FenetreDebutPartie(MainGroup mg) {
        this.mg = mg;
        init();
    }

    public final void init()  {
        Scene scene = new Scene(new DialogGroup(), 190, 180);

        this.setTitle(Config.titreFenetre);
        this.setScene(scene);
        this.show();
    }


    class DialogGroup extends Group {

        public DialogGroup() {
            int iterateurX = 20;

            // Saisie du joueur 1
            Label labelJoueur1 = new Label("Nom du joueur 1");
            labelJoueur1.setTranslateX(iterateurX);
            labelJoueur1.setTranslateY(20);
            TextField saisieJoueur1 = new TextField();
            saisieJoueur1.setTranslateX(iterateurX);
            saisieJoueur1.setTranslateY(45);

            // Saisie du joueur 2
            Label labelJoueur2 = new Label("Nom du joueur 2");
            labelJoueur2.setTranslateX(iterateurX);
            labelJoueur2.setTranslateY(80);
            TextField saisieJoueur2 = new TextField();
            saisieJoueur2.setTranslateX(iterateurX);
            saisieJoueur2.setTranslateY(105);

            // Bouton de validation
            Button valider = new Button("Jouer");
            valider.setTranslateX(iterateurX + 50);
            valider.setTranslateY(140);
            valider.setOnAction((ActionEvent event) -> {
                // Sauvegarde des noms
                ConfigPartie.nomJoueur1 = saisieJoueur1.getText();
                ConfigPartie.nomJoueur2 = saisieJoueur2.getText();
                mg.changerNoms(saisieJoueur1.getText(), saisieJoueur2.getText());
                // Fermeture de cette fenêtre
                close();
            });

            // Ajout des composants de type Label
            this.getChildren().addAll(labelJoueur1, labelJoueur2);
            // Ajout des composants de type TextField
            this.getChildren().addAll(saisieJoueur1, saisieJoueur2);
            // Ajout des composants de type Button
            this.getChildren().addAll(valider);
        }

    }
}
