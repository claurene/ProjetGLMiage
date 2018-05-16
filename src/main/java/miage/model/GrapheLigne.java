package miage.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Constitution d'un graphe où les lignes sont considérées comme des sommets
 */
public class GrapheLigne extends Graphe{
    private HashMap<String,Ligne> listeLignes;

    public GrapheLigne(HashMap<String,Ligne> listeLignes){
        this.listeLignes = listeLignes;
        for (HashMap.Entry<String,Ligne> ligneEntry : listeLignes.entrySet()){
            ArrayList<Station> listeStations = ligneEntry.getValue().getListeStation();
            for (HashMap.Entry<String,Ligne> ligneEntry2 : listeLignes.entrySet()){
                if (!ligneEntry2.getKey().equals(ligneEntry.getKey())){
                    ArrayList<Station> listeStations2 = new ArrayList<Station>(ligneEntry2.getValue().getListeStation());
                    listeStations2.retainAll(listeStations);
                    for (Station s : listeStations2){
                        try {
                            adjacents.get(ligneEntry.getKey()).get(0);
                        } catch (NullPointerException e) {
                            adjacents.put(ligneEntry.getKey(), new ArrayList<String>());
                            n++; // On incrémente le nombre de sommets uniquement si elle n'a pas encore été traitée
                        }
                        if(!adjacents.get(ligneEntry.getKey()).contains(ligneEntry2.getKey())){
                            adjacents.get(ligneEntry.getKey()).add(ligneEntry2.getKey());
                        }
                        try {
                            adjacents.get(ligneEntry2.getKey()).get(0);
                        } catch (NullPointerException e) {
                            adjacents.put(ligneEntry2.getKey(), new ArrayList<String>());
                            n++;
                        }
                        if(!adjacents.get(ligneEntry2.getKey()).contains(ligneEntry.getKey())){
                            adjacents.get(ligneEntry2.getKey()).add(ligneEntry.getKey());
                        }
                        // temps de parcours et direction inutiles pour cette modélisation
                        Arc a = new Arc(ligneEntry.getKey(),
                                ligneEntry2.getKey(),
                                s.getTempsArret(),
                                s.getNomStation(),
                                ligneEntry.getValue().getTerminus().getNomStation(),
                                ligneEntry2.getValue().getTerminus().getNomStation()
                        );
                        listeArcs.add(a);
                        m++;
                    }
                }
            }
        }

    }

    @Override
    int getPoids(String x, String y) {
        for (Arc a : listeArcs) {
            if ((a.getOrigine().equals(x) && a.getDestination().equals(y))
                    || (a.getOrigine().equals(y) && a.getDestination().equals(x))){
                return 1;
            }
        }
        return -1;
    }

    @Override
    String getNomLigneDirection(String x, String y) {
        for (Arc a : listeArcs) {
            if ((a.getOrigine().equals(x) && a.getDestination().equals(y))
                    || (a.getOrigine().equals(y) && a.getDestination().equals(x))){
                return a.getNomLigne(); // direction calculée dans l'itinéraire
            }
        }
        return "";
    }

    public HashMap<String, Ligne> getListeLignes() {
        return listeLignes;
    }
}
