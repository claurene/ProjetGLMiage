package miage.model;

import java.util.ArrayList;
import java.util.HashMap;

public class ItineraireLigne extends Itineraire {
    HashMap<String,Ligne> listeLignes;
    String stationDest,stationSource; // pour avoir la bonne direction

    public ItineraireLigne(GrapheLigne g, String sourceLigne, String stationSource, String stationDest) {
        this.g=g;
        this.stationDest=stationDest;
        this.stationSource=stationSource;
        this.listeLignes=g.getListeLignes();
        this.source=sourceLigne;
        this.dest=stationDest;
        this.dist = new HashMap<String,Integer>();
        this.pred = new HashMap<String,String>();

        // Initialisation de l'algorithme
        for (HashMap.Entry<String,ArrayList<String>> adjacentEntry : g.getAdjacents().entrySet()) {
            dist.put(adjacentEntry.getKey(),Integer.MAX_VALUE);
            pred.put(adjacentEntry.getKey(),"");
        }
        dist.put(source,0);
        pred.put(source,source);

        nonTraites.add(source);
    }

    private String getDirection(String l, String x, String y){
        Ligne ligne = listeLignes.get(l);
        if (ligne.trouverPosListeStation(x)<ligne.trouverPosListeStation(y)) {
            return ligne.getTerminus().getNomStation();
        } else {
            return ligne.getDepart().getNomStation();
        }
    }

    private ArrayList constChemin(){
        ArrayList<String> chemin = getCheminStations();
        ArrayList<ArrayList<String>> cheminComplet = new ArrayList<ArrayList<String>>();
        int totalTempsParcours=0;

        ArrayList<String> s = new ArrayList<String>();
        String currentStation = g.getNomLigneDirection(chemin.get(0),chemin.get(1));
        s.add(chemin.get(0)+" direction \""+getDirection(chemin.get(0),stationSource,currentStation)+"\"");
        cheminComplet.add((ArrayList<String>) s.clone());
        totalTempsParcours+=listeLignes.get(chemin.get(0)).getTempsParcoursStations(stationSource,currentStation);
        String newCurrentStation;

        for (int i=0;i<chemin.size()-2;i++){
            s = new ArrayList<String>();
            newCurrentStation = g.getNomLigneDirection(chemin.get(i+1),chemin.get(i+2));
            s.add(currentStation);s.add(chemin.get(i+1)+" direction \""+getDirection(chemin.get(i+1),currentStation,newCurrentStation)+"\"");
            cheminComplet.add((ArrayList<String>) s.clone());
            totalTempsParcours+=listeLignes.get(chemin.get(i+1)).getTempsParcoursStations(currentStation,newCurrentStation);
            currentStation=newCurrentStation;
        }

        s = new ArrayList<String>();
        s.add(currentStation);s.add(chemin.get(chemin.size()-1)+" direction \""+getDirection(chemin.get(chemin.size()-1),currentStation,stationDest)+"\"");
        cheminComplet.add((ArrayList<String>) s.clone());
        totalTempsParcours+=listeLignes.get(chemin.get(chemin.size()-1)).getTempsParcoursStations(currentStation,stationDest);

        this.totalTempsParcours=totalTempsParcours;
        return cheminComplet;
    }

    private int dijkstra(){
        String x;
        while (nonTraites.size()>0){
            x = getMinDistSuivant();
            if(listeLignes.get(x).trouverStation(dest)) {
                this.dest=x; // pour pouvoir reconstituer le chemin
                return dist.get(x);
            } else {
                // pour tous les sommets adjacents à x :
                if (g.getAdjacents().get(x)!=null) {
                    for (String y : g.getAdjacents().get(x)) {
                        //On récupére la ligne correspondante a y
                        Ligne ligne = listeLignes.get(y);
                        //si cette ligne n'est pas en incident, on peut l'ajouter a la liste des lignes a traiter dans l'algorithme.
                        if (!ligne.isIncident()) {
                            maj(y, x);
                        }
                    }
                }
            }
        }
        return -1; // pas de chemin possible
    }

    public ArrayList<ArrayList<String>> constItineraireMoinsChangements(){
        ArrayList<ArrayList<String>> response = new ArrayList<ArrayList<String>>();
        int tempsParcours = dijkstra();
        //total temps parcours déjà calculé
        if (tempsParcours>0){
            response = constChemin();
        }
        return response;
    }
}
