package miage.model;

import java.io.Serializable;

/**
 * Classe station qui permet de modéliser une station de metro
 */
public class Station implements Serializable {
    private static final long serialVersionUID = -2864975348390153703L;

    private static final int TEMPS_MINIMUM_ARRET = 0;
    private static final int TEMPS_MAXIMUM_ARRET = 10;
    private String nomStation;
    private int tempsArret;
    private boolean incident;
    private Position position;

    /**
     * Constructeur d'une station de metro
     * @param nom, le nom de la station
     * @param temps, le temps d'arret
     * @param inc, presence d'un incident ou non
     * @param latitude, latitude de la station de metro
     * @param longitude, longitude de la station de metro
     */
    public Station(String nom, int temps, boolean inc, double latitude, double longitude){
        this.nomStation=nom;
        if(verifierTempsArret(temps))
            this.tempsArret=temps;
        else
            this.tempsArret=TEMPS_MINIMUM_ARRET;
        this.incident=inc;
        this.position = new Position(latitude,longitude);
    }

    public String getNomStation() {
        return nomStation;
    }

    public void setNomStation(String nomStation) {
        this.nomStation = nomStation;
    }

    public int getTempsArret() {
        return tempsArret;
    }

    public void setTempsArret(int tempsArret) {
        if(verifierTempsArret(tempsArret))
            this.tempsArret = tempsArret;
    }

    public boolean isIncident() {
        return incident;
    }

    public void setIncident(boolean incident) {
        this.incident = incident;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(double latitude, double longitude) {
        this.position.setLat(latitude);
        this.position.setLon(longitude);
    }

    public int getTEMPS_MINIMUM_ARRET() {
        return TEMPS_MINIMUM_ARRET;
    }

    public int getTEMPS_MAXIMUM_ARRET() {
        return TEMPS_MAXIMUM_ARRET;
    }

    @Override
    public String toString() {
        if(incident)
            return "La Station : " + nomStation + " qui se trouve en Latitude : "+ position.getLat() +" et Longitude : "+ position.getLon()+ " a un temps d'arret de " + tempsArret + " minutes et possède un incident";
        else
            return "La Station : " + nomStation + " qui se trouve en Latitude : "+ position.getLat() +" et Longitude : "+ position.getLon()+ " a un temps d'arret de " + tempsArret + " minutes et ne possède pas d'incident";
    }

    /**
     * Fonction qui permet de verifier que le temps d'arret se trouve bien dans l'intervalle choisie
     * @param tps le temps d'arret
     * @return true si le temps d'arret et correct, false sinon
     */
    public static boolean verifierTempsArret(int tps){
        return (tps>=TEMPS_MINIMUM_ARRET && tps<=TEMPS_MAXIMUM_ARRET);
    }
}
