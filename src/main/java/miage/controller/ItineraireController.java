package miage.controller;

import miage.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class ItineraireController {
    // Création du logger
    private static final Logger LOG = Logger.getLogger(ItineraireController.class.getName());

    private String afficherItineraire(ArrayList<ArrayList<String>> itineraire,String source, String dest){
        String response = "";
        if (!itineraire.isEmpty()){
            response+="Pour aller de "+source+" à "+dest+" : ";
            for (ArrayList<String> l : itineraire){
                if (l.size()>1) {
                    response += "\n - À "+l.get(0)+" prendre la ligne "+l.get(1);
                } else {
                    response +="\n - Descendre à "+l.get(0);
                }
            }
        } else {
            response +="Aucun chemin possible avec les paramètres renseignés";
        }
        return response;
    }

    /**
     * Affiche l'itinéraire le plus rapide entre deux stations
     * @param listeLignes
     * @param sourceStation
     * @param destStation
     * @return les directions de l'itinéraire le plus rapide
     */
    public String itinerairePlusRapide(HashMap<String,Ligne> listeLignes, String sourceStation, String destStation){
        Graphe g = new Graphe(listeLignes);
        ArrayList<ArrayList<String>> itineraire = new Itineraire(g,sourceStation,destStation).constItineraireRapide();
        return afficherItineraire(itineraire,sourceStation,destStation);
    }

    /**
     * Affiche l'itinéraire le plus rapide avec la liste des changements spécifiés
     * @param listeLignes
     * @param listeStations
     * @return les direction de l'itinéraire
     */
    public String itineraireAvecChangements(HashMap<String,Ligne> listeLignes, ArrayList<String> listeStations){
        Graphe g = new Graphe(listeLignes);
        Itineraire itineraire;
        ArrayList<ArrayList<String>> cheminItineraire = new ArrayList<ArrayList<String>>();
        for (int i=0;i<listeStations.size()-1;i++){
            itineraire = new Itineraire(g,listeStations.get(i),listeStations.get(i+1));
            for (ArrayList<String> l : itineraire.constItineraireRapide()){
                cheminItineraire.add(l);
            }
        }
        return afficherItineraire(cheminItineraire,listeStations.get(0),listeStations.get(listeStations.size()-1));
    }

}
