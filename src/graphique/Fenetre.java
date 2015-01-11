/*
 * Fenêtre principale.
 */
package graphique;

import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import modele.ConfigPartie;
import modele.Plateau;

/**
 *
 * @author Maxime BLAISE
 */
public class Fenetre extends Application {

    public static Scene scene;

    @Override
    public void start(Stage primaryStage) {
        MainGroup mg = new MainGroup();
        scene = new Scene(mg, Config.largeurFenetre, Config.hauteurFenetre);
        scene.setFill(Config.couleurFond);
        mg.addAction();
        System.out.println("Bonjour");

        primaryStage.setTitle(Config.titreFenetre);
        primaryStage.setScene(scene);
        primaryStage.show();

        Plateau.initialiserTableau();
        FenetreDebutPartie fen = new FenetreDebutPartie(mg);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

    class MainGroup extends Parent {
        
        private ArrayList<Circle> listeCercles = new ArrayList<>();

        //
        private Label nomJoueur1;
        private Label nomJoueur2;
        //
        public int marge = 10;
        public int espace = (Config.hauteurFenetre - marge * 2) / Config.taillePlateau;

        MainGroup() {
            // Tracement du plateau
            int iterateurX = marge + espace / 2;
            int iterateurY = marge + espace / 2;

            for (int i = 0; i < Config.taillePlateau; i++) {
                Line line = new Line(iterateurX, marge, iterateurX, Config.hauteurFenetre - marge);
                iterateurX += espace;
                this.getChildren().add(line);
            }
            // Lignes hori.ontales
            for (int i = 0; i < Config.taillePlateau; i++) {
                Line line = new Line(marge, iterateurY, Config.hauteurFenetre - marge, iterateurY);
                iterateurY += espace;
                this.getChildren().add(line);
            }
            // Cercle central
            int centreX = marge + espace / 2 + 9 * espace;
            Circle cercleCentral = new Circle(centreX, centreX, 5);
            cercleCentral.setFill(Color.GRAY);
            this.getChildren().add(cercleCentral);

            // Caractéristiques
            Font font = new Font("Trebuchet MS", 18);
            int posXDroit = Config.hauteurFenetre + 25;
            // Rond noir
            Circle cercle1 = new Circle(Config.hauteurFenetre + 15, 25, 7);
            cercle1.setFill(Color.BLACK);
            this.getChildren().add(cercle1);
            nomJoueur1 = new Label("Joueur 1");
            nomJoueur1.setTranslateX(posXDroit);
            nomJoueur1.setTranslateY(20);
            nomJoueur1.setFont(font);
            nomJoueur1.setTextFill(Color.web("#a30000"));
            // Rond blanc
            Circle cercle2 = new Circle(Config.hauteurFenetre + 15, 55, 7);
            cercle2.setFill(Color.WHITE);
            this.getChildren().add(cercle2);
            nomJoueur2 = new Label("Joueur 2");
            nomJoueur2.setTranslateX(posXDroit);
            nomJoueur2.setTranslateY(50);
            nomJoueur2.setFont(font);
            nomJoueur2.setTextFill(Color.web("#0076a3"));
            this.getChildren().addAll(nomJoueur1, nomJoueur2);

            // Bouton pour recommencer
            Button recommencer = new Button("Recommencer");
            recommencer.setTranslateX(posXDroit);
            recommencer.setTranslateY(100);
            recommencer.setOnAction((ActionEvent event) -> {
                enleverCercle();
            });
            this.getChildren().add(recommencer);
        }

        public void changerNoms(String nom1, String nom2) {
            nomJoueur1.setText(nom1);
            nomJoueur2.setText(nom2);
        }

        private void actionClick(MouseEvent event) {
            System.out.println("Click sur le point : (" + event.getX() + ";" + event.getY() + ")");
            if (ConfigPartie.premierTour) {
                if (verifierClick(9, 9, event.getX(), event.getY())) {
                    ConfigPartie.premierTour = false;
                }
            } else {
                // Parcours
                for (int i = 0; i < Config.taillePlateau; i++) {
                    for (int j = 0; j < Config.taillePlateau; j++) {
                        if (verifierClick(i, j, event.getX(), event.getY())) {
                            break;
                        }
                    }
                }
            }

        }

        private boolean estAProximite(int pointPlateauX, int pointPlateauY, int x, int y) {
            // Tolérance
            int tolerance = espace * 40 / 100;
            // Distance
            int dx, dy;
            dx = pointPlateauX - x;
            dy = pointPlateauY - y;
            int distance = (int) java.lang.Math.sqrt(dx * dx + dy * dy);

            //System.out.println("Traitement du point du plateau : {" + pointPlateauX + ";" + pointPlateauY + "} ; distance="+distance);
            return distance <= tolerance;
        }

        public void addAction() {
// Action du click
            scene.setOnMouseClicked((MouseEvent event) -> {
                actionClick(event);
            });
        }

        private boolean verifierClick(int i, int j, double x, double y) {
            int pointPlateauX = (marge + espace / 2) + i * espace;
            int pointPlateauY = (marge + espace / 2) + j * espace;
            if (Plateau.tab[i][j].equals("EMPTY") && estAProximite(pointPlateauX, pointPlateauY, (int) x, (int) y)) {
                Circle cercleCentral = new Circle(pointPlateauX, pointPlateauY, 10);
                cercleCentral.setFill(ConfigPartie.recupererCouleurTour());
                this.listeCercles.add(cercleCentral);
                this.getChildren().add(cercleCentral);

                Plateau.mettreEnMemoire(i, j);

                if (Plateau.gagne(i, j)) {
                    System.out.println("Bravo ! Vous avez gagné !");
                    JOptionPane.showMessageDialog(null, ConfigPartie.recupererNomJoueurTour() + " a gagné !", "Win", JOptionPane.INFORMATION_MESSAGE);
                }

                ConfigPartie.changerTour();
                if (ConfigPartie.tour) {
                    nomJoueur1.setTextFill(Color.web("#a30000"));
                    nomJoueur2.setTextFill(Color.web("#0076a3"));
                } else {
                    nomJoueur2.setTextFill(Color.web("#a30000"));
                    nomJoueur1.setTextFill(Color.web("#0076a3"));
                }
                return true;
            }
            return false;
        }
        
        /**
         * Enlève tous les cercles dessinés au cours de la partie.
         */
        private void enleverCercle() {
            // Enlevage
            this.getChildren().removeAll(listeCercles);
            // Ré-initialisation de la liste
            this.listeCercles = new ArrayList<>();
            // Vidage de la mémoire
            Plateau.initialiserTableau();
            ConfigPartie.tour = true;
        }
    }
}
