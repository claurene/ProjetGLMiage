package miage.controller;

import miage.model.Ligne;
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
    private HashMap<String,Station> stations = new HashMap<String,Station>();

    private static LigneController ligneController = new LigneController();

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
        if(s.getPosition().VerifierPosition(s.getPosition().getLat(),s.getPosition().getLon()) && nom != "") {
            if(this.stations.containsKey(nom)){
                reponse = "La station existe déjà";
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
     * Méthode qui permet de modifier une station dans le fichier de sauvegarde
     * @param nom , le nom de la station
     * @param temps , le temps d'arret de la station
     * @param inc , présence d'un incident ou non
     * @param latitude , la latitude de la station
     * @param longitude , la longitude de la station
     * @return reponse , la reponse de la modification de la station
     */
    public String modifierStation(String nom, int temps, boolean inc, double latitude, double longitude){
        String reponse ="";
        Station s = new Station(nom, temps, inc, latitude,longitude);
        if(s.getPosition().VerifierPosition(s.getPosition().getLat(),s.getPosition().getLon()) && nom != "") {
            if(this.stations.containsKey(nom)){
                this.stations.put(nom,s);
                reponse = "La station a été remplacée";
            }
            else {
                reponse = "La station que vous souhaitez modifier n'existe pas";
            }
        }
        else{
            reponse = "La station n'a pas été modifiée pour cause d'erreur de position";
        }
        return reponse;
    }

    /**
     * Méthode qui permet de modifier l'incident d'une station dans le fichier de sauvegarde
     * @param nom , le nom de la station
     * @param inc , présence d'un incident ou non
     * @return reponse , la reponse de la modification de l'incident de la station
     */
    public String modifierStationIncident(String nom, boolean inc) {
        String reponse = "";
        if (this.stations.containsKey(nom)) {
            Station s = this.stations.get(nom);
            s.setIncident(inc);
            this.stations.put(nom, s);
            if (inc)
                reponse = "La station possède maintenant un incident";
            else
                reponse = "La station ne possède maintenant plus d'incident";
        } else
            reponse = "La station que vous souhaitez modifiée n'existe pas";
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

    /**
     * Fonction qui permet de calculer le temps de parcours + temps d'arrêt entre deux stations d'une même ligne
     * @param station1
     * @param station2
     * @return int du temps de parcours entre les deux stations
     */

    public int calculTempsParcours(Station station1, Station station2){
        //todo gérer le calcul dans les deux sens (i--)
        ligneController.initialisationLignes();

        String nomDepart = station1.getNomStation();
        String nomArrivee = station2.getNomStation();

        List<String> lignesDepart = ligneController.lignesDeLaStation(nomDepart);
        List<String> lignesArrivee = ligneController.lignesDeLaStation(nomArrivee);

        for(int i = lignesArrivee.size() - 1; i > -1; --i){
            String str = lignesArrivee.get(i);
            if(!lignesDepart.remove(str))
                lignesArrivee.remove(str);
        }

        if (lignesArrivee.isEmpty()){
            System.out.println("Les stations ne font pas partie de la même ligne");
            return 0;
        }

        Ligne ligne = ligneController.getLignes().get(lignesArrivee.get(0));
        ArrayList<Station> listeStations = ligneController.getLignes().get(lignesArrivee.get(0)).getListeStation();
        int debutIndex = ligneController.getLignes().get(lignesArrivee.get(0)).trouverPosListeStation(nomDepart);
        int tempsParcours = ligne.getTempsParcours(listeStations.get(debutIndex).getNomStation(),ligne.getListeTempsParcours());

        for (int i = debutIndex+1; !listeStations.get(i).getNomStation().equals(nomArrivee); i++ ){
            tempsParcours += listeStations.get(i).getTempsArret() + ligne.getTempsParcours(listeStations.get(i).getNomStation(),ligne.getListeTempsParcours());
        }

        return tempsParcours;
    }

}
