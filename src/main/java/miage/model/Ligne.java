package miage.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe ligne qui permet de modéliser une ligne de metro
 */

public class Ligne implements Serializable {
    private static final long serialVersionUID = -5023105476811757582L;
    private String stationDepart;
    private int id;
    private String nomLigne;
    private ArrayList<Integer> tempsParcours;
    private boolean incident;
    private ArrayList<Station> listeStation;


    /**
     * Constructeur d'une ligne de metro
     * @param id id de la ligne
     * @param nomLigne nom de la ligne
     * @param tempsParcours liste de temps de parcours entre deux stations
     * @param listeStation liste des stations de la ligne
     */
    public Ligne(int id, String nomLigne, ArrayList<Integer> tempsParcours, ArrayList<Station> listeStation) {
        this.id = id;
        this.nomLigne = nomLigne;
        this.tempsParcours = tempsParcours;
        this.listeStation = listeStation;
        for (Station aListeStation : this.listeStation) {
            if (aListeStation.isIncident()) {
                this.incident = true;
                break;
            } else {
                this.incident = false;
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomLigne() {
        return nomLigne;
    }

    public void setNomLigne(String nomLigne) {
        this.nomLigne = nomLigne;
    }

    public ArrayList<Integer> getListeTempsParcours() {
        return tempsParcours;
    }

    /**
    * permet de récupérer le temps de parcours entre les deux stations de la ligne
     * @param stationDepart nom de la station de départ
     * @param tempsParcours liste des temps de parcours
     * @return le temps de parcours entre la station précisée et la suivante
     **/

    public int getTempsParcours(String stationDepart, ArrayList<Integer> tempsParcours){
        this.tempsParcours = tempsParcours;
        this.stationDepart = stationDepart;
        int positionListe = trouverPosListeStation(stationDepart);
        return tempsParcours.get(positionListe);
    }

    public void setListeTempsParcours(ArrayList<Integer> tempsParcours) {
        this.tempsParcours = tempsParcours;
    }

    /**
     * permet de modifier le temps de parcours entre les deux stations de la ligne
     * @param temps nouvelle valeur pour le temps de parcours
     * @param stationDepart nom de la station de départ
     * @param tempsParcours liste des temps de parcours
     **/


    public void setTempsParcours(int temps, String stationDepart, ArrayList<Integer> tempsParcours){
        this.tempsParcours = tempsParcours;
        this.stationDepart = stationDepart;
        int positionListe = trouverPosListeStation(stationDepart);
        tempsParcours.set(positionListe, temps);
    }

    public boolean isIncident() {
        return incident;
    }

    public void setIncident(boolean incident) {
        this.incident = incident;
    }

    public ArrayList<Station> getListeStation() {
        return listeStation;
    }

    public void setListeStation(ArrayList<Station> listeStation) {
        this.listeStation = listeStation;
    }

    public int getTotalTempsParcours(){
        // Le temps de parcours total équivaut au temps de parcours entre la première et la dernière station de la ligne, sans compter leur temps d'arrêt
        int total = this.tempsParcours.get(0);
        for (int i=1;i<this.tempsParcours.size();i++) {
            total+=this.tempsParcours.get(i)+this.listeStation.get(i).getTempsArret();
        }
        return total;
    }

    @Override
    public String toString() {
        String reponse;
        if(incident){
            reponse = "La ligne : " + nomLigne + ", d'id "+ id +", a un temps de parcours de " + getTotalTempsParcours() +
                    " minutes et possède un incident. Elle passe par les stations suivantes : \n";
            for (Station aListeStation : listeStation) {
                reponse += "-" + aListeStation.getNomStation() + "\n";
            }
        }else{
            reponse = "La ligne : " + nomLigne + ", d'id "+ id +", a un temps de parcours de " + getTotalTempsParcours() +
                    " minutes et ne possède pas d'incident. Elle passe par les stations suivantes :\n";
            for (Station aListeStation : listeStation) {
                reponse += "-" + aListeStation.getNomStation() + "\n";
            }
        }
        return reponse;
    }

    /**
     * Méthode qui permet de trouver si une station appartient à une ligne
     * @param nomStation nom de la station a chercher
     * @return booleen déterminant si la station existe ou pas
     */
    public boolean trouverStation(String nomStation){
        String nomStationActuelle;
        Boolean existe = false;
        for (Station aListeStation : this.listeStation) {
            nomStationActuelle = aListeStation.getNomStation();
            if (nomStation == nomStationActuelle) {
                existe = true;
            }
        }
        return existe;
    }

    public int trouverPosListeStation(String nomStation){
        String nomStationActuelle;
        int posListe = -1;
        for(int i=0; i<this.listeStation.size(); i++){
            nomStationActuelle = this.listeStation.get(i).getNomStation();
            if (nomStation.equals(nomStationActuelle)){
                posListe = i;
            }
        }
        return posListe;
    }

    /**
     * Retourne la station terminus (dernier élément de listeStation)
     * @return station terminus
     */
    public Station getTerminus(){
        return this.listeStation.get(this.listeStation.size()-1);
    }

    /**
     * Retourne la station de départ (premier élément de listeStation)
     * @return station départ
     */
    public Station getDepart(){
        return this.listeStation.get(0);
    }

}
