package miage.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public abstract class Itineraire {
    protected Graphe g;
    protected String source;
    protected String dest;
    protected HashMap<String,Integer> dist; // liste des distances à la source
    protected HashMap<String,String> pred; // liste des prédécésseurs à la source
    protected ArrayList<String> nonTraites = new ArrayList<String>();
    protected int totalTempsParcours;


    /**
     * Trouve le sommet de distance minimale parmis les sommets non traités, et l'enlève de la liste
     * @return le sommet de distance minimale
     */
    protected String getMinDistSuivant(){
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
     * @param y le sommet adjacent à x
     * @param x le sommet en train d'être traité par l'algorithme
     */
    protected void maj(String y, String x){
        int newDist = dist.get(x) + g.getPoids(x,y);
        if (dist.get(y)>newDist) {
            dist.put(y,newDist);
            pred.put(y,x);
            nonTraites.add(y);
        }
    }

    /**
     * Construit le chemin en fonction des stations traitées par l'algorithme de dijkstra
     * Parcours la liste des prédécésseurs modifiée par l'algorithme
     * @return le chemin dans l'ordre à parcourir
     */
    protected ArrayList<String> getCheminStations(){
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
     * Permet de récupérer le temps de parcours total après avoir appliqué l'algorithme de dijkstra
     * @return totalTempsParcours
     */
    public int getTotalTempsParcours() {
        return totalTempsParcours;
    }
    
}
