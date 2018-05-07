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
    private String statut;
    private LocalDateTime heure;


    /**
     * Constructeur des horaires de metro
     * @param arret nom de la station
     * @param ligne nom de la ligne
     * @param statut statut
     * @param heure heure
     */

    public Horaire(Station arret, Ligne ligne, String statut, LocalDateTime heure){
        this.arret = arret;
        this.ligne = ligne;
        this.statut = statut;
        this.heure = heure;
    }

    /**
     * Constructeur des horaires de métro avec détection automatique du statut
     * @param arret nom de la station
     * @param ligne nom de la ligne
     * @param heure heure
     */
    public Horaire(Station arret, Ligne ligne, LocalDateTime heure) {
        this.arret = arret;
        this.ligne = ligne;
        this.heure = heure;
        detectionStatutIncident();
    }

    /**
     * Défini le statut de l'horaire en fonction des incidents sur la ligne ou la station
     */
    private void detectionStatutIncident(){
        if (arret.isIncident()) {
            this.statut = "Incident sur la station";
        } else if (ligne.isIncident()) {
            this.statut = "Incident sur la ligne";
        } else {
            this.statut = "En service";
        }
    }

    /**
     * Calcul des horaires de passage de toutes les rames à un certain arret
     * @param date la date de passage
     * @param debutRames l'heure du début de passage des rames à l'arret choisi
     * @return table des horaires
     */
    private ArrayList<LocalDateTime> tableHoraireRames(LocalDate date, LocalTime debutRames){
        LocalDateTime heure = LocalDateTime.of(date,debutRames);
        ArrayList<LocalDateTime> tableHoraire = new ArrayList<LocalDateTime>();
        for (int i=0;i<24*NOMBRE_RAMES_HEURE;i++){
            tableHoraire.add(heure);
            heure = heure.plusMinutes(60/NOMBRE_RAMES_HEURE);
        }
        return tableHoraire;
    }

    /**
     * Renvoie le prochain passage de la rame en fonction de ses horaires et de l'heure actuelle
     * @param heure l'heure actuelle
     * @param tableHoraire la table des passages de la rame
     * @return l'heure de passage à l'arret après l'heure actuelle
     */
    private LocalDateTime getDateDepartRame(LocalDateTime heure, ArrayList<LocalDateTime> tableHoraire) {
        for (LocalDateTime e : tableHoraire) {
            if (e.isAfter(heure)) {return e;}
        }
        //Si passage après 24h
        return LocalDateTime.of(heure.toLocalDate().plusDays(1), LocalTime.from(tableHoraire.get(0)));
    }

    /**
     * Obtenir l'horaire désiré en fonction de la ligne, la station, et l'heure choisies
     * @return l'horaire approprié
     */
    public LocalDateTime getHoraire() {
        return getDateDepartRame(this.heure,getTableHoraire());
    }

    /**
     * Obtenir la table des horaires à un arret spécifié, pour une ligne et une date choisies
     * @return la table des horaires
     */
    public ArrayList<LocalDateTime> getTableHoraire() {
        Station departLigne = this.ligne.getDepart();
        ArrayList<LocalDateTime> horaire;
        // Si la station est un départ de ligne :
        if (this.arret.getNomStation().equals(departLigne.getNomStation())) {
            horaire = tableHoraireRames(this.heure.toLocalDate(),DEBUT_RAMES);
        } else {
            // Calcul du temps de parcours jusqu'à l'arret choisi
            int posArret = this.ligne.trouverPosListeStation(this.arret.getNomStation());
            ArrayList<Integer> tempsParcours = this.ligne.getListeTempsParcours();
            ArrayList<Station> listeStations = this.ligne.getListeStation();
            int total = 0;
            for (int i=0;i<posArret;i++) {
                total+=tempsParcours.get(i)+listeStations.get(i).getTempsArret();
            }
            // Horaire en fonction du temps du départ jusqu'à l'arret
            horaire = tableHoraireRames(this.heure.toLocalDate(),DEBUT_RAMES.plusMinutes(total));
        }
        return horaire;
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
        LocalDateTime horaire = getHoraire();
        return "Le prochain passage d'une rame à l'arrêt : " + arret.getNomStation() +
                " de la ligne "+ ligne.getNomLigne() +
                " direction : "+ ligne.getTerminus().getNomStation() +
                " se fera le " + horaire.toLocalDate() +
                " à "+horaire.toLocalTime();
    }

}
