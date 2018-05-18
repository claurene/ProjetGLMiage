package miage.controller;

import miage.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class ItineraireController {
    // Création du logger
    private static final Logger LOG = Logger.getLogger(ItineraireController.class.getName());

    private String afficherItineraire(ArrayList<ArrayList<String>> itineraire,String source, String dest, int tempsParcours, String option){
        String response = "";
        if (!itineraire.isEmpty()){
            response+="Pour aller de "+source+" à "+dest+" : "+tempsParcours+" minutes";
            for (ArrayList<String> l : itineraire){
                if (l.size()>1) {
                    response += "\n - À "+l.get(0)+" prendre la ligne "+l.get(1);
                } else if (option.equals("station")){
                    response +="\n - Descendre à "+l.get(0);
                } else if (option.equals("ligne")){
                    response +="\n - Prendre la ligne "+l.get(0);
                }
            }
        } else {
            response +="Aucun chemin possible avec les paramètres renseignés";
        }
        return response;
    }

    /**
     * Affiche l'itinéraire le plus rapide entre deux stations
     * @param listeLignes liste des lignes existantes
     * @param listeStations liste des stations existantes
     * @param sourceStation station de départ
     * @param destStation station d'arrivee
     * @return les directions de l'itinéraire le plus rapide
     */
    /*
         On crée un graphe a partir de la liste des lignes
         A partir de ce graphe, on fait la recherche d'itinéraire le plus rapide
         on retourne l'afficage de l'itinéraire trouvé.
     */
    public String itinerairePlusRapide(HashMap<String,Ligne> listeLignes, HashMap<String,Station> listeStations, Station sourceStation, Station destStation){
        GrapheStation g = new GrapheStation(LigneController.getLignes(),StationController.getStations());
        ItineraireStation i = new ItineraireStation(g,sourceStation,destStation);
        ArrayList<ArrayList<String>> itineraire = i.constItineraireRapide();
        return afficherItineraire(itineraire,sourceStation.getNomStation(),destStation.getNomStation(),i.getTotalTempsParcours(),"station");
    }

    /**
     * Affiche l'itinéraire le plus rapide avec la liste des changements spécifiés
     * @param listeLignes liste des lignes existantes
     * @param toutesStations liste des stations existantes
     * @param listeStations liste des points de passage
     * @return les direction de l'itinéraire
     */
    public String itineraireAvecChangements(HashMap<String,Ligne> listeLignes, HashMap<String,Station> toutesStations, ArrayList<Station> listeStations){
        GrapheStation g = new GrapheStation(listeLignes,toutesStations);
        ItineraireStation itineraire;
        int tempsParcours=0;
        ArrayList<ArrayList<String>> cheminItineraire = new ArrayList<ArrayList<String>>();
        for (int i=0;i<listeStations.size()-1;i++){
            itineraire = new ItineraireStation(g,listeStations.get(i),listeStations.get(i+1));
            for (ArrayList<String> l : itineraire.constItineraireRapide()){
                cheminItineraire.add(l);
            }
            tempsParcours+=itineraire.getTotalTempsParcours();
        }
        return afficherItineraire(cheminItineraire,listeStations.get(0).getNomStation(),listeStations.get(listeStations.size()-1).getNomStation(),tempsParcours,"station");
    }

    /**
     * Affiche l'itinéraire avec le moins de changement de lignes
     * @param listeLignes
     * @param sourceStation
     * @param destStation
     * @return
     */
    public String itineraireMoinsChangements(HashMap<String,Ligne> listeLignes, String sourceStation, String destStation) {
        GrapheLigne g = new GrapheLigne(listeLignes);
        ArrayList<ArrayList<ArrayList<String>>> listeItineraire = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<Integer> tempsParcours = new ArrayList<Integer>();
        ItineraireLigne itineraireLigne;
        // Pour chaque ligne avec laquelle on peut commencer
        for (HashMap.Entry<String,Ligne> ligneEntry : listeLignes.entrySet()) {
            if (ligneEntry.getValue().trouverStation(sourceStation)){
                if (ligneEntry.getValue().trouverStation(destStation)) {
                    // c'est sur la même ligne
                    return "Prendre la ligne "+ligneEntry.getKey()+" de "+sourceStation+" jusqu'à "+destStation;
                } else {
                    // itinéraire :
                    itineraireLigne = new ItineraireLigne(g,ligneEntry.getKey(),sourceStation,destStation);
                    listeItineraire.add(itineraireLigne.constItineraireMoinsChangements());
                    tempsParcours.add(itineraireLigne.getTotalTempsParcours());
                }
            }
        }
        // itinéraire avec le moins de changements
        if (!listeItineraire.isEmpty()) {
            int index=0;
            int minSize = listeItineraire.get(0).size();
            for (int i=0;i<listeItineraire.size();i++) {
                if (listeItineraire.get(i).size()<minSize) {
                    minSize=listeItineraire.get(i).size();
                    index=i;
                }
            }
            return afficherItineraire(listeItineraire.get(index),sourceStation,destStation,tempsParcours.get(index),"ligne");
        }
        return afficherItineraire(new ArrayList<>(),sourceStation,destStation,0,"ligne");
    }
}
