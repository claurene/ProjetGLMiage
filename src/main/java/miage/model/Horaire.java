package miage.model;


import java.time.LocalDateTime;

/**
 * Classe Horaire qui permet de connaître les horaires de passage des rames sur l'arrêt d'une ligne
 */

public class Horaire {

    private final int NOMBRE_RAMES_HEURE = 10;
    private Station arret;
    private Ligne ligne;
    private String direction;
    private String statut;
    private LocalDateTime heure;


    /**
     * Constructeur des horaires de metro
     * @param arret nom de la station
     * @param ligne nom de la ligne
     * @param direction direction de la ligne
     * @param statut statut
     * @param heure heure
     */

    public Horaire(Station arret, Ligne ligne, String direction, String statut, LocalDateTime heure){

        this.arret = arret;
        this.ligne = ligne;
        this.direction = direction;
        this.statut = statut;
        this.heure = heure;
    }

    /**
     * Fonction qui permet de verifier le nombre de minutes avant le prochain passage
     * On récupère d'abord la date à laquelle la fonction a été executée puis on extrait
     * les minutes, ensuite on calcul le nombre de minutes avant la prochaine rame et on l'ajoute
     * à la date actuelle
     * @return la date de prochain passage au format LocalDateTime
     */

    private LocalDateTime getProchainPassage() {
        LocalDateTime heureActuelle = LocalDateTime.now();
        int minutes = heureActuelle.getMinute();
        int calcul = minutesEntreRames() - (minutes % minutesEntreRames());
        return heureActuelle.plusMinutes(calcul);
    }


    /**
     * Fonction qui permet de calculer le nombre de minutes entre deux rames en partant de
     * la constante NOMBRE_RAMES_HEURE
     * @return un int du nombre de minutes entre deux rames
     */


    private int minutesEntreRames(){
        int minutesRames;
        minutesRames = 60/NOMBRE_RAMES_HEURE;
        return minutesRames;
    }

    public int getNOMBRE_RAMES_HEURE() {
        return NOMBRE_RAMES_HEURE;
    }

    public Station getArret() {
        return arret;
    }

    public void setArret(Station arret) {
        this.arret = arret;
    }

    public Ligne getLigne() {
        return ligne;
    }

    public void setLigne(Ligne ligne) {
        this.ligne = ligne;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDateTime getHeure() {
        return heure;
    }

    @Override
    public String toString() {
        return "Le prochain passage d'une rame à l'arrêt : " + arret + " de la ligne "+ ligne +" direction "+ direction + " se fera le " + getProchainPassage();
    }

}
