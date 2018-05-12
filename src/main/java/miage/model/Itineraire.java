package miage.model;

import miage.controller.StationController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Classe permettant le calcul de l'itinéraire en fonction du graphe du réseau ainsi que de la source et la destination choisies
 */
public class Itineraire {
    private Graphe g;
    private String source;
    private String dest;
    private HashMap<String,Integer> dist; // liste des distances à la source
    private HashMap<String,String> pred; // liste des prédécésseurs à la source
    private ArrayList<String> nonTraites = new ArrayList<String>();

    //private ArrayList<String> stationsChangement = new ArrayList<String>(); // stations de changement, complètées auto. TODO: à voir si encore utile

    /**
     * Constructeur d'un itinéraire en fonction d'un réseau et des stations de départ et d'arrivée
     * @param g le graphe du réseau
     * @param sourceStation la station de départ
     * @param destStation la station d'arrivée
     */
    public Itineraire(Graphe g, String sourceStation, String destStation) {
        this.g = g;
        this.source = sourceStation;
        this.dest = destStation;
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

    /**
     * Trouve la station de distance minimale parmis les stations non traitées, et l'enlève de la liste
     * @return la station de distance minimale
     */
    private String getMinDistSuivant(){
        int minDist = dist.get(nonTraites.get(0));
        String suivant= nonTraites.get(0);
        for (String s : nonTraites) {
            if (dist.get(s) < minDist) {
                minDist=dist.get(s);
                suivant=s;
            }
        }
        nonTraites.remove(suivant);
        return suivant;
    }

    /**
     * Actualise les distances dans l'algorithme de dijkstra
     * @param y la station adjacente à x
     * @param x la station en train d'être traitée par l'algorithme
     */
    private void maj(String y, String x){
        //On récupére la station correspondante a y
        Station station =  StationController.getStations().get(y);
        //si cette station n'est pas en incident, on peut l'ajouter a la liste des stations a traiter dans l'algorithme.
        if(!station.isIncident()){
            int newDist = dist.get(x) + g.getPoids(x,y);
            if (dist.get(y)>newDist) {
                dist.put(y,newDist);
                pred.put(y,x);
                nonTraites.add(y);
            }
        }
    }

    /**
     * Implémentation de l'algorithme de dijkstra pour trouve le plus court chemin
     * Modifie les variables de l'itinéraire afin de pouvoir reconstruire le chemin
     * @return le temps du plus court chemin
     */
    /*
    on boucle sur toutes les stations possibles non traitées précédement
        on recupère la station la plus proche
        si c'est la destination, on retourne la distance correspondante
        sinon
            s'il y'a des sommets adjacents
                pour tout les adjacent de la station
                    on met a jour les distances
    */
    private int dijkstra(){
        String x = source;
        while (nonTraites.size()>0){
            x = getMinDistSuivant();
            if (x.equals(dest)) {
                return dist.get(dest);
            } else {
                // pour tous les sommets adjacents à x :
                if (g.getAdjacents().get(x)!=null) {
                    for (String y : g.getAdjacents().get(x)) {
                        maj(y, x);
                    }
                }
            }
        }
        return -1; // pas de chemin possible
    }

    /**
     * Construit le chemin en fonction des stations traitées par l'algorithme de dijkstra
     * Parcours la liste des prédécésseurs modifiée par l'algorithme
     * @return le chemin dans l'ordre à parcourir
     */
    private ArrayList<String> getCheminStations(){
        String entry = dest;
        ArrayList<String> chemin = new ArrayList<String>();
        chemin.add(entry);
        while (entry != source){
            entry=pred.get(entry);
            chemin.add(entry);
        }
        Collections.reverse(chemin);
        return chemin;
    }

    /**
     * Construit le chemin afin de donner des directions à l'utilisateur
     * @return Un tableau des stations de changement et lignes correspondantes
     */
    private ArrayList constChemin(){
        ArrayList<String> chemin = getCheminStations();
        ArrayList<ArrayList<String>> cheminComplet = new ArrayList<ArrayList<String>>();

        String currentLigne = g.getNomLigneDirection(chemin.get(0),chemin.get(1));
        ArrayList<String> s = new ArrayList<String>();
        s.add(chemin.get(0)); s.add(currentLigne);
        cheminComplet.add((ArrayList<String>) s.clone());

        for (int i=1;i<chemin.size()-1;i++){
            // à tous les changements de ligne :
            if (!g.getNomLigneDirection(chemin.get(i),chemin.get(i+1)).equals(currentLigne)){
                currentLigne=g.getNomLigneDirection(chemin.get(i),chemin.get(i+1));
                s = new ArrayList<String>();
                s.add(chemin.get(i)); s.add(currentLigne);
                cheminComplet.add((ArrayList<String>) s.clone());
                //stationsChangement.add(chemin.get(i)); TODO: peut-être à utiliser pour gérer les incidents
            }
        }

        s = new ArrayList<String>();
        s.add(chemin.get(chemin.size()-1));
        cheminComplet.add(s);
        return cheminComplet;
    }

    /**
     * Construit l'itinéraire le plus rapide
     * Applique l'algorithme de Dijkstra et modifie les variables de la classe
     * @return le tableau correspondant à l'itinéraire
     */
    /*
        dijkstra retourne toujours le temps de parcours le plus rapide. en sachant qu'on a mis a jour les différentes listes, on sait que notre itinéraire est le plus rapide.
     */
    public ArrayList<ArrayList<String>> constItineraireRapide(){
        ArrayList<ArrayList<String>> response = new ArrayList<ArrayList<String>>();
        int tempsParcours = dijkstra();
        if (tempsParcours>0){
            response = constChemin();
        }
        return response;
    }
}
