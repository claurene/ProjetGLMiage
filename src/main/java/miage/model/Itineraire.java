package miage.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Classe permettant le calcul de l'itinéraire en fonction du graphe du réseau ainsi que de la source et la destination choisies
 */
public class Itineraire {
    private Graphe g;
    private int n; // nombre de stations
    private String source;
    private String dest;
    private HashMap<String,Integer> dist; // liste des distances à la source
    private HashMap<String,String> pred; // liste des prédécésseurs à la source
    private ArrayList<String> nonTraites = new ArrayList<String>();

    private ArrayList<String> stationsChangement = new ArrayList<String>(); // stations de changement, complètées auto. TODO: à réutiliser pour les autres types d'itinéraire

    public Itineraire(Graphe g, String sourceStation, String destStation) {
        this.g = g;
        this.n = g.getNombreSommets();
        this.source = sourceStation;//.getNomStation();
        this.dest = destStation;//.getNomStation();
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

    private String getMinDistSuivant(){ //nonTraités
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

    private void maj(String y, String x){ //g, nonTraités
        int newDist = dist.get(x) + g.getPoids(x,y);
        if (dist.get(y)>newDist) {
            dist.put(y,newDist);
            pred.put(y,x);
            nonTraites.add(y);
        }
    }

    private int dijkstra(){
        String x = source;
        while (nonTraites.size()>0){
            x = getMinDistSuivant();
            if (x.equals(dest)) {
                return dist.get(dest);
            } else {
                // pour tous les sommets adjacents à x :
                //TODO: gérer nullPointerException si les stations n'existent pas
                for ( String y : g.getAdjacents().get(x)){
                    maj(y,x);
                }
            }
        }
        return -1; // pas de chemin possible
    }

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

    private String constChemin(){
        ArrayList<String> chemin = getCheminStations();
        String currentLigne = g.getNomLigne(chemin.get(0),chemin.get(1));
        String s = "\n - Prendre ligne "+currentLigne+" à "+chemin.get(0); //TODO: afficher direction
        for (int i=1;i<chemin.size()-1;i++){
            if (!g.getNomLigne(chemin.get(i),chemin.get(i+1)).equals(currentLigne)){
                currentLigne=g.getNomLigne(chemin.get(i),chemin.get(i+1));
                s += "\n - Prendre ligne "+currentLigne+" à "+chemin.get(i);
                stationsChangement.add(chemin.get(i));
            }
        }
        s+="\n - Descendre à "+chemin.get(chemin.size()-1);
        return s;
    }

    /**
     * Construit l'itinéraire le plus rapide
     * @return l'itinéraire le plus rapide
     */
    public String constItineraireRapide(){
        String response;
        int tempsParcours = dijkstra();
        if (tempsParcours>0){
            response = "Itinéraire le plus rapide entre "+source+" et "+dest+" : "+tempsParcours+" minutes";
            /*for (String c : getCheminStations()){
                response+="\n- "+c;
            }*/
            response+=constChemin();
        } else {
            response = "Aucun chemin possible entre "+source+" et "+dest;
        }
        return response;
    }
}
