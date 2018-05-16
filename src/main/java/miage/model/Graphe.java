package miage.model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Graphe {
    protected int n = 0; // nombre de sommets
    protected int m = 0; // nombre d'arcs
    protected HashMap<String,ArrayList<String>> adjacents = new HashMap<String, ArrayList<String>>(); // liste des sommets adjacents
    protected ArrayList<Arc> listeArcs = new ArrayList<Arc>(); // liste des arcs pondérés

    abstract int getPoids(String x, String y);

    abstract String getNomLigneDirection(String x, String y);

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
