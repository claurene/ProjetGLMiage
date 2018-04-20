package miage.view;

import miage.controller.JSONProcessor;
import miage.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    // Création du logger
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private static Position utilisateur = new Position();
    /*
     * Lancement du programme principal
     */
    public static void main(String args[]){
        // Booléen vrai tant qu'on est dans le système
        boolean running = true;

        // Liste des lignes de métro
        ArrayList<Ligne> lignes = new ArrayList<Ligne>();

        // Chargement des lignes de métro
        File dir = new File("src/main/resources");
        if (dir.listFiles() != null) {
            for (File fichier : dir.listFiles()) {
                Ligne l = null;
                try {
                    l = JSONProcessor.deserialize(fichier.getPath());
                    lignes.add(l);
                } catch (IOException e) {
                    LOG.warning("Erreur de désérialisation");
                }
            }
        } else {
            LOG.info("Aucune donnée trouvée");
        }

        /*------------------------------------------*/

        // Menu utilisateur
        System.out.println("Bienvenue sur notre application.");
        do {
            System.out.println("Votre Geolocalisation est : "+utilisateur.toString());
            System.out.println("Voici les fonctions disponibles :");
            System.out.println("[1] Afficher les informations d'une ligne de métro");
            System.out.println("[2] Afficher les informations d'une station de métro");
            System.out.println("[3] Quitter l'application");

            // Gestion des choix de l'utilisateur
            Scanner sc = new Scanner(System.in);
            System.out.println("Entrez votre choix : ");
            int choix;
            try {
                choix = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Votre choix est invalide. Veuillez choisir une option parmis celles proposées.");
                continue;
            }

            // Action effectuée en fonction du choix :
            switch(choix) {
                case 1:
                    // Afficher les informations d'une ligne de métro
                    //TODO

                    // En attente d'une nouvelle commande
                    break;

                case 2:
                    // Afficher les informations d'une station de métro
                    //TODO

                    // En attente d'une nouvelle commande
                    break;

                case 3:
                    // Quitter l'application
                    System.out.println("Au revoir !");
                    running=false;
                    break;

                default:
                    System.out.println("Votre choix est invalide. Veuillez choisir une option parmis celles proposées.");
            }

        } while(running);

        /*------------------------------------------*/

        // Sauvegarde des lignes de métro
        for (Ligne l : lignes) {
            String filename = "src/main/resources/"+l.getNomLigne()+".json";
            try {
                JSONProcessor.serialize(l,filename);
            } catch (IOException e) {
                LOG.warning("Erreur de sérialisation");
            }
        }
    }
}
