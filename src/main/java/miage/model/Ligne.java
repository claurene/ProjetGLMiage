package miage.model;

import java.util.ArrayList;

/**
 * Classe ligne qui permet de modéliser une ligne de metro
 */

public class Ligne {
    private int id;
    private String nomLigne;
    private int tempsParcours;
    private boolean incident;
    private ArrayList<Station> listeStation;

    /**
     * Constructeur d'une ligne de metro
     * @param id id de la ligne
     * @param nomLigne nom de la ligne
     * @param tempsParcours temps pour parcourir toute la ligne
     * @param listeStation liste des stations de la ligne
     */
    public Ligne(int id, String nomLigne, int tempsParcours, ArrayList<Station> listeStation) {
        this.id = id;
        this.nomLigne = nomLigne;
        this.tempsParcours = tempsParcours;
        this.listeStation = listeStation;
        for (int i = 0; i < this.listeStation.size(); i++) {
            if(this.listeStation.get(i).isIncident()==true){
                this.incident = true;
                break;
            }else{
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

    public int getTempsParcours() {
        return tempsParcours;
    }

    public void setTempsParcours(int tempsParcours) {
        this.tempsParcours = tempsParcours;
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

    @Override
    public String toString() {
        String reponse;
        if(incident){
            reponse = "La ligne : " + nomLigne + ", a un temps de parcours de " + tempsParcours +
                    " minutes et possède un incident. Elle passe par les stations suivantes : \n";
            for(int i=0; i<listeStation.size(); i++){
                reponse += "-"+listeStation.get(i).getNomStation()+"\n";
            }
        }else{
            reponse = "La ligne : " + nomLigne + ", a un temps de parcours de " + tempsParcours +
                    " minutes et ne possède pas d'incident. Elle passe par les stations suivantes :\n";
            for(int i=0; i<listeStation.size(); i++){
                reponse += "-"+listeStation.get(i).getNomStation()+"\n";
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
        for(int i=0; i<this.listeStation.size(); i++){
            nomStationActuelle = this.listeStation.get(i).getNomStation();
            if(nomStation == nomStationActuelle){
                existe = true;
            }
        }
        return existe;
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
