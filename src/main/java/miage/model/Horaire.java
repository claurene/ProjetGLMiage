package miage.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Classe Horaire qui permet de connaître les horaires de passage des rames sur l'arrêt d'une ligne
 */

public class Horaire {

    private final int NOMBRE_RAMES_HEURE = 10;
    private final LocalTime DEBUT_RAMES = LocalTime.MIDNIGHT;
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
     * Calcul des horaires de passage des rames à la station de départ de chaque ligne
     * @return table des horaires
     */
    private ArrayList<LocalDateTime> tableHoraireRames(LocalDate date){
        LocalDateTime heure = LocalDateTime.of(date,DEBUT_RAMES);
        ArrayList<LocalDateTime> tableHoraire = new ArrayList<LocalDateTime>();
        for (int i=0;i<24*NOMBRE_RAMES_HEURE;i++){
            tableHoraire.add(heure);
            heure = heure.plusMinutes(60/NOMBRE_RAMES_HEURE);
        }
        return tableHoraire;
    }

    private LocalDateTime getDateDepartRame(LocalDateTime heure) {
        for (LocalDateTime e : tableHoraireRames(heure.toLocalDate())) {
            if (e.isAfter(heure)) {return e;}
        }
        //Si passage après 24h
        return LocalDateTime.of(heure.toLocalDate().plusDays(1),DEBUT_RAMES);
    }

    /**
     * Obtenir l'horaire désiré en fonction de la ligne, la station, la direction et l'heure choisies
     * @return l'horaire approprié
     */
    public LocalDateTime getHoraire() {
        //TODO : gérer la direction !!
        Station departLigne = this.ligne.getDepart();
        LocalDateTime horaire = this.heure; //initialisation
        // Si la station est un départ de ligne :
        if (this.arret.getNomStation().equals(departLigne.getNomStation())) {
            horaire = getDateDepartRame(this.heure);
        } else {
            //TODO : avec temps de parcours entre deux stations
        }
        return horaire;
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
        return "Le prochain passage d'une rame à l'arrêt : " + arret.getNomStation() + " de la ligne "+ ligne.getNomLigne() +" direction "+ direction + " se fera le " + getProchainPassage();
    }

}
