package miage.controller;

import miage.model.Horaire;
import miage.model.Ligne;
import miage.model.Station;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;

public class HoraireController {
    // Création du logger
    private static final Logger LOG = Logger.getLogger(HoraireController.class.getName());

    /**
     * Affiche la table des horaires des prochains passages de la rame à l'arrêt demandé
     * @param arret
     * @param ligne
     * @param heure actuelle
     * @param maxHoraires nombre maximum d'horaires affichés
     * @return affiche la table des prochains horaires
     */
    public String afficherTableHoraire(Station arret, Ligne ligne, LocalDateTime heure, int maxHoraires){
        String response;
        if (ligne.trouverStation(arret.getNomStation())){
            response = "Horaires pour la ligne "+ligne.getNomLigne()+" direction "+ligne.getTerminus().getNomStation()+" à l'arrêt "+arret.getNomStation()+" :\n";
            Horaire h = new Horaire(arret,ligne,heure);
            ArrayList<LocalDateTime> tableHoraire = h.getTableHoraire();
            int i = 1;
            for (LocalDateTime l : tableHoraire) {
                if (l.isAfter(heure)) {
                    response += "- " + l.toLocalDate() + " " + l.toLocalTime() + "\n";
                    i++;
                }
                if (i>maxHoraires){break;} // Nombre maximum d'horaires affichés
            }
        } else {
            response = "La ligne "+ligne.getNomLigne()+" ne passe pas à l'arret "+arret.getNomStation();
        }
        return response;
    }

    /**
     * Affiche l'heure du prochain passage de la rame à l'arrêt précisé
     * @param arret
     * @param ligne
     * @param heure actuelle
     * @return affiche le prochain passage de la rame
     */
    public String afficherProchainPassage(Station arret, Ligne ligne, LocalDateTime heure){
        String response;
        if (ligne.trouverStation(arret.getNomStation())){
            Horaire h = new Horaire(arret,ligne,heure);
            response = h.toString();
        } else {
            response = "La ligne "+ligne.getNomLigne()+" ne passe pas à l'arret "+arret.getNomStation();
        }
        return response;
    }
}
