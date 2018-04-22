package miage.view;

import miage.controller.LigneController;
import miage.model.*;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    // Création du logger
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private static Position utilisateur = new Position();

    private static LigneController ligneController = new LigneController();
    /*
     * Lancement du programme principal
     */
    public static void main(String args[]){
        // Booléen vrai tant qu'on est dans le système
        boolean running = true;

        // Liste des lignes et stations de métro
        HashMap<String,Ligne> lignes = new HashMap<String,Ligne>();
        HashMap<String,Station> stations = new HashMap<String,Station>();

        // Chargement des lignes de métro

        FileInputStream in = null;
        ObjectInputStream ois = null;

        try {
            in = new FileInputStream("src/main/resources/lignes.txt");
            try {
                ois = new ObjectInputStream(in);
                while(true) {
                    try {
                        Ligne l = (Ligne) ois.readObject();
                        lignes.put(l.getNomLigne(),l);
                    } catch (EOFException e){
                        break;
                    }
                }

            } catch (StreamCorruptedException e) {
                // Fichier corrompu
                LOG.warning("Fichier lignes.txt corrompu");
            }  catch (Exception e ) {
                e.printStackTrace();
            } finally {
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
        } catch (FileNotFoundException e) {
            // Fichier ligne non trouvé
            LOG.warning("Aucun fichier lignes.txt trouvé");
        }

        // Chargement des stations de métro

        try {
            in = new FileInputStream("src/main/resources/stations.txt");
            try {
                ois = new ObjectInputStream(in);
                while(true) {
                    try {
                        Station s = (Station) ois.readObject();
                        stations.put(s.getNomStation(),s);
                    } catch (EOFException e){
                        break;
                    }
                }

            } catch (StreamCorruptedException e) {
                // Fichier corrompu
                LOG.warning("Fichier stations.txt corrompu");
            }  catch (Exception e ) {
                e.printStackTrace();
            } finally {
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
        } catch (FileNotFoundException e) {
            // Fichier ligne non trouvé
            LOG.warning("Aucun fichier stations.txt trouvé");
        }

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
                    // Afficher les informations d'une ligne de métro
                    boolean listeExiste = ligneController.listeLigne(lignes);
                    if(listeExiste) {
                        System.out.println("Veuillez choisir la ligne dont vous souhaitez des informations : ");
                        String nomLigne = sc.nextLine().toLowerCase();
                        String ligne = ligneController.afficherLigne(lignes, nomLigne);
                    }

                    // En attente d'une nouvelle commande
                    break;

                case 3:
                    // Afficher les informations d'une station de métro
                    //TODO

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

        FileOutputStream out=null;
        ObjectOutputStream oos=null;

        try {
            out = new FileOutputStream("src/main/resources/lignes.txt");
        } catch (FileNotFoundException e) {
            // Fichier non trouvé : création du fichier
            LOG.info("Création d'un fichier lignes.txt");
        }

        try {
            oos = new ObjectOutputStream(out);
            for (HashMap.Entry<String,Ligne> entry : lignes.entrySet()) {
                oos.writeObject(entry.getValue());
            }
            oos.flush();
        } catch (IOException e) {
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
            }
        }

        // Sauvegarde des stations

        try {
            out = new FileOutputStream("src/main/resources/stations.txt");
        } catch (FileNotFoundException e) {
            // Fichier non trouvé : création du fichier
            LOG.info("Création d'un fichier stations.txt");
        }

        try {
            oos = new ObjectOutputStream(out);
            for (HashMap.Entry<String,Station> entry : stations.entrySet()) {
                oos.writeObject(entry.getValue());
            }
            oos.flush();
        } catch (IOException e) {
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
            }
        }
    }
}
