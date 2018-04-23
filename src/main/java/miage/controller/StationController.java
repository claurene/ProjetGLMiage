package miage.controller;

import miage.model.Station;
import miage.model.Position;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;



public class StationController {
    // Création du logger
    private static final Logger LOG = Logger.getLogger(StationController.class.getName());

    //Liste des stations de metro
    HashMap<String,Station> stations = new HashMap<String,Station>();

    public HashMap<String, Station> getStations() {
        return stations;
    }

    public void setStations(HashMap<String, Station> stations) {
        this.stations = stations;
    }

    /**
     * Méthode qui permet de recuperer les donnees du fichier stations.txt
     */
    public void initialisationStations(){
        FileInputStream in = null;
        ObjectInputStream ois = null;

        try {
            in = new FileInputStream("src/main/resources/stations.txt");
            try {
                ois = new ObjectInputStream(in);
                while(true) {
                    try {
                        Station s = (Station) ois.readObject();
                        this.stations.put(s.getNomStation(),s);
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
    }

    /**
     * Methode qui permet de sauvegarder les nouvelles donnees dans le fichier stations.txt
     */
    public void sauvegardeStations(){
        FileOutputStream out=null;
        ObjectOutputStream oos=null;

        try {
            out = new FileOutputStream("src/main/resources/stations.txt");
        } catch (FileNotFoundException e) {
            // Fichier non trouvé : création du fichier
            LOG.info("Création d'un fichier stations.txt");
        }

        try {
            oos = new ObjectOutputStream(out);
            for (HashMap.Entry<String,Station> entry : this.stations.entrySet()) {
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

    /**
     * Méthode qui permet de lister toutes les stations existantes
     * @return string la liste des stations
     */
    public String listeStation(){
        String reponse = "";
        if(this.stations.size()>0){
            for(Map.Entry<String, Station> station : this.stations.entrySet()){
                reponse += station.getKey()+"\n";
            }
        } else {
            reponse = "noStation";
        }
        return reponse;
    }

    /**
     * Methode qui affiche les informations d'une station
     * @param nomStation la station dont l'utilisateur veut les informations
     * @return string les informations de la station
     */
    public String afficherStation(String nomStation){
        String reponse = "";
        if(this.stations.containsKey(nomStation)){
            reponse += this.stations.get(nomStation);
        }else{
            reponse = "La station "+nomStation+" n'existe pas.";
        }
        return reponse;
    }

    /**
     * Méthode qui permet d'ajouter une station dans le fichier de sauvegarde
     * @param nom , le nom de la station
     * @param temps , le temps d'arret de la station
     * @param inc , présence d'un incident ou non
     * @param latitude , la latitude de la station
     * @param longitude , la longitude de la station
     * @return reponse , la reponse de l'ajout de la station
     */
    public String ajouterStation(String nom, int temps, boolean inc, double latitude, double longitude){
        String reponse ="";
        Station s = new Station(nom, temps, inc, latitude,longitude);
        if(s.getPosition().VerifierPosition(s.getPosition().getLat(),s.getPosition().getLon())) {
            if(this.stations.containsKey(nom)){
                this.stations.put(nom,s);
                reponse = "La station a été remplacée";
            }
            else {
                this.stations.put(nom, s);
                reponse = "La station a bien été ajoutée";
            }
        }
        else{
            reponse = "La station n'a pas été ajoutée pour cause d'erreur de position";
        }
        return reponse;
    }

    /**
     * Methode permettant de supprimer une station
     * @param nom , le nom de la station a supprimée
     * @return reponse, la reponse a affichée
     */
    public String supprimerStation(String nom){
        String reponse ="";
        if(this.stations.containsKey(nom)) {
            this.stations.remove(nom);
            reponse = "La station a bien été supprimée";
        }
        else{
            reponse = "La station n'a pas pu être supprimée car elle n'existe pas";
        }
        return reponse;
    }



    /**
     * Fonction qui permet de renvoyer sous forme de liste les deux stations les plus proches d'une position
     * @param utilisateur position par rapport à laquelle on veut calculer les stations les plus proches
     * @param stations Hashmap des différentes stations
     * @return une Liste comprenant les deux stations les plus proches
     */
    public static List<Station> deuxplusProches(Position utilisateur, HashMap<String,Station> stations){
        // initialisation des variables
        String clefs[] =  {"", ""};
        List<Station> proches = new ArrayList<>();
        double premier, deuxieme;
        // On initialise ces variables la valeur maximale comme on cherche les valeurs minimales
        premier = deuxieme = Double.MAX_VALUE;

        // parcours de la hashmap
        for(Map.Entry<String, Station> entry : stations.entrySet()) {

            double distance = utilisateur.distance(entry.getValue().getPosition());
            // on passe l'itération s'il y a un accident
            if (entry.getValue().isIncident()){ continue; }
            // on trie et on sauvegarde les clefs correspondantes
            if (distance<premier){
                deuxieme = premier;
                premier = distance;
                clefs[1] = clefs[0];
                clefs[0] = entry.getKey();
            } else if(distance<deuxieme && distance != premier){
                deuxieme = distance;
                clefs[1] = entry.getKey();
            }
        }
        proches.add(stations.get(clefs[0]));
        proches.add(stations.get(clefs[1]));
        return proches;

    }
}
