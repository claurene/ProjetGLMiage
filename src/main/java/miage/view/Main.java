package miage.view;

import miage.controller.LigneController;
import miage.controller.StationController;
import miage.model.*;

import java.io.*;
import java.util.*;
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

        //Attributs
        String listeLigneExiste;
        String listeStationExiste;
        String nomLigne;

        /*------------------------------------------*/

        // Menu utilisateur
        System.out.println("Bienvenue sur notre application.");
        do {
            System.out.println("Votre Geolocalisation est : "+utilisateur.toString());
            System.out.println("Voici les fonctions disponibles :");
            System.out.println("[1] Indiquer un itinéraire");
            System.out.println("[2] Afficher les informations d'une ligne de métro");
            System.out.println("[3] Afficher les informations d'une station de métro");
            System.out.println("[4] Ajouter une ligne de métro");
            System.out.println("[5] Supprimer une ligne de métro");
            System.out.println("[10] Quitter l'application");

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

                    // calcul des stations les plus proches par rapport à la position de l'utilisateur
                    List<Station> plusProches = StationController.deuxplusProches(utilisateur,stationController.getStations());
                    System.out.println("Station la plus proche : "+plusProches.get(0).getNomStation()+"\nSeconde station la plus proche : "+plusProches.get(1).getNomStation());
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
                    listeLigneExiste = ligneController.listeLigne();
                    if(!listeLigneExiste.equals("noLigne")){
                        System.out.println(listeLigneExiste);
                        System.out.println("Veuillez choisir la ligne dont vous souhaitez des informations : ");
                        nomLigne = sc.nextLine().toLowerCase();
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
                    listeStationExiste = stationController.listeStation();
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
                    sc.nextLine();
                    int idLigne;
                    int tmpParc = 0 ;
                    String nomStation;
                    int compteur = 0;

                    //Ajouter une ligne
                    System.out.println("Veuillez saisir les informations suivantes : ");

                    //nom ligne
                    System.out.println("- Nom de la ligne");
                    nomLigne = sc.nextLine();
                    if(!ligneController.ligneExiste(nomLigne)){

                        //id ligne
                        System.out.println("- id de la ligne");
                        idLigne = sc.nextInt();

                        //temps parcours
                        System.out.println("- Temps de parcours");
                        while(tmpParc <= 0 ){
                            tmpParc = sc.nextInt();
                            if(tmpParc <= 0){
                                System.out.println("Le temps de parcours doit être strictement superieur à 0");
                            }
                        }

                        //listeStation
                        listeStationExiste = stationController.listeStation();
                        if (!listeStationExiste.equals("noStation")) {
                            System.out.println(listeStationExiste);
                            System.out.println("- Veuillez saisir au moins 2 stations présentes sur une ligne " +
                                    "(Saisissez 'Fin' lorsque vous aurez terminé) : ");
                            ArrayList<Station> listeStation = new ArrayList<Station>();
                            sc.nextLine();
                            while (true) {
                                nomStation = sc.nextLine().toLowerCase();
                                System.out.print("- ");
                                if (nomStation.equals("fin")) {
                                    if(compteur <= 1){
                                        System.out.println("Veuillez saisir au moins 2 stations.");
                                    }else{
                                        break;
                                    }
                                } else {
                                    if(stationController.getStations().containsKey(nomStation)){
                                        listeStation.add(stationController.getStations().get(nomStation));
                                        compteur += 1;
                                    }else{
                                        System.out.println("La station "+ nomStation +" n'existe pas");
                                    }
                                }
                            }
                            System.out.println(ligneController.ajouterLigne(nomLigne, idLigne, tmpParc, listeStation));
                        }
                    }else{
                        System.out.println("La ligne "+nomLigne+" existe déjà.");
                    }

                    // En attente d'une nouvelle commande
                    break;

                case 5:
                    sc.nextLine();
                    // Supprimer une ligne de métro
                    System.out.println("Les lignes disponibles sont : ");
                    listeLigneExiste = ligneController.listeLigne();
                    if(!listeLigneExiste.equals("noLigne")){
                        System.out.println(listeLigneExiste);
                        System.out.println("Veuillez choisir la ligne que vous souhaitez supprimer : ");
                        nomLigne = sc.nextLine().toLowerCase();
                        System.out.println(ligneController.supprimerLigne(nomLigne));
                    }else{
                        System.out.println("Il n'y a aucune ligne.");
                    }

                    // En attente d'une nouvelle commande
                    break;

                case 10:
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
