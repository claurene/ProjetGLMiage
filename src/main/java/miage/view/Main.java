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

        private static String listeLigneExiste, listeStationExiste, nomLigne;

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
                System.out.println("[4] Ajouter une ligne de métro");
                System.out.println("[5] Supprimer une ligne de métro");
                System.out.println("[6] Modifier une ligne de métro");
                System.out.println("[7] Ajouter une station de métro");
                System.out.println("[8] Supprimer une station de métro");
                System.out.println("[9] Modifier une station de métro");
                System.out.println("[10] Modifier un incident de station de métro");
                System.out.println("[0] Quitter l'application");

                // Gestion des choix de l'utilisateur
                Scanner sc = new Scanner(System.in);
                sc.useLocale(Locale.US);
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
                        while(depart.equals(arrivee) || depart.equals("") || arrivee.equals("")) {
                            if(depart.equals("")) {
                                System.out.println("Indiquer votre départ  : ");
                                depart = sc.nextLine();
                            }
                            while(!stationController.getStations().containsKey(depart)) {
                                System.out.println("La station demandée n'existe pas, réessayez");
                                depart = sc.nextLine();
                            }
                            if(arrivee.equals("")) {
                                System.out.println("Indiquer votre arrivée : ");
                                arrivee = sc.nextLine();
                            }
                            while(!stationController.getStations().containsKey(arrivee)) {
                                System.out.println("La station demandée n'existe pas, réessayez");
                                arrivee = sc.nextLine();
                            }
                            if (depart.equals(arrivee)) {
                                System.out.println("Veuillez choisir un départ différent de l'arrivée !");
                                depart = "";
                                arrivee = "";
                            }
                            else if(stationController.getStations().get(depart).isIncident()){
                                System.out.println("Votre départ est en travaux, veuillez choisir un autre départ");
                                depart = "";
                            }
                            else if(stationController.getStations().get(arrivee).isIncident()){
                                System.out.println("Votre arrivée est en travaux, veuillez choisir une autre arrivée");
                                arrivee = "";
                            }
                            else {
                                System.out.println("Vous avez choisi :\r Depart : " + depart + " |  Arrivee : " + arrivee + "");
                            }
                        }
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
                        //TODO Modifier une ligne de métro
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
            ligneController.sauvegardeLignes();

            // Sauvegarde des stations
            stationController.sauvegardeStations();
        }

        /**
         * Méthode qui permet à l'utilisateur d'afficher les informations d'une ligne
         * @param sc
         */
        public static void afficherLigneUtilisateur(Scanner sc){
            System.out.println("Les lignes disponibles sont : ");
            listeLigneExiste = ligneController.listeLigne();
            if(!listeLigneExiste.equals("noLigne")){
                System.out.println(listeLigneExiste);
                System.out.println("Veuillez choisir la ligne dont vous souhaitez des informations : ");
                nomLigne = sc.nextLine().toLowerCase();
                String message = ligneController.afficherLigne(nomLigne);
                System.out.println(message);
            }else{
                System.out.println("Il n'y a aucune ligne.");
            }
        }

        /**
         * Méthode qui permet à l'utilisateur d'afficher les informations d'une station
         * @param sc
         */
        public static void afficherStationUtilisateur(Scanner sc){
            System.out.println("Les stations disponibles sont : ");
            listeStationExiste = stationController.listeStation();
            if(!listeStationExiste.equals("noStation")){
                System.out.println(listeStationExiste);
                System.out.println("Veuillez choisir la station dont vous souhaitez des informations : ");
                String nomStation = sc.nextLine().toLowerCase();
                String message = stationController.afficherStation(nomStation);
                System.out.println(message);
            }else{
                System.out.println("Il n'y a aucune station.");
            }
        }

        /**
         * Méthode qui permet à l'utilisateur d'ajouter une ligne
         * @param sc
         */
        public static void ajouterLigneUtilisateur(Scanner sc){
            //paramètres
            String nomStation;
            ArrayList<Station> listeStation = new ArrayList<Station>();
            ArrayList<Integer> listeTempsParcours = new ArrayList<>();
            int tmpParc;

            System.out.println("Veuillez saisir les informations suivantes : ");

            //nom ligne
            System.out.println("- Nom de la ligne");
            nomLigne = sc.nextLine();

            //Liste de stations
            listeStationExiste = stationController.listeStation();
            //sc.nextLine();
            if (!listeStationExiste.equals("noStation")) {
                System.out.println(listeStationExiste);
                System.out.println("- Veuillez saisir au moins 2 stations présentes sur une ligne " +
                        "(Saisissez 'Fin' lorsque vous aurez terminé) : ");

                while(true){
                    System.out.print("- ");
                    nomStation = sc.nextLine().toLowerCase();
                    if (nomStation.equals("fin")) {
                        break;
                    } else {
                        if(stationController.getStations().containsKey(nomStation)){
                            listeStation.add(stationController.getStations().get(nomStation));
                        }else{
                            System.out.println("La station "+ nomStation +" n'existe pas");
                        }
                    }

                }
                //Suppression des doublons
                listeStation = new ArrayList<Station>(new LinkedHashSet<Station>(listeStation));
            }else{
                System.out.println("Il n'y a aucune station de disponible, veuillez ajouter des stations avant d'ajouter une ligne.");
            }

            //temps parcours
            System.out.println("Veuillez renseigner le temps de parcours pour chacune des stations de la ligne");
            for (int i =0; i<(listeStation.size()-1);i++){
                tmpParc = 0;
                System.out.println("Temps de parcours entre "+listeStation.get(i).getNomStation()+" et "+listeStation.get(i+1).getNomStation());
                while(tmpParc <= 0 ){
                    tmpParc = sc.nextInt();
                    if(tmpParc <= 0){
                        System.out.println("Le temps de parcours doit être strictement superieur à 0");
                    } else {
                        listeTempsParcours.add(tmpParc);
                    }
                }
            }

            String message = ligneController.ajouterLigne(nomLigne, listeTempsParcours, listeStation);
            System.out.println(message);
        }

        /**
         * Méthode qui permet à l'utilisateur de supprimer une ligne
         * @param sc
         */
        public static void supprimerLigneUtilisateur(Scanner sc){
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
        }

        /**
         * Méthode qui permet à l'utilisateur de modifier une ligne
         * @param sc
         */
        public static void modifierLigneUtilisateur(Scanner sc){
            boolean run = true;
            System.out.println("Les lignes disponibles sont : ");
            listeLigneExiste = ligneController.listeLigne();
            if(!listeLigneExiste.equals("noLigne")) {
                System.out.println(listeLigneExiste);
                System.out.println("Veuillez saisir les informations suivantes : ");
                System.out.println("Nom de la ligne");
                nomLigne = sc.nextLine().toLowerCase();
                if (ligneController.getLignes().containsKey(nomLigne)){
                    while(run) {
                        System.out.println("[1] Modifier le temps de parcours entre 2 stations");
                        System.out.println("[2] Ajouter une station entre 2 stations");
                        System.out.println("[3] Supprimer une station entre 2 stations");
                        System.out.println("[0] Fin dela modification d'une ligne");

                        System.out.println("Entrez votre choix : ");
                        int choix;
                        try {
                            choix = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Votre choix est invalide. Veuillez choisir une option parmis celles proposées.");
                            continue;
                        }
                        switch (choix) {
                            case 1:
                                //TODO modifier temps parcours
                                //Afficher les stations de la ligne
                                String listeStation = ligneController.listeStationsParLigne(nomLigne);
                                System.out.println(listeStation);
                                sc.nextLine();

                                //Sélectionner 2 stations
                                System.out.println("Veuillez saisir les stations entre lesquelles vous voulez changer le temps de parcours");
                                System.out.println("Station 1 : ");
                                String station1 = sc.nextLine().toLowerCase();
                                System.out.println("Station 2 : ");
                                String station2 = sc.nextLine().toLowerCase();

                                //Saisir temps de parcours à modifier
                                System.out.println("Veuillez saisir le nouveau temps de parcours entre la station "+station1+" et la station "+station2);
                                int tmpParc = sc.nextInt();

                                String message = ligneController.modifierLigneTempsParcours(nomLigne, station1, station2, tmpParc);
                                System.out.println(message);
                                break;
                            case 2:
                                //TODO Ajouter une station entre 2
                                break;
                            case 3:
                                //TODO Supprimer une station entre 2
                                break;
                            case 0:
                                run = false;
                                break;
                            default:
                                System.out.println("Votre choix est invalide. Veuillez choisir une option parmis celles proposées.");
                        }
                    }
                }else{
                    System.out.println("La ligne "+nomLigne+" n'existe pas.");
                }
            }else{
                System.out.println("Aucune ligne n'est disponble.");
            }
        }

        /**
         * Méthode qui permet à l'utilisateur de modifier l'incident d'une ligne
         * @param sc
         */
        public static void modifierLigneIncidentUtilisateur(Scanner sc){
            String nomLigne, incident;
            System.out.println("Veuillez saisir les informations suivantes : ");
            System.out.println("Nom de la station");
            nomLigne = sc.nextLine().toLowerCase();
            System.out.println("Incident ?");
            System.out.println("y/n");
            incident = sc.nextLine().toLowerCase();
            String message = ligneController.modifierLigneIncident(nomLigne,incident);
            System.out.println(message);
        }

        public static void ajouterStationUtilisateur(Scanner sc){
            String nomStation;
            int tempsArret;
            double latitude,longitude;
            System.out.println("Veuillez saisir les informations suivantes : ");
            System.out.println("Nom de la station");
            nomStation = sc.nextLine().toLowerCase();
            System.out.println("Temps d'arret de la station");
            tempsArret = sc.nextInt();
            while(!Station.verifierTempsArret(tempsArret)){
                System.out.println("Votre temps d'arrêt est faux recommencez");
                tempsArret = sc.nextInt();
            }
            System.out.println("Latitude ? (Entre "+Position.getLATITUDE_MIN()+" et "+Position.getLATITUDE_MAX()+" )");
            latitude = sc.nextDouble();
            System.out.println("Longitude ? (Entre "+Position.getLONGITUDE_MIN()+" et "+Position.getLONGITUDE_MAX()+" )");
            longitude = sc.nextDouble();
            while(!Position.VerifierPosition(latitude,longitude)){
                System.out.println("Votre latitude ou longitude est fausse veuillez recommencez");
                System.out.println("Latitude ? (Entre "+Position.getLATITUDE_MIN()+"et "+Position.getLATITUDE_MAX()+" )");
                latitude = sc.nextDouble();
                System.out.println("Longitude ? (Entre "+Position.getLONGITUDE_MIN()+" et "+Position.getLONGITUDE_MAX()+" )");
                longitude = sc.nextDouble();
            }
            String message = stationController.ajouterStation(nomStation,tempsArret,false,latitude,longitude);
            System.out.println(message);
        }

        public static void supprimerStationUtilisateur(Scanner sc){
            //TODO supprimer des lignes aussi et mettre a jour le temps de parcours
            String nomStation;
            System.out.println("Les stations disponibles sont :");
            System.out.println(stationController.listeStation());
            System.out.println("Veuillez choisir la station que vous souhaitez supprimer : ");
            nomStation = sc.nextLine().toLowerCase();
            System.out.println(stationController.supprimerStation(nomStation,ligneController));
        }

        public static void modifierStationUtilisateur(Scanner sc) {
            String nomStation;
            int tempsArret;
            double latitude, longitude;
            System.out.println("Veuillez saisir les informations suivantes : ");
            System.out.println("Nom de la station");
            nomStation = sc.nextLine().toLowerCase();
            if (stationController.getStations().containsKey(nomStation)){
                System.out.println("Temps d'arret de la station");
                tempsArret = sc.nextInt();
                while (!Station.verifierTempsArret(tempsArret)) {
                    System.out.println("Votre temps d'arrêt est faux recommencez");
                    tempsArret = sc.nextInt();
                }
                System.out.println("Latitude ? (Entre " + Position.getLATITUDE_MIN() + " et " + Position.getLATITUDE_MAX() + " )");
                latitude = sc.nextDouble();
                System.out.println("Longitude ? (Entre " + Position.getLONGITUDE_MIN() + " et " + Position.getLONGITUDE_MAX() + " )");
                longitude = sc.nextDouble();
                while (!Position.VerifierPosition(latitude, longitude)) {
                    System.out.println("Votre latitude ou longitude est fausse veuillez recommencez");
                    System.out.println("Latitude ? (Entre " + Position.getLATITUDE_MIN() + "et " + Position.getLATITUDE_MAX() + " )");
                    latitude = sc.nextDouble();
                    System.out.println("Longitude ? (Entre " + Position.getLONGITUDE_MIN() + " et " + Position.getLONGITUDE_MAX() + " )");
                    longitude = sc.nextDouble();
                }
                String message = stationController.modifierStation(nomStation, tempsArret, stationController.getStations().get(nomStation).isIncident(), latitude, longitude);
                System.out.println(message);
        }
        else{
                System.out.println("La station n'existe pas");
        }

        }

        public static void modifierStationIncidentUtilisateur(Scanner sc){
            String nomStation,inc;
            boolean incident = false;
            System.out.println("Veuillez saisir les informations suivantes : ");
            System.out.println("Nom de la station");
            nomStation = sc.nextLine().toLowerCase();
            if(stationController.getStations().containsKey(nomStation)){
                System.out.println("Incident ?");
                System.out.println("y/n");
                inc = sc.nextLine().toLowerCase();
                if(inc.equals("y")){
                    incident = true;
                }
                if(inc.equals("y")||inc.equals("n")){
                    stationController.modifierStationIncident(nomStation,incident);
                    System.out.println("L'incident de la station "+nomStation.toLowerCase()+" est maintenant "+incident);
                }
                else{
                    System.out.println("Le systeme n'a pas compris votre reponse d'incident");
                }
            }
            else{
                System.out.println("La station n'existe pas");
            }
        }

    }
