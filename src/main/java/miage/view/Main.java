package miage.view;

import miage.controller.LigneController;
import miage.controller.StationController;
import miage.model.*;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    private static LigneController ligneController = new LigneController();
    private static StationController stationController = new StationController();

    private static Position utilisateur = new Position();

    /*
     * Lancement du programme principal
     */
    public static void main(String args[]){
        // Booléen vrai tant qu'on est dans le système
        boolean running = true;

        // Chargement des lignes de métro
        ligneController.initialisationLignes();

        // Chargement des stations de métro
        stationController.initialisationStations();

        /*------------------------------------------*/

        // Menu utilisateur
        System.out.println("Bienvenue sur notre application.");
        do {
            System.out.println("Votre Geolocalisation est : "+utilisateur.toString());
            System.out.println("Voici les fonctions disponibles :");
            System.out.println("[1] Indiquer un itinéraire");
            System.out.println("[2] Afficher les informations d'une ligne de métro");
            System.out.println("[3] Afficher les informations d'une station de métro");
            System.out.println("[4] Quitter l'application");

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
                    // Commencer un itinéraire
                    //TODO Verifier que le choix existe
                    //TODO Verifier que depart et arrivee ne sont pas en travaux
                    // On vide la ligne avant d'en lire une autre
                    String depart = "";
                    String arrivee = "";
                    sc.nextLine();
                    while(depart.equals(arrivee)) {
                        System.out.println("Indiquer votre départ : ");
                        depart = sc.nextLine();
                        System.out.println("Indiquer votre arrivée : ");
                        arrivee = sc.nextLine();
                        if (depart.equals(arrivee))
                            System.out.println("Veuillez choisir un départ différent de l'arrivée !");
                        else {
                            System.out.println("Vous avez choisi :\r Depart : " + depart + " |  Arrivee : " + arrivee + "");
                        }
                    }
                    break;
                case 2:
                    sc.nextLine();
                    // Afficher les informations d'une ligne de métro
                    System.out.println("Les lignes disponibles sont : ");
                    String listeLigneExiste = ligneController.listeLigne();
                    if(!listeLigneExiste.equals("noLigne")){
                        System.out.println(listeLigneExiste);
                        System.out.println("Veuillez choisir la ligne dont vous souhaitez des informations : ");
                        String nomLigne = sc.nextLine().toLowerCase();
                        System.out.println(ligneController.afficherLigne(nomLigne));
                    }else{
                        System.out.println("Il n'y a aucune ligne.");
                    }

                    // En attente d'une nouvelle commande
                    break;

                case 3:
                    sc.nextLine();
                    // Afficher les informations d'une station de métro
                    System.out.println("Les stations disponibles sont : ");
                    String listeStationExiste = stationController.listeStation();
                    if(!listeStationExiste.equals("noStation")){
                        System.out.println(listeStationExiste);
                        System.out.println("Veuillez choisir la station dont vous souhaitez des informations : ");
                        String nomStation = sc.nextLine().toLowerCase();
                        System.out.println(stationController.afficherStation(nomStation));
                    }else{
                        System.out.println("Il n'y a aucune station.");
                    }

                    // En attente d'une nouvelle commande
                    break;

                case 4:
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
        ligneController.sauvegardeLignes();

        // Sauvegarde des stations
        stationController.sauvegardeStations();
    }
}
