package miage.model;

/**
 * Classe ParcoursEntreStations qui permet de connaître les temps de parcours entre deux stations
 */

public class ParcoursEntreStations {
    private Station station1;
    private Station station2;
    private Ligne ligne;
    private int tempsParcours;

    /**
     * Constructeur du temps de parcours entre deux Stations
     * @param station1 , une Station
     * @param station2 , une seconde Station
     * @param tempsParcours , le temps de parcours entre les deux stations
     */
    public ParcoursEntreStations(Station station1, Station station2, Ligne ligne, int tempsParcours){
        this.station1 = station1;
        this.station2 = station2;
        this.ligne = ligne;
        this.tempsParcours = tempsParcours;
    }

    public Station getStation1() {
        return station1;
    }

    public void setStation1(Station station1) {
        this.station1 = station1;
    }

    public Station getStation2() {
        return station2;
    }

    public void setStation2(Station station2) {
        this.station2 = station2;
    }

    public Ligne getLigne() {
        return ligne;
    }

    public void setLigne(Ligne ligne) {
        this.ligne = ligne;
    }

    public int getTempsParcours() {
        return tempsParcours;
    }

    public void setTempsParcours(int tempsParcours) {
        this.tempsParcours = tempsParcours;
    }

}
