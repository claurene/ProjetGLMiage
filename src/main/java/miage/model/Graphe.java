package miage.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Graphe {
    private int n = 0; // nombre de stations
    private int m = 0; // nombre d'arcs
    private HashMap<String,ArrayList<String>> adjacents = new HashMap<String, ArrayList<String>>(); // liste des sommets adjacents
    private ArrayList<Arc> listeArcs = new ArrayList<Arc>(); // liste des arcs pondérés
    private HashMap<String,Station> toutesStations; // liste de toutes les stations (pour le calcul du temps d'arrêt)

    /**
     * Génération automatique du graphe du réseau à partir de la liste des lignes
     * @param listeLignes
     */
    public Graphe(HashMap<String,Ligne> listeLignes,HashMap<String,Station> toutesStations) {
        this.toutesStations = toutesStations;
        // Pour chaque ligne
        for (HashMap.Entry<String,Ligne> ligneEntry : listeLignes.entrySet()) {
            if (!ligneEntry.getValue().isIncident()) {
                // Pour chaque station de la ligne
                ArrayList<Station> listeStations = ligneEntry.getValue().getListeStation();
                ArrayList<Integer> tempsParcours = ligneEntry.getValue().getListeTempsParcours();
                for (int i = 0; i < listeStations.size() - 1; i++) {
                    Arc a = new Arc(listeStations.get(i).getNomStation(),
                            listeStations.get(i + 1).getNomStation(),
                            tempsParcours.get(i),
                            ligneEntry.getKey(),
                            ligneEntry.getValue().getTerminus().getNomStation(),
                            ligneEntry.getValue().getDepart().getNomStation());
                    m++;
                    // On ajoute les sommets adjacents dans les deux sens
                    try {
                        adjacents.get(listeStations.get(i).getNomStation()).get(0);
                    } catch (NullPointerException e) {
                        adjacents.put(listeStations.get(i).getNomStation(), new ArrayList<String>());
                        n++; // On incrémente le nombre de stations uniquement si elle n'a pas encore été traitée
                    }
                    // Sens de direction inverse :
                    adjacents.get(listeStations.get(i).getNomStation()).add(listeStations.get(i + 1).getNomStation());
                    try {
                        adjacents.get(listeStations.get(i + 1).getNomStation()).get(0);
                    } catch (NullPointerException e) {
                        adjacents.put(listeStations.get(i + 1).getNomStation(), new ArrayList<String>());
                        n++;
                    }
                    adjacents.get(listeStations.get(i + 1).getNomStation()).add(listeStations.get(i).getNomStation());
                    // On garde l'arc en mémoire
                    listeArcs.add(a);
                }
            }
        }
    }

    int getPoids(String x, String y){
        for (Arc a : listeArcs) {
            if ((a.getOrigine().equals(x) && a.getDestination().equals(y))
                || (a.getOrigine().equals(y) && a.getDestination().equals(x))){
                // Ajout du temps d'arrêt de la station d'arrivée
                return a.getTempsParcours()+toutesStations.get(y).getTempsArret();
            }
        }
        return -1;
    }

    //TODO: à voir si encore utile
    String getNomLigne(String x, String y){
        for (Arc a : listeArcs) {
            if ((a.getOrigine().equals(x) && a.getDestination().equals(y))
                    || (a.getOrigine().equals(y) && a.getDestination().equals(x))){
                return a.getNomLigne();
            }
        }
        return "";
    }

    String getNomLigneDirection(String x, String y){
        for (Arc a : listeArcs) {
            if (a.getOrigine().equals(x) && a.getDestination().equals(y)){
                return a.getNomLigne()+" direction \""+a.getDirectionAvant()+"\"";
            } else if (a.getOrigine().equals(y) && a.getDestination().equals(x)) {
                return a.getNomLigne()+" direction \""+a.getDirectionInverse()+"\"";
            }
        }
        return "";
    }

    // Getters
    public int getNombreSommets() {
        return n;
    }

    public int getNombreArcs() {
        return m;
    }

    public HashMap<String, ArrayList<String>> getAdjacents() {
        return adjacents;
    }

    public ArrayList<Arc> getListeArcs() {
        return listeArcs;
    }

    @Override
    public String toString() {
        return "Graphe{" +
                "adjacents=" + adjacents.toString() +
                ", listeArcs=" + listeArcs.toString() +
                '}';
    }
}
