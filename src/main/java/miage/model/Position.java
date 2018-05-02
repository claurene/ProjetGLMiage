package miage.model;

import java.io.Serializable;
import java.util.Random;

/**
 * Classe position permettant de représenter la position d'un utilisateur ou d'une station
 */
public class Position implements Serializable {
    private static final long serialVersionUID = 7195882432555722461L;

    private final double LATITUDE_MIN = 48.7534;
    private final double LATITUDE_MAX = 48.9534;
    private final double LONGITUDE_MIN = 2.0488;
    private final double LONGITUDE_MAX = 2.5488;
    private double lat;
    private double lon;

    /**
     * Constructeur permettant de créer une position aletoire dans l'intervalle définie
     */
    public Position(){
        Random r = new Random();
        this.lat = LATITUDE_MIN + (r.nextDouble() * (LATITUDE_MAX-LATITUDE_MIN));
        this.lon = LONGITUDE_MIN + (r.nextDouble() * (LONGITUDE_MAX-LONGITUDE_MIN));
    }

    /**
     * Constructeur permettant de définir une position choisie
     * @param latx, la latitude
     * @param lony, la longitude
     */
    public Position(double latx, double lony){
        if(VerifierPosition(latx,lony)) {
            this.lat = latx;
            this.lon = lony;
        }
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double latx) {
        if(VerifierLatitude(latx))
        lat = latx;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lony) {
        if(VerifierLongitude(lony))
        lon = lony;
    }

    public double getLATITUDE_MIN() {
        return LATITUDE_MIN;
    }

    public double getLATITUDE_MAX() {
        return LATITUDE_MAX;
    }

    public double getLONGITUDE_MIN() {
        return LONGITUDE_MIN;
    }

    public double getLONGITUDE_MAX() {
        return LONGITUDE_MAX;
    }

    @Override
    public String toString() {
        return "Position{" +"Latitude=" + lat + ", Longitude=" + lon+ '}';
    }

    /**
     * Methode permettant de verifier que la longitude reste dans l'intervalle définie
     * @param lony la longitude
     * @return boolean
     */
    public boolean VerifierLongitude(double lony){
        return (lony >= LONGITUDE_MIN && lony <= LONGITUDE_MAX);
    }

    /**
     * Methode permettant de verifier que la latitude reste dans l'intervalle définie
     * @param latx la latitude
     * @return boolean
     */
    public boolean VerifierLatitude(double latx){
        return (latx>=LATITUDE_MIN && latx<=LATITUDE_MAX);
    }

    /**
     * Methode permettant de verifier que la position reste dans l'intervalle définie
     * @param latx la latitude
     * @param lony la longitude
     * @return boolean
     */

    public boolean VerifierPosition(double latx,double lony){
        return (VerifierLatitude(latx) && VerifierLongitude(lony));
    }

    /**
     * Méthode permettant de calculer la distance entre un objet et une position
     * @param p la position sur laquelle on va calculer la distance
     * @return un double correspondant à la distance entre les deux objets
     */

    public double distance(Position p){
        return Math.sqrt(Math.abs(p.getLat()-this.getLat()) + Math.abs(p.getLon()-this.getLon()));
    }
}
