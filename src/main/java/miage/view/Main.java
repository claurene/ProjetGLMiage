package miage.view;

import miage.controller.HoraireController;
import miage.controller.ItineraireController;
import miage.controller.LigneController;
import miage.controller.StationController;
import miage.model.*;


import java.time.*;
import java.util.*;

public class Main {

    private static ItineraireController itineraireController = new ItineraireController();
    private static HoraireController horaireController = new HoraireController();

    private static Position utilisateur = new Position();

    private static String nomLigne;

    /*
     * Lancement du programme principal
     */
    public static void main(String args[]){
        // Booléen vrai tant qu'on est dans le système
        boolean running = true;

        // Chargement des lignes de métro
        LigneController.initialisationLignes();

        // Chargement des stations de métro
        StationController.initialisationStations();



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
            System.out.println("[6] Modifier une ligne de métro");
            System.out.println("[7] Modifier l'incident d'une ligne de métro");
            System.out.println("[8] Ajouter une station de métro");
            System.out.println("[9] Supprimer une station de métro");
            System.out.println("[10] Modifier une station de métro");
            System.out.println("[11] Modifier un incident de station de métro");
            System.out.println("[12] Afficher horaires");
            System.out.println("[0] Quitter l'application");

            // Gestion des choix de l'utilisateur
            Scanner sc = new Scanner(System.in);
            sc.useLocale(Locale.US);
            System.out.println("Entrez votre choix : ");
            int choix = entierEntree(sc);
            // Action effectuée en fonction du choix :
            switch(choix) {
                case 1:
                    //Trouver un itinéraire
                    sc.nextLine();
                    trouverItineraire(sc);
                    break;

                case 2:
                    // Afficher les informations d'une ligne de métro
                    sc.nextLine();
                    afficherLigneUtilisateur(sc);
                    break;

                case 3:
                    // Afficher les informations d'une station de métro
                    sc.nextLine();
                    afficherStationUtilisateur(sc);
                    break;

                case 4:
                    //Ajouter une ligne
                    sc.nextLine();
                    ajouterLigneUtilisateur(sc);
                    break;

                case 5:
                    // Supprimer une ligne de métro
                    sc.nextLine();
                    supprimerLigneUtilisateur(sc);
                    break;
                case 6:
                    // Modifier une ligne de métro
                    sc.nextLine();
                    modifierLigneUtilisateur(sc);
                    break;
                case 7 :
                    //Modifier l'incident d'une ligne de métro
                    sc.nextLine();
                    modifierLigneIncidentUtilisateur(sc);
                    break;

                case 8:
                    //Ajouter une station de métro
                    sc.nextLine();
                    ajouterStationUtilisateur(sc);
                    break;
                case 9:
                    //Supprimer une station de métro
                    sc.nextLine();
                    supprimerStationUtilisateur(sc);
                    break;
                case 10:
                    //Modifier une station de métro
                    sc.nextLine();
                    modifierStationUtilisateur(sc);
                    break;
                case 11:
                    //Modifier l'incident d'une station de métro
                    sc.nextLine();
                    modifierStationIncidentUtilisateur(sc);
                    break;
                case 12:
                    sc.nextLine();
                    afficherHoraireStationLigne(sc);
                    break;
                case 0:
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
        LigneController.sauvegardeLignes();

        // Sauvegarde des stations
        StationController.sauvegardeStations();
    }

    /**
     * Méthode qui permet de trouver un itinéraire
     * L'utilisateur à la possibilité d'avoir un itinéraire rapide, avec des points de passage ou avec le moins de changement
     * @param sc
     */
    private static void trouverItineraire(Scanner sc){
        // Commencer un itinéraire

        // calcul des stations les plus proches par rapport à la position de l'utilisateur
        List<Station> plusProches = StationController.deuxplusProches(utilisateur);
        System.out.println("Station la plus proche : "+plusProches.get(0).getNomStation()+"\nSeconde station la plus proche : "+plusProches.get(1).getNomStation());
        // On vide la ligne avant d'en lire une autre
        String depart = "";
        String arrivee = "";
        System.out.println(StationController.listeStation());
        while(depart.equals(arrivee) || depart.equals("") || arrivee.equals("")) {
            if(depart.equals("")) {
                System.out.println("Indiquer votre départ  : ");
                depart = stationDansStations(sc);
            }
            if(arrivee.equals("")) {
                System.out.println("Indiquer votre arrivée : ");
                arrivee = stationDansStations(sc);
            }
            if (depart.equals(arrivee)) {
                System.out.println("Veuillez choisir un départ différent de l'arrivée !");
                depart = "";
                arrivee = "";
            }
            else if(StationController.getStations().get(depart).isIncident()){
                System.out.println("Votre départ est en travaux, veuillez choisir un autre départ");
                depart = "";
            }
            else if(StationController.getStations().get(arrivee).isIncident()){
                System.out.println("Votre arrivée est en travaux, veuillez choisir une autre arrivée");
                arrivee = "";
            }
            else {
                System.out.println("Vous avez choisi :\r Depart : " + depart + " |  Arrivee : " + arrivee + "");
            }
        }
        System.out.println("Quel type d'itineraire souhaitez-vous ?");
        System.out.println("[1] - Rapide");
        System.out.println("[2] - Points de passages");
        System.out.println("[3] - Moins de changements");
        int typeitineraire =  entierEntree(sc);
        ArrayList<Station> listeStations = new ArrayList<>();
        String reponseitineraire, passage, reponsepassage;
        boolean ajoutpassage = true;
        Station stationDepart = StationController.getStations().get(depart);
        Station stationArrivee = StationController.getStations().get(arrivee);

        //Itineraire le plus rapide
        if(typeitineraire==1) {
            reponseitineraire = itineraireController.itinerairePlusRapide(LigneController.getLignes(), StationController.getStations(), stationDepart, stationArrivee);
        }

        //Itineraire avec points de changement
        else if(typeitineraire==2) {
            sc.nextLine();
            listeStations.add(stationDepart);
            while(ajoutpassage){
                System.out.println("Souhaitez-vous ajouter un point de passage ? (Y pour continuer)");
                reponsepassage = sc.nextLine().toLowerCase();
                if(reponsepassage.equals("y")){
                    System.out.println("Indiquer votre point de passage");
                    passage = stationDansStations(sc);
                    if(!StationController.getStations().get(passage).isIncident()) {
                        listeStations.add(StationController.getStations().get(passage));
                    }else{
                        System.out.println("Votre point de passage est en travaux, veuillez en choisir un autre");
                    }
                }else {
                    ajoutpassage = false;
                }
            }
            listeStations.add(stationArrivee);
            reponseitineraire = itineraireController.itineraireAvecChangements(LigneController.getLignes(), StationController.getStations(), listeStations);

        //Itinéraire avec le moins de changement
        }else {
            reponseitineraire = itineraireController.itineraireMoinsChangements(LigneController.getLignes(), depart, arrivee);
        }
        System.out.println(reponseitineraire);
    }

    /**
     * Méthode qui permet à l'utilisateur d'afficher les informations d'une ligne
     * @param sc
     */
    private static void afficherLigneUtilisateur(Scanner sc){
        if(listeLignesExiste()){
            System.out.println("Veuillez choisir la ligne dont vous souhaitez des informations : ");
            nomLigne = sc.nextLine().toLowerCase();
            String message = LigneController.afficherLigne(nomLigne);
            System.out.println(message);
        }else{
            System.out.println("Il n'y a aucune ligne.");
        }
    }


    /**
     * Méthode qui permet d'affichier les horaires d'une station sur une ligne
     * @param sc
     */
    private static void afficherHoraireStationLigne(Scanner sc){
        if(listeLignesExiste()){
            System.out.println("Sur quelle ligne souhaitez vous connaitre les horaires ?");
            String lignehoraire = sc.nextLine().toLowerCase();
            if (LigneController.ligneExiste(lignehoraire)) {
                String message = LigneController.afficherLigne(lignehoraire);
                System.out.println(message);
                System.out.println("Sur quelle station souhaitez vous connaitre les horaires ?");
                String stationhoraire = sc.nextLine().toLowerCase();
                if (LigneController.getLignes().get(lignehoraire).trouverStation(stationhoraire)) {
                    LocalDateTime heure = LocalDateTime.now();
                    System.out.println(horaireController.afficherTableHoraire(StationController.getStations().get(stationhoraire), LigneController.getLignes().get(lignehoraire), heure, 5));
                } else {
                    System.out.println("Cette station n'existe pas sur cette ligne");
                }
            } else {
                System.out.println("Cette ligne n'existe pas");
            }
        }
    }

    /**
     * Méthode qui permet à l'utilisateur d'afficher les informations d'une station
     * @param sc
     */
    private static void afficherStationUtilisateur(Scanner sc){
        if(listeStationExiste()){
            System.out.println("Veuillez choisir la station dont vous souhaitez des informations : ");
            String nomStation = stationDansStations(sc);
            String message = StationController.afficherStation(nomStation);
            System.out.println(message);
        }else{
            System.out.println("Il n'y a aucune station.");
        }
    }

    /**
     * Méthode qui permet à l'utilisateur d'ajouter une ligne
     * @param sc
     */
    private static void ajouterLigneUtilisateur(Scanner sc){
        //paramètres
        String nomStation;
        ArrayList<Station> listeStation = new ArrayList<>();
        ArrayList<Integer> listeTempsParcours = new ArrayList<>();
        int tmpParc;

        System.out.println("Veuillez saisir les informations suivantes : ");

        //nom ligne
        System.out.println("- Nom de la ligne");
        nomLigne = sc.nextLine();

        //Liste de stations
        if(listeStationExiste()){
            System.out.println("- Veuillez saisir au moins 2 stations présentes sur une ligne " +
                    "(Saisissez 'Fin' lorsque vous aurez terminé) : ");
            while (true) {
                System.out.print("- ");
                nomStation = sc.nextLine().toLowerCase();
                if (nomStation.equals("fin")) {
                    if (listeStation.size() > 1) {
                        break;
                    } else {
                        System.out.println("Vous devez saisir au moins 2 stations");
                    }
                } else {
                    if (StationController.getStations().containsKey(nomStation)) {
                        listeStation.add(StationController.getStations().get(nomStation));
                    } else {
                        System.out.println("La station " + nomStation + " n'existe pas");
                    }
                }
                //Suppression des doublons
                listeStation = new ArrayList<>(new LinkedHashSet<>(listeStation));
            }

                //temps parcours
                System.out.println("Veuillez renseigner le temps de parcours pour chacune des stations de la ligne");
                for (int i = 0; i < (listeStation.size() - 1); i++) {
                    tmpParc = 0;
                    System.out.println("Temps de parcours entre " + listeStation.get(i).getNomStation() + " et " + listeStation.get(i + 1).getNomStation());
                    while (tmpParc <= 0) {
                        tmpParc = entierEntree(sc);
                        if (tmpParc <= 0) {
                            System.out.println("Le temps de parcours doit être strictement superieur à 0");
                        } else {
                            listeTempsParcours.add(tmpParc);
                        }
                    }
                }
                String message = LigneController.ajouterLigne(nomLigne, listeTempsParcours, listeStation);
                System.out.println(message);
            }else{
                System.out.println("Il n'y a aucune station de disponible, veuillez ajouter des stations avant d'ajouter une ligne.");
            }

    }

    /**
     * Méthode qui permet à l'utilisateur de supprimer une ligne
     * @param sc
     */
    private static void supprimerLigneUtilisateur(Scanner sc){
        if(listeLignesExiste()){
            System.out.println("Veuillez choisir la ligne que vous souhaitez supprimer : ");
            nomLigne = sc.nextLine().toLowerCase();
            System.out.println(LigneController.supprimerLigne(nomLigne));
        }else{
            System.out.println("Il n'y a aucune ligne.");
        }
    }

    /**
     * Méthode qui permet à l'utilisateur de modifier une ligne
     * @param sc
     */
    private static void modifierLigneUtilisateur(Scanner sc){
        String message;
        String station1, station2, listeStation;
        int tmpParc;
        if(listeLignesExiste()){
            System.out.println("Veuillez saisir les informations suivantes : ");
            System.out.println("Nom de la ligne");
            nomLigne = sc.nextLine().toLowerCase();
            if (LigneController.getLignes().containsKey(nomLigne)){
                Ligne ligne = LigneController.getLignes().get(nomLigne);
                System.out.println("[1] Modifier le temps de parcours entre 2 stations");
                System.out.println("[2] Ajouter une station entre 2 stations");
                System.out.println("[3] Supprimer une station entre 2 stations");
                System.out.println("[0] Fin dela modification d'une ligne");

                System.out.println("Entrez votre choix : ");
                int choix = -1;
                try {
                    choix = entierEntree(sc);
                } catch (InputMismatchException e) {
                    System.out.println("Votre choix est invalide. Veuillez choisir une option parmis celles proposées.");
                }

                switch (choix) {
                    case 1:
                        //modifier temps parcours
                        //Afficher les stations de la ligne
                        listeStation = LigneController.listeStationsParLigne(nomLigne);
                        System.out.println(listeStation);
                        sc.nextLine();

                        //Sélectionner 2 stations
                        System.out.println("Veuillez saisir les stations entre lesquelles vous voulez changer le temps de parcours");
                        System.out.println("Station 1 : ");
                        station1 = stationDansLigne(ligne, sc);

                        System.out.println("Station 2 : ");
                        station2 = stationDansLigne(ligne, sc);

                        //Saisir temps de parcours à modifier
                        System.out.println("Veuillez saisir le nouveau temps de parcours entre la station "+station1+" et la station "+station2);
                        tmpParc = entierEntree(sc);
                        while(tmpParc <= 0){
                            System.out.println("Le temps de parcours doit etre strictement supérieur à 0.");
                            tmpParc = entierEntree(sc);
                        }
                        message = LigneController.modifierLigneTempsParcours(nomLigne, station1, station2, tmpParc);
                        System.out.println(message);
                        break;

                    case 2:
                        //Ajouter une station entre 2
                        sc.nextLine();
                        //Paramètres
                        String stationNouv, stationPrec="", stationSuiv ="";
                        int tmpParcPrec=0, tmpParcSuiv=0;

                        //Afficher la liste des stations
                        if(listeStationExiste()){

                            //Saisir la station à ajouter
                            System.out.println("Veuillez saisir la station que vous souhaitez ajouter à la ligne " + nomLigne + " :");
                            stationNouv = stationDansStations(sc);
                            //Vérifier que la station n'est pas présente dans la ligne
                            if(!ligne.trouverStation(stationNouv)) {
                                    System.out.println("[1] Ajouter la station en départ");
                                    System.out.println("[2] Ajouter la station en terminus");
                                    System.out.println("[3] Ajouter la station entre 2 stations");
                                    System.out.println("Veuillez choisir l'action à réaliser :");
                                    int ch = -1;
                                    try {
                                        ch = entierEntree(sc);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Votre choix est invalide. Veuillez choisir une option parmis celles proposées.");
                                    }
                                    switch (ch) {
                                        case 1:
                                            sc.nextLine();
                                            //Départ
                                            //Afficher les stations de la ligne
                                            listeStation = LigneController.listeStationsParLigne(nomLigne);
                                            System.out.println(listeStation);
                                            stationSuiv = ligne.getListeStation().get(0).getNomStation();
                                            System.out.println("Veuillez saisir le temps de parcours entre " + stationNouv + " et " + stationSuiv + " :");
                                            tmpParcSuiv = entierEntree(sc);
                                            message = LigneController.modifierLigneAjouterStation(ch, StationController.getStations(), nomLigne, stationNouv, stationPrec, stationSuiv, tmpParcPrec, tmpParcSuiv);
                                            System.out.println(message);
                                            break;
                                        case 2:
                                            sc.nextLine();
                                            //Terminus
                                            //Afficher les stations de la ligne
                                            listeStation = LigneController.listeStationsParLigne(nomLigne);
                                            System.out.println(listeStation);
                                            stationPrec = ligne.getListeStation().get(ligne.getListeStation().size() - 1).getNomStation();
                                            System.out.println("Veuillez saisir le temps de parcours entre " + stationPrec + " et " + stationNouv + " :");
                                            tmpParcPrec = entierEntree(sc);
                                            message = LigneController.modifierLigneAjouterStation(ch, StationController.getStations(), nomLigne, stationNouv, stationPrec, stationSuiv, tmpParcPrec, tmpParcSuiv);
                                            System.out.println(message);
                                            break;
                                        case 3:
                                            sc.nextLine();
                                            //Entre deux stations
                                            //Afficher les stations de la ligne
                                            listeStation = LigneController.listeStationsParLigne(nomLigne);
                                            System.out.println(listeStation);
                                            System.out.println("Veuillez saisir les stations entre lesquelles vous voulez placer " + stationNouv + " :");
                                            System.out.print("Station précédente : ");
                                            stationPrec = sc.nextLine().toLowerCase();
                                            System.out.print("Station suivante : ");
                                            stationSuiv = sc.nextLine().toLowerCase();
                                            System.out.println("Veuillez saisir le temps de parcours entre " + stationPrec + " et " + stationNouv + " :");
                                            tmpParcPrec = entierEntree(sc);
                                            System.out.println("Veuillez saisir le temps de parcours entre " + stationNouv + " et " + stationSuiv + " :");
                                            tmpParcSuiv = entierEntree(sc);
                                            message = LigneController.modifierLigneAjouterStation(ch, StationController.getStations(), nomLigne, stationNouv, stationPrec, stationSuiv, tmpParcPrec, tmpParcSuiv);
                                            System.out.println(message);
                                            break;
                                    }
                            }else{
                                System.out.println("La station "+stationNouv+" existe déjà dans la ligne "+nomLigne+".");
                            }
                        }else{
                            System.out.println("Il n'y a aucune station.");
                        }

                        break;
                    case 3:
                        sc.nextLine();
                        //Supprimer une station entre 2
                        listeStation = LigneController.listeStationsParLigne(nomLigne);
                        System.out.println(listeStation);

                        //Sélectionner la station à supprimer
                        System.out.println("Veuillez saisir la station que vous souhaitez supprimer :");
                        station1 = sc.nextLine();

                        message = LigneController.modifierLigneSupprimerStation(nomLigne, station1);
                        System.out.println(message);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Votre choix est invalide. Veuillez choisir une option parmis celles proposées.");
                }
            }else{
                System.out.println("La ligne "+nomLigne+" n'existe pas.");
            }
        }else{
            System.out.println("Aucune ligne n'est disponible.");
        }
    }


    /**
     * Méthode qui permet à l'utilisateur de modifier l'incident d'une ligne
     * @param sc
     */
    private static void modifierLigneIncidentUtilisateur(Scanner sc){
        String nomLigne, incident;
        if(listeLignesExiste()) {
            System.out.println("Veuillez saisir les informations suivantes : ");
            System.out.println("Nom de la ligne");
            nomLigne = sc.nextLine().toLowerCase();
            System.out.println("Incident ?");
            System.out.println("y/n");
            incident = sc.nextLine().toLowerCase();
            String message = LigneController.modifierLigneIncident(nomLigne, incident);
            System.out.println(message);
        }else{
            System.out.println("Aucune ligne n'est disponible.");
        }
    }

    /**
     * Méthode qui permet à l'utilisateur d'ajouter une nouvelle station
     * @param sc
     */
    private static void ajouterStationUtilisateur(Scanner sc){
        String nomStation;
        int tempsArret;
        double latitude,longitude;
        System.out.println("Veuillez saisir les informations suivantes : ");
        System.out.println("Nom de la station");
        nomStation = sc.nextLine().toLowerCase();
        System.out.println("Temps d'arret de la station");
        tempsArret = entierEntree(sc);
        while(!Station.verifierTempsArret(tempsArret)){
            System.out.println("Votre temps d'arrêt est faux, recommencez");
            tempsArret = entierEntree(sc);
        }
        System.out.println("Latitude ? (Entre "+Position.getLATITUDE_MIN()+" et "+Position.getLATITUDE_MAX()+" )");
        latitude = sc.nextDouble();
        System.out.println("Longitude ? (Entre "+Position.getLONGITUDE_MIN()+" et "+Position.getLONGITUDE_MAX()+" )");
        longitude = sc.nextDouble();
        while(!Position.VerifierPosition(latitude,longitude)){
            System.out.println("Votre latitude ou longitude est fausse veuillez recommencer");
            System.out.println("Latitude ? (Entre "+Position.getLATITUDE_MIN()+"et "+Position.getLATITUDE_MAX()+" )");
            latitude = sc.nextDouble();
            System.out.println("Longitude ? (Entre "+Position.getLONGITUDE_MIN()+" et "+Position.getLONGITUDE_MAX()+" )");
            longitude = sc.nextDouble();
        }
        String message = StationController.ajouterStation(nomStation,tempsArret,false,latitude,longitude);
        System.out.println(message);
    }

    /**
     * Méthode qui permet à l'utilisateur de supprimer une station
     * @param sc
     */
    private static void supprimerStationUtilisateur(Scanner sc){
        String nomStation;
        if(listeStationExiste()) {
            System.out.println("Veuillez choisir la station que vous souhaitez supprimer : ");
            nomStation = stationDansStations(sc);
            System.out.println(StationController.supprimerStation(nomStation));
        }else{
            System.out.println("Aucune station n'est disponible.");
        }
    }

    /**
     * Méthode qui permet à l'utilisateur de modifier une station
     * @param sc
     */
    private static void modifierStationUtilisateur(Scanner sc) {
        String nomStation;
        int tempsArret;
        double latitude, longitude;

        //Afficher liste des stations
        if(listeStationExiste()) {
            System.out.println("Veuillez saisir les informations suivantes : ");
            System.out.println("Nom de la station");
            nomStation = stationDansStations(sc);
            System.out.println("Temps d'arret de la station");
            tempsArret = entierEntree(sc);
            while (!Station.verifierTempsArret(tempsArret)) {
                System.out.println("Votre temps d'arrêt est faux, recommencez");
                tempsArret = entierEntree(sc);
            }
            // Afficher la position actuelle de la station
            Position positionActuelle = StationController.getStations().get(nomStation).getPosition();
            System.out.println("Latitude ? (Entre " + Position.getLATITUDE_MIN() + " et " + Position.getLATITUDE_MAX() + " ) [actuellement " + positionActuelle.getLat() + "]");
            latitude = sc.nextDouble();
            System.out.println("Longitude ? (Entre " + Position.getLONGITUDE_MIN() + " et " + Position.getLONGITUDE_MAX() + " ) [actuellement " + positionActuelle.getLon() + "]");
            longitude = sc.nextDouble();
            while (!Position.VerifierPosition(latitude, longitude)) {
                System.out.println("Votre latitude ou longitude est fausse veuillez recommencer");
                System.out.println("Latitude ? (Entre " + Position.getLATITUDE_MIN() + "et " + Position.getLATITUDE_MAX() + " ) [actuellement " + positionActuelle.getLat() + "]");
                latitude = sc.nextDouble();
                System.out.println("Longitude ? (Entre " + Position.getLONGITUDE_MIN() + " et " + Position.getLONGITUDE_MAX() + " ) [actuellement " + positionActuelle.getLon() + "]");
                longitude = sc.nextDouble();
            }
            String message = StationController.modifierStation(nomStation, tempsArret, StationController.getStations().get(nomStation).isIncident(), latitude, longitude);
            System.out.println(message);
        }else{
            System.out.println("Aucune station n'est disponible.");
        }

    }

    /**
     * Méthode qui permet à l'utilisateur de modifier l'incident sur une station
     * @param sc
     */
    private static void modifierStationIncidentUtilisateur(Scanner sc){
        String nomStation,inc;
        boolean incident = false;
        if(listeStationExiste()) {
            System.out.println("Veuillez saisir les informations suivantes : ");
            System.out.println("Nom de la station");
            nomStation = stationDansStations(sc);
            System.out.println("Incident ?");
            System.out.println("y/n");
            inc = sc.nextLine().toLowerCase();
            if (inc.equals("y")) {
                incident = true;
            }
            if (inc.equals("y") || inc.equals("n")) {
                StationController.modifierStationIncident(nomStation, incident);
                System.out.println("L'incident de la station " + nomStation.toLowerCase() + " est maintenant " + incident);
            } else {
                System.out.println("Le systeme n'a pas compris votre reponse d'incident");
            }
        }else{
            System.out.println("Aucune station n'est disponible.");
        }
    }

    /**
     * Méthode pour vérifier qu'un entier positif ou nul est saisie
     *
     * @return integer la valeur de l'entier
     */
    private static int entierEntree(Scanner sc){
        int choix = 0;
        boolean valide = false;
        while (!valide) {
            try {
                choix = sc.nextInt();
                valide = choix >=0;
            } catch (Exception e) {
                System.out.println("Veuillez saisir un nombre entier. \n");
                sc.nextLine();
                valide = false;
            }
        }
        return choix;
    }

    /**
     * Méthode pour vérifier qu'une station se trouve dans une ligne
     *
     * @return string la valeur de la station
     */
    private static String stationDansLigne(Ligne ligne,Scanner sc){
        String station = sc.nextLine().toLowerCase();
        while (!ligne.trouverStation(station)) {
            System.out.println("La station "+station+" n'existe pas sur la ligne "+ligne.getNomLigne()+".\nVeuillez recommencer.");
            station = sc.nextLine().toLowerCase();
        }
        return station;
    }

    /**
     * Méthode pour vérifier qu'une station existe dans la liste des stations
     *
     * @return string la valeur de la station
     */
    private static String stationDansStations(Scanner sc){
        String station = sc.nextLine().toLowerCase();
        while (!StationController.getStations().containsKey(station)) {
            System.out.println("La station "+station+" n'existe pas sur la liste des stations.\nVeuillez recommencer.");
            station = sc.nextLine().toLowerCase();
        }
        return station;
    }

    /**
     * Méthode qui permet d'afficher la liste des lignes
     *
     * @return boolean si la liste est remplie
     */
    private static boolean listeLignesExiste(){
        boolean existe=false;
        System.out.println("Les lignes disponibles sont : ");
        String listeLigneExiste = LigneController.listeLigne();
        //Si la liste existe
        if(!listeLigneExiste.equals("noLigne")){
            System.out.println(listeLigneExiste);
            existe = true;
        }
        return existe;
    }

    /**
     * Méthode qui permet d'afficher la liste des stations
     *
     * @return boolean si la liste est remplie
     */
    private static boolean listeStationExiste(){
        boolean existe=false;
        System.out.println("Les stations disponibles sont : ");
        String listeStationExiste = StationController.listeStation();
        //Si la liste existe
        if(!listeStationExiste.equals("noLigne")){
            System.out.println(listeStationExiste);
            existe = true;
        }
        return existe;
    }

}
